package pl.weeklyplanner.weeklyworksheet.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.weeklyplanner.weeklyworksheet.model.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTests {
    @Autowired
    TaskRepository taskRepository;

    @Test
    public void FindTasksByMonday_MondayExist_ReturnTasks(){
        //Arrange
        Task task = Task.builder()
                .monday(LocalDate.now().minusYears(5).with(DayOfWeek.MONDAY))
                .build();
        Task task2 = Task.builder()
                .monday(LocalDate.now().minusYears(5).with(DayOfWeek.MONDAY))
                .build();
        //Act
        taskRepository.save(task);
        taskRepository.save(task2);
        List<Task> taskList = taskRepository.findTasksByMonday(task.getMonday());
        //Assert
        Assertions.assertThat(taskList).isNotNull();
        Assertions.assertThat(taskList.size()).isEqualTo(2);
    }
    @Test
    public void FindTasksByMonday_MondayDoesNotExist_ReturnEmpty(){
        //Act
        List<Task> taskList = taskRepository.findTasksByMonday(null);
        //Assert
        Assertions.assertThat(taskList).isEmpty();
        Assertions.assertThat(taskList.size()).isEqualTo(0);
    }
    @Test
    public void FindTasksByMonday_MondayIsNull_ReturnEmpty(){
        //Arrange
        Task task = Task.builder()
                .monday(null)
                .build();
        //Act
        taskRepository.save(task);
        List<Task> taskList = taskRepository.findTasksByMonday(task.getMonday());
        //Assert
        Assertions.assertThat(taskList).isEmpty();
    }
}
