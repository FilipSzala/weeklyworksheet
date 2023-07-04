package pl.weeklyplanner.weeklyworksheet.service;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.weeklyplanner.weeklyworksheet.PasswordValidator;
import pl.weeklyplanner.weeklyworksheet.model.Task;
import pl.weeklyplanner.weeklyworksheet.model.User;
import pl.weeklyplanner.weeklyworksheet.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordValidator passwordValidator;
    @Autowired
    public UserService(UserRepository userRepository, PasswordValidator passwordValidator) {
        this.userRepository = userRepository;
        this.passwordValidator = passwordValidator;
    }

    public void saveUser(User user){

        userRepository.save(user);
    }
    public List<User> findAllUsers(){
        List<User> users =  userRepository.findAll();
        return users;
    }
    public User findUserByUserId(HttpSession httpSession){
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId!=null) {
            return userRepository.findByUserId(Long.valueOf(userId));
        }
        else
            return null;
    }
    public boolean isPasswordValid(String password){
        return passwordValidator.isPasswordValid(password);
    }
    public boolean existUsername(String username){
        return findAllUsers().stream()
                .map(user->user.getUsername())
                .filter(name->name.equalsIgnoreCase(username))
                .findAny()
                .isPresent();
    }
    public boolean isPasswordCorrect(String username, String password){
        if(findByUserName(username)==null){
            return false;
        }
        return userRepository.findByUserName(username).getPassword().equals(password);
    }
    public User findByUserName(String username){
        return userRepository.findByUserName(username);
}
    public List<Task> findTasksByMonday(HttpSession httpSession, LocalDate monday){
        return  findUserByUserId(httpSession).getTaskList().stream()
                .filter(task -> task.getMonday().equals(monday))
                .collect(Collectors.toList());
    }

}
