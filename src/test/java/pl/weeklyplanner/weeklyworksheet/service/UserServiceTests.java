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
import pl.weeklyplanner.weeklyworksheet.model.User;
import pl.weeklyplanner.weeklyworksheet.repository.UserRepository;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
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
    public void SaveUser_CorrectUser_ReturnException_SaveUser(){
        assertAll(() ->userService.saveUser(userCorrect));
    }
    @Test
    public void SaveUser_IdIsNull_ReturnException(){
        assertThatThrownBy(() -> userService.saveUser(userIdNull))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    public void SaveUser_IdLessThanExpected_ReturnException(){
        assertThatThrownBy(() -> userService.saveUser(userIdNull))
                .isInstanceOf(IllegalArgumentException.class);
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

    }


}

