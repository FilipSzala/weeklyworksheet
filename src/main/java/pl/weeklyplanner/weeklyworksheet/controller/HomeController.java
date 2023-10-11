package pl.weeklyplanner.weeklyworksheet.controller;


import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.weeklyplanner.weeklyworksheet.AuthenticationFilter;
import pl.weeklyplanner.weeklyworksheet.model.Task;
import pl.weeklyplanner.weeklyworksheet.service.TaskService;
import pl.weeklyplanner.weeklyworksheet.service.UserService;

@Controller
@Getter

public class HomeController {
    private TaskService taskService;
    private UserService userService;
    private AuthenticationFilter authenticationFilter;
    @Autowired
    public HomeController(TaskService taskService,UserService userService, AuthenticationFilter authenticationFilter) {
        this.taskService = taskService;
        this.userService = userService;
        this.authenticationFilter = authenticationFilter;
    }
    //I can't use mapping - Put/Patch/Delete, because it isn't available in html elements (form and input).
    @GetMapping("home")
    public String displayHomePage(Model model, HttpSession httpSession) {
            model.addAttribute("username", userService.findUserByUserId(httpSession).getUsername());
            return "home";
    }
    @GetMapping("")
    public String displayHomePageWithOtherUrl(Model model, HttpSession httpSession) {
        model.addAttribute("username", userService.findUserByUserId(httpSession).getUsername());
        return "home";
    }

    @GetMapping("/tasks")
    public String displayTaskPage() {
        return "task";
    }

    @PostMapping("/tasks")
    public String addTask(Task task,HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        taskService.saveTask(task, userId);
        return "task";
    }

}

