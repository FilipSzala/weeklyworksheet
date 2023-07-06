package pl.weeklyplanner.weeklyworksheet.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.weeklyplanner.weeklyworksheet.model.User;
import pl.weeklyplanner.weeklyworksheet.repository.UserRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    User user;

    @BeforeEach
        public void init() {
        user = User.builder().userId(1L).username("Filip").password("Szala123").taskList(new ArrayList<>()).build();
    }
    @Test
    public void SaveUser_CorrectUser_ReturnException_SaveUser(){
        assertAll(() ->userService.saveUser(user));
    }

}

