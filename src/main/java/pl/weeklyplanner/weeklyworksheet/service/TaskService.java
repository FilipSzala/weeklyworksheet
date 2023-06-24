package pl.weeklyplanner.weeklyworksheet.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.weeklyplanner.weeklyworksheet.model.Task;
import pl.weeklyplanner.weeklyworksheet.model.User;
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

    public Task saveTaskWithUserId(Task task,Long userId) {
        task.setUserId(userId);
        return taskRepository.save(task);
    }
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
    public Task updateTask(Task editedTask, Long editTaskId) {
        Optional<Task> task = findTasktById(editTaskId);
        task.get().setId(editTaskId);
        task.get().setName(editedTask.getName());
        task.get().setType(editedTask.getType());
        task.get().setCategory(editedTask.getCategory());
        return saveTask(task.orElseThrow());
            }
    public void updateFieldCheckboxValue(Task task) {
        task.setCheckboxValue(task.getCheckboxValue() == false ? true : false);
        saveTask(task);
    }
    }

