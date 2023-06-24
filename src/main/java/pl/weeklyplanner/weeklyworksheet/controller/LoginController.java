package pl.weeklyplanner.weeklyworksheet.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.weeklyplanner.weeklyworksheet.LoginSession;
import pl.weeklyplanner.weeklyworksheet.model.User;
import pl.weeklyplanner.weeklyworksheet.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String displayLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(String username, String password, HttpSession httpSession) {
        if (userService.isPasswordCorrect(username, password)) {
            User loggedInUser = userService.findByUserName(username);
            LoginSession session = new LoginSession(loggedInUser.getUserId());
            httpSession.setAttribute("userId", session.getUserId());
            return "redirect:/home";
        } else {
            return "redirect:/login?error";
}}}