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

    public Optional<Task> findTaskById(Long id) {
        if (id==null){
            throw new NullPointerException("Id is incorrect");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id is incorrect");
        }
        return taskRepository.findById(id);
    }

    public Task saveTaskWithUserId(Task task,Long userId) {
        if (userId<=0){
            throw new IllegalArgumentException("Id is incorrect");
        } else if(userId==null){
            throw new NullPointerException("Id is incorrect");
        } else if (task.getName() == null||task.getType()==null||task.getCategory()==null) {
            throw new IllegalArgumentException("Task can't be empty");
        }
        task.setUserId(userId);
       return taskRepository.save(task);
    }

    public Task saveTask(Task task) {
        if (task.getName() == null||task.getType()==null||task.getCategory()==null) {
            throw new IllegalArgumentException("Task can't be empty");
        }
        return taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
    public void updateTask(Task editedTask, Long id) {
        Optional<Task> task = findTaskById(id);
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

