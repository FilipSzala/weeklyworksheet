package pl.weeklyplanner.weeklyworksheet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.weeklyplanner.weeklyworksheet.model.User;
import pl.weeklyplanner.weeklyworksheet.service.UserService;

@Controller
public class RegistrationController {

    private UserService userService;
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    //I can't use mapping - Put/Patch/Delete, because it isn't available in html elements (form and input).

    @GetMapping("/registration")
    public String displayRegistrationForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user, Model model) {

        if (!userService.isPasswordValid(user.getPassword())) {
            model.addAttribute("error", "Error.Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 digit, and a total of minimum 9 characters. Special characters are not allowed in the password");
            return "registration";
        }

        if (userService.existUsername(user.getUsername())) {
            model.addAttribute("errorUsername", "Account with this username already exists.");
            return "registration";
        }

        userService.saveUser(user);

        return "redirect:/login";
    }
}