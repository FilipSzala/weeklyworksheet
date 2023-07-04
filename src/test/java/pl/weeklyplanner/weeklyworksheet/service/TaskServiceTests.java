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

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private  TaskService taskService;
    @Test void FindTaskById_CorrectId_ReturnTask(){
        Task task = Task.builder()
                .id(1L)
                .build();
        when(taskRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(task));
        Optional <Task> foundTask = taskService.findTaskById(task.getId());
        Assertions.assertThat(foundTask).isNotNull();
    }
    @Test void FindTaskById_IdLessThanExpected_ReturnException(){
        Task task = Task.builder()
                .id(0L)
                .build();
        assertThatThrownBy(() -> taskService.findTaskById(task.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void FindTaskById_IdIsNull_ReturnException(){
        Task task = Task.builder()
                .id(null)
                .build();
        assertThatThrownBy(() -> taskService.findTaskById(task.getId()))
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
}
