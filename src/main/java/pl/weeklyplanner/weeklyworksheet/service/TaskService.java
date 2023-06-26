package pl.weeklyplanner.weeklyworksheet.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.weeklyplanner.weeklyworksheet.model.Task;
import pl.weeklyplanner.weeklyworksheet.repository.TaskRepository;
import pl.weeklyplanner.weeklyworksheet.repository.UserRepository;

import java.util.Optional;

@Getter
@Setter
@Service


public class TaskService {
    private TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {

        this.taskRepository = taskRepository;
    }
    @Autowired
    private UserRepository userRepository;

    public TaskService() {
    }

    public Optional<Task> findTasktById(long id) {
        return taskRepository.findById(id);
    }

    public void saveTaskWithUserId(Task task,Long userId) {
        task.setUserId(userId);
       taskRepository.save(task);
    }
    public void saveTask(Task task) {
        taskRepository.save(task);
    }
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
    public void updateTask(Task editedTask, Long id) {
        Optional<Task> task = findTasktById(id);
        task.get().setName(editedTask.getName());
        task.get().setType(editedTask.getType());
        task.get().setCategory(editedTask.getCategory());
        saveTask(task.orElseThrow());
            }
    public void updateFieldCheckboxValue(Task task) {
        task.setCheckboxValue(task.getCheckboxValue() == false ? true : false);
        saveTask(task);
    }
    }

