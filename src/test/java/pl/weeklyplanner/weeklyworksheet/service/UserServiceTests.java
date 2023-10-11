package pl.weeklyplanner.weeklyworksheet.service;

import jakarta.servlet.http.HttpSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.weeklyplanner.weeklyworksheet.PasswordValidator;
import pl.weeklyplanner.weeklyworksheet.TaskCategory;
import pl.weeklyplanner.weeklyworksheet.TaskType;
import pl.weeklyplanner.weeklyworksheet.model.Task;
import pl.weeklyplanner.weeklyworksheet.model.User;
import pl.weeklyplanner.weeklyworksheet.repository.UserRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User userCorrect;
    private User userIdNull;
    private User userIdLessThanExpected;
    private User usernameNull;

    @BeforeEach
        public void init() {
        userCorrect = User.builder().userId(1L).username("Filip").password("Szala123").taskList(new ArrayList<>()).build();
        userIdNull = User.builder().userId(null).username("Filip").password("Szala123").taskList(new ArrayList<>()).build();
        userIdLessThanExpected = User.builder().userId(0L).username("Filip").password("Szala123").taskList(new ArrayList<>()).build();
        usernameNull = User.builder().userId(1L).username(null).password("Szala123").taskList(new ArrayList<>()).build();
    }
    @Test
    public void SaveUser_CorrectUser_SaveUser(){
        assertAll(() ->userService.saveUser(userCorrect));
    }
    @Test
    public void SaveUser_UsernameIsNull_ReturnException(){
        assertThatThrownBy(() -> userService.saveUser(usernameNull))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void FindUserByUserId_CorrectSession_ReturnUser() {
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        Mockito.when(httpSession.getAttribute("userId")).thenReturn(1L);
        Mockito.when(userService.findUserByUserId(httpSession)).thenReturn(userCorrect);
        User foundUser = userService.findUserByUserId(httpSession);
        Assertions.assertThat(foundUser).isNotNull();
    }
    @Test
    public void FindUserByUserId_SessionIdIsNull_ReturnUser() {
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        Mockito.when(httpSession.getAttribute("userId")).thenReturn(null);
        assertThatThrownBy(() -> userService.findUserByUserId(httpSession))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    public void FindUserByUserId_SessionIdIsLessThanExpected_ReturnUser() {
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        Mockito.when(httpSession.getAttribute("userId")).thenReturn(0L);
        assertThatThrownBy(() -> userService.findUserByUserId(httpSession))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void ExistUsername_ValidUsername_ReturnTrue(){

        ArrayList<User> userList = new ArrayList<>();
        userList.add(userCorrect);
        userList.add(userCorrect);
        when (userRepository.findAll()).thenReturn(userList);
        String username = "Filip";
        Boolean usernameExist = userService.existUsername(username);
        Assertions.assertThat(usernameExist).isTrue();
    }
    @Test
    public void ExistUsername_InvalidUsername_ReturnFalse(){
        ArrayList<User> userList = new ArrayList<>();
        userList.add(userCorrect);
        userList.add(userCorrect);
        when (userRepository.findAll()).thenReturn(userList);
        String username = "Mariusz";
        Boolean usernameExist = userService.existUsername(username);
        Assertions.assertThat(usernameExist).isFalse();
    }
    @Test
    public void ExistUsername_UsernameIsEmpty_ReturnException(){
        String username = "";
        ArrayList<User> userList = new ArrayList<>();
        userList.add(userCorrect);
        userList.add(userCorrect);
        assertThatThrownBy(() -> userService.existUsername(username))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void ExistUsername_UsernameIsNull_ReturnException(){
        String username = null;
        ArrayList<User> userList = new ArrayList<>();
        userList.add(userCorrect);
        userList.add(userCorrect);
        assertThatThrownBy(() -> userService.existUsername(username))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void FindByUserName_ValidUsername_ReturnUser(){
        when (userRepository.findByUserName(userCorrect.getUsername())).thenReturn(Optional.of(userCorrect));
        Optional <User> foundUser = userService.findByUserName("Filip");
        Assertions.assertThat(foundUser).isNotNull();
    }
    @Test
    public void FindByUserName_InvalidUsername_ReturnEmptyOptional() {
        when(userRepository.findByUserName(Mockito.any(String.class))).thenReturn(Optional.empty());
        Optional<User> foundUser = userService.findByUserName("InvalidUsername");
        Assertions.assertThat(foundUser).isNotPresent();
    }
    @Test
    public void FindByUserName_UsernameIsEmpty_ReturnEmptyOptional(){
        when(userRepository.findByUserName(Mockito.any(String.class))).thenReturn(Optional.empty());
        Optional<User> foundUser = userService.findByUserName("");
        Assertions.assertThat(foundUser).isNotPresent();
    }
    @Test
    public void FindByUserName_UsernameIsNull_ReturnException(){
        assertThatThrownBy(() -> userService.findByUserName(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void FindTasksByMonday_CorrectMonday_ReturnTaskList(){
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(monday));
        tasks.add(new Task(monday));
        userCorrect.setTaskList(tasks);

        Mockito.when(httpSession.getAttribute("userId")).thenReturn(1L);
        when(userService.findUserByUserId(httpSession)).thenReturn(userCorrect);


        List <Task> taskList = userService.findTasksByMonday(httpSession,monday);
        Assertions.assertThat(taskList).isNotNull();
        Assertions.assertThat(taskList).size().isEqualTo(2);
    }

    @Test
    public void FindTasksByMonday_IncorrectMonday_ReturnEmptyTaskList(){
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(monday));
        tasks.add(new Task(monday));
        userCorrect.setTaskList(tasks);

        Mockito.when(httpSession.getAttribute("userId")).thenReturn(1L);
        when(userService.findUserByUserId(httpSession)).thenReturn(userCorrect);
        List <Task> taskList = userService.findTasksByMonday(httpSession,monday.plusDays(1));


        Assertions.assertThat(taskList).isEmpty();
    }
    @Test
    public void FindTasksByMonday_MondayIsNull_ReturnEmptyTaskList(){
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(monday));
        tasks.add(new Task(monday));
        userCorrect.setTaskList(tasks);

        Mockito.when(httpSession.getAttribute("userId")).thenReturn(1L);
        when(userService.findUserByUserId(httpSession)).thenReturn(userCorrect);
        List <Task> taskList = userService.findTasksByMonday(httpSession,null);


        Assertions.assertThat(taskList).isEmpty();
    }

}

