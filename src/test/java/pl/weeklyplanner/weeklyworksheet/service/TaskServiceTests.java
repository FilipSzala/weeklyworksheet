
package pl.weeklyplanner.weeklyworksheet.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.weeklyplanner.weeklyworksheet.TaskCategory;
import pl.weeklyplanner.weeklyworksheet.TaskType;
import pl.weeklyplanner.weeklyworksheet.model.Task;
import pl.weeklyplanner.weeklyworksheet.repository.TaskRepository;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertAll;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private  TaskService taskService;
    @Test void FindTaskById_CorrectId_ReturnTask(){
        Long id = 1L;
        Task task = Task.builder().build();
        when(taskRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(task));
        Optional <Task> foundTask = taskService.findTaskById(id);
        Assertions.assertThat(foundTask).isNotNull();
    }
    @Test void FindTaskById_IdLessThanExpected_ReturnException(){
        Long id = 0L;
        assertThatThrownBy(() -> taskService.findTaskById(id))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void FindTaskById_IdIsNull_ReturnException(){
        Long id = null;
        assertThatThrownBy(() -> taskService.findTaskById(id))
                .isInstanceOf(NullPointerException.class);
    }


    @Test void SaveTaskWithUserId_CorrectTaskAndId_ReturnTask(){
        Long userId = 1L;
        Task task = Task.builder()
                .name("Test")
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();
        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        Task savedTask = taskService.saveTaskWithUserId(task,userId);
        Assertions.assertThat(savedTask).isNotNull();
    }
    @Test void SaveTaskWithUserId_CorrectTaskIdLessThanExpected_ReturnException(){
        Long userId = 0L;
        Task task = Task.builder()
                .name("Test")
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();

        assertThatThrownBy(() -> taskService.saveTaskWithUserId(task,userId))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void SaveTaskWithUserId_CorrectTaskIdIsNull_ReturnException(){
        Long userId = null;
        Task task = Task.builder()
                .name("Test")
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();

        assertThatThrownBy(() -> taskService.saveTaskWithUserId(task,userId))
                .isInstanceOf(NullPointerException.class);
    }
    @Test void SaveTaskWithUserId_IncorrectTaskIdLessThanExpexted_ReturnExceptions(){
        Long userId = 0L;
        Task task = Task.builder()
                .name(null)
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();
        assertThatThrownBy(() -> taskService.saveTaskWithUserId(task,userId))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void SaveTaskWithUserId_IncorrectTaskIdIsNull_ReturnExceptions(){
        Long userId = null;
        Task task = Task.builder()
                .name(null)
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();
        assertThatThrownBy(() -> taskService.saveTaskWithUserId(task,userId))
                .isInstanceOf(NullPointerException.class);
    }
    @Test void SaveTaskWithUserId_InorrectTaskIdCorrect_ReturnException(){
        Long userId = 1L;
        Task task = Task.builder()
                .name(null)
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();

        assertThatThrownBy(() -> taskService.saveTaskWithUserId(task,userId))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void SaveTask_CorrectTask_ReturnTask(){
        Task task = Task.builder()
                .name("Test")
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();
        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        Task savedTask = taskService.saveTask(task);

        Assertions.assertThat(savedTask).isNotNull();
        Assertions.assertThat(savedTask.getName()).isEqualTo(task.getName());
    }

    @Test
    public void SaveTask_IncorrectTask_ReturnException(){
        Task task = Task.builder()
                .build();
        assertThatThrownBy(() -> taskService.saveTask(task))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void DeleteTaskById_CorrectId_DeleteTask(){
        Task task = Task.builder().id(1L).build();
        assertAll(() -> taskService.deleteTaskById(task.getId()));
    }
    @Test
    public void DeleteTaskById_IdIsNull_ReturnException(){
        Task task = Task.builder().id(null).build();
        assertThatThrownBy(() -> taskService.deleteTaskById(task.getId()))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    public void DeleteTaskById_IdLessThanExpected_ReturnException(){
        Task task = Task.builder().id(0L).build();
        assertThatThrownBy(() -> taskService.deleteTaskById(task.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test void UpdateTask_CorrectTaskAndId_ReturnTask(){
        Long userId = 1L;
        Task oldTask = Task.builder()
                .id(1L)
                .name("Test")
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();
        Task editedTask = Task.builder()
                .id(1L)
                .name("TestCorrect")
                .type(TaskType.WEEKLYTASK)
                .category(TaskCategory.THEMOSTIMPORTANT)
                .build();
        when(taskRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(oldTask));
        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(oldTask);


        Task savedTask = taskService.updateTask(editedTask,userId);
        Assertions.assertThat(savedTask).isNotNull();
        Assertions.assertThat(savedTask.getName()).isEqualTo("TestCorrect");
        Assertions.assertThat(savedTask.getCategory()).isEqualTo(TaskCategory.THEMOSTIMPORTANT);
    }
    @Test void UpdateTask_CorrectTaskIdLessThanExpected_ReturnException(){
        Long userId = 0L;
        Task editedTask = Task.builder()
                .id(1L)
                .name("TestCorrect")
                .type(TaskType.WEEKLYTASK)
                .category(TaskCategory.THEMOSTIMPORTANT)
                .build();

        assertThatThrownBy(() -> taskService.updateTask(editedTask,userId))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void UpdateTask_CorrectTaskIdIsNull_ReturnException(){
        Long userId = null;
        Task editedTask = Task.builder()
                .id(1L)
                .name("TestCorrect")
                .type(TaskType.WEEKLYTASK)
                .category(TaskCategory.THEMOSTIMPORTANT)
                .build();

        assertThatThrownBy(() -> taskService.updateTask(editedTask,userId))
                .isInstanceOf(NullPointerException.class);
    }
    @Test void UpdateTask_IncorrectTaskIdLessThanExpexted_ReturnException(){
        Long userId = 0L;
        Task editedTask = Task.builder()
                .name(null)
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();
        assertThatThrownBy(() -> taskService.updateTask(editedTask,userId))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void UpdateTask_IncorrectTaskIdIsNull_ReturnException(){
        Long userId = null;
        Task editedTask = Task.builder()
                .name(null)
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();
        assertThatThrownBy(() -> taskService.updateTask(editedTask,userId))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void UpdateTask_IncorrectTaskIdCorrect_ReturnException(){
        Long userId = 1L;
        Task editedTask = Task.builder()
                .name(null)
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();

        assertThatThrownBy(() -> taskService.updateTask(editedTask,userId))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void UpdateFieldCheckboxValue_CorrectTask_UpdateCheckbox(){
        Task task = Task.builder()
                .id(1L)
                .name("test")
                .checkboxValue(false)
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();

        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);
        assertAll(() -> taskService.updateFieldCheckboxValue(task));
    }

    @Test
    void UpdateFieldCheckboxValue_InorrectTask_ReturnException(){
        Task task = Task.builder()
                .id(1L)
                .name("test")
                .checkboxValue(null)
                .type(TaskType.ONETIMETASK)
                .category(TaskCategory.NORMAL)
                .build();
        assertThatThrownBy(() -> taskService.updateFieldCheckboxValue(task))
                .isInstanceOf(NullPointerException.class);
    }

}

