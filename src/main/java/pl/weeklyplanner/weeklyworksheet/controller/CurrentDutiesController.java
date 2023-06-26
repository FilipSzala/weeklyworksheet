package pl.weeklyplanner.weeklyworksheet.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.weeklyplanner.weeklyworksheet.Summary;
import pl.weeklyplanner.weeklyworksheet.model.Task;
import pl.weeklyplanner.weeklyworksheet.service.TaskService;
import pl.weeklyplanner.weeklyworksheet.service.UserService;

import java.time.LocalDate;

@Controller
@Getter
public class CurrentDutiesController {
    private TaskService taskService;
    private UserService userService;
    private Summary summary;
    private String previousPage=null;
    @Autowired
    public CurrentDutiesController(TaskService taskService, UserService userService, Summary summary) {
        this.taskService = taskService;
        this.userService = userService;
        this.summary = summary;
    }
    //I can't use mapping - Put/Patch/Delete, because it isn't available in html elements (form and input).
    @GetMapping("/currentDuties")
    public String displaycurrentDuties(Model model, HttpSession httpSession,LocalDate monday) {
        summary.setFields(httpSession,monday,0,null,null);
        weeklyAttribiutes(model,httpSession,monday);
        return "/currentDuties";
    }
    @GetMapping ("/tasks/checkboxValue/{taskId}")
    public String updateCheckboxValue(@PathVariable("taskId") Long taskId, HttpServletRequest request) {
        String requestUrl = request.getHeader("Referer");
        taskService.updateFieldCheckboxValue(taskService.findTasktById(taskId).orElseThrow());
        return "redirect:" + requestUrl;
    }
    @GetMapping  ("/tasks/{taskId}")
    public String displayTaskEditPage(@PathVariable("taskId")Long taskId, Model model, HttpServletRequest request){
        previousPage = request.getHeader("Referer");
        model.addAttribute("task", taskService.findTasktById(taskId).get());
        model.addAttribute("previousPage",previousPage);
        return "taskEdit";
    }

    @PostMapping("/tasks/{taskId}")
    public String updateTask(@PathVariable("taskId") Long taskId, Task editedTask){
        taskService.updateTask(editedTask,taskId);
        return "redirect:" +previousPage;
    }
    @GetMapping("/tasks/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId")Long taskId,HttpServletRequest request){
        previousPage = request.getHeader("Referer");
        taskService.deleteTaskById(taskId);
        return "redirect:" +previousPage;
    }

    public void weeklyAttribiutes(Model model,HttpSession httpSession,LocalDate monday) {


        model.addAttribute("allTasks",userService.findUserByUserId(httpSession).getTaskList());
        model.addAttribute("currentWeek",getSummary().getCurrentWeek());
        model.addAttribute("finishedTasksWithOneTimeTask",getSummary().getListOfTaskFinishedOneTimeType());
        model.addAttribute("finishedTasksWithWeeklyType",getSummary().getListOfTaskFinishedWeeklyType());
        model.addAttribute("notFinishTasksWitOneTimeTask",getSummary().getListOfTaskNotFinishOneTimeType());
        model.addAttribute("notFinishTasksWithWeeklyType",getSummary().getListOfTaskNotFinishWeeklyType());
        model.addAttribute("tasksWithOneTimeType",getSummary().getListOfAllTasksOneTimeType());
        model.addAttribute("tasksWithWeeklyType",getSummary().getListofAllTasksWeeklyType());
    }

}
