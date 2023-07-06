package pl.weeklyplanner.weeklyworksheet.service;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    protected final Logger log = LoggerFactory.getLogger(getClass());
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
            log.error("Id was less than expected. Id - " + userId.toString());
            throw new IllegalArgumentException("Id can't be less than 1");
        } else if(userId==null){
            log.error("Id was null. Id - " + userId.toString());
            throw new NullPointerException("Id can't be null");
        } else if (task.getName() == null||task.getType()==null||task.getCategory()==null) {
            log.error("Some fields of task were empty. Name - " +task.getName() + " Type - " + task.getType().toString() + " Category - "+task.getCategory().toString());
            throw new IllegalArgumentException("Fields of task can't be empty");
        }
            else if (task==null) {
            log.error("Task was empty");
            throw new IllegalArgumentException("Task can't be empty");
        }

        task.setUserId(userId);
        log.info("Task added successfully");
       return taskRepository.save(task);
    }

    public Task saveTask(Task task) {
        if (task.getName() == null||task.getType()==null||task.getCategory()==null) {
            log.error("Empty fields");
            throw new IllegalArgumentException("Task can't be empty");
        }
        log.info("Task added successfully");
        return taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        if (id==null){
            throw new NullPointerException("Id is incorrect");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id is incorrect");
        }
        taskRepository.deleteById(id);
    }
    public Task updateTask(Task editedTask, Long id) {
        if (editedTask.getName() == null||editedTask.getType()==null||editedTask.getCategory()==null) {
            throw new IllegalArgumentException("Edited task can't be empty");
        }
        else if (id==null){
            throw new NullPointerException("Id is incorrect");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id is incorrect");
        }
        Optional<Task> task = findTaskById(id);
        task.get().setName(editedTask.getName());
        task.get().setType(editedTask.getType());
        task.get().setCategory(editedTask.getCategory());
        return saveTask(task.orElseThrow());

        }
    public void updateFieldCheckboxValue(Task task) {
        if (task.getName() == null||task.getType()==null||task.getCategory()==null || task.getCheckboxValue()==null) {
            throw new IllegalArgumentException("Task has empty field");}
        task.setCheckboxValue(task.getCheckboxValue() == false ? true : false);
        saveTask(task);
    }
    }

