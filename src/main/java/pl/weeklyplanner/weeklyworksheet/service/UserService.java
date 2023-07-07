package pl.weeklyplanner.weeklyworksheet.service;


import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    public UserService(UserRepository userRepository, PasswordValidator passwordValidator) {
        this.userRepository = userRepository;
        this.passwordValidator = passwordValidator;
    }

    public void saveUser(User user){
        if (user.getUserId() <= 0) {
            log.error("Id was less than expected. Id - " + user.getUserId().toString());
            throw new IllegalArgumentException("Id can't be less than 1");
        } else if (user.getUserId() == null) {
            log.error("Id was null. Id - " + user.getUserId());
            throw new NullPointerException("Id can't be null");
        } else if (user.getUsername() == null || user.getPassword() == null) {
            log.error("Some fields of user were empty. Name - " + user.getUsername());
            throw new IllegalArgumentException("Fields of user can't be empty");
        } else if (user == null) {
            log.error("User was empty");
            throw new IllegalArgumentException("User can't be empty");
        }
        user.setPassword(encryptPassword(user.getPassword()));
        String username = user.getUsername();
        log.info("Username " + username + " added successfully");
        userRepository.save(user);
    }
    public List<User> findAllUsers(){
        List<User> users =  userRepository.findAll();
        return users;
    }
    public User findUserByUserId(HttpSession httpSession){
        if (httpSession == null) {
            throw new IllegalArgumentException("HttpSession can't be null");
        }
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId <= 0) {
            log.error("Id was less than expected. Id - " + userId.toString());
            throw new IllegalArgumentException("Id can't be less than 1");
        } else if (userId == null) {
            log.error("Id was null. Id - " + userId);
            throw new NullPointerException("Id can't be null");
        }
        return userRepository.findByUserId(userId);
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
        String hashedPassword = userRepository.findByUserName(username).getPassword();
        return checkDecryptPassword(password,hashedPassword);
    }
    public User findByUserName(String username){
        return userRepository.findByUserName(username);
}
    public List<Task> findTasksByMonday(HttpSession httpSession, LocalDate monday){
        return  findUserByUserId(httpSession).getTaskList().stream()
                .filter(task -> task.getMonday().equals(monday))
                .collect(Collectors.toList());
    }
    public String encryptPassword (String password){
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt());
        return hashedPassword;
    }
    public Boolean checkDecryptPassword (String password,String hashedPassword){
        return BCrypt.checkpw(password,hashedPassword);
    }
}
