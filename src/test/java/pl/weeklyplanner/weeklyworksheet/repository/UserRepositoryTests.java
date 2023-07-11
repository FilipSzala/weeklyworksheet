
package pl.weeklyplanner.weeklyworksheet.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.weeklyplanner.weeklyworksheet.model.User;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void FindByUserName_UsernameExists_ReturnUser(){
     //Arrange
     User user = User.builder()
             .username("a".repeat(21))
             .build();
     //Act
    userRepository.save(user);
    Optional <User> foundUser =userRepository.findByUserName(user.getUsername());
     //Assert
     Assertions.assertThat(foundUser).isNotNull();
    }
    @Test
    public void FindByUserName_UsernameWithDifferentLetterSize_ReturnNull(){
        //Arrange
        User user = User.builder()
                .username("a".repeat(21))
                .build();
        User user2 = User.builder()
                .username("A".repeat(22))
                .build();
        //Act
        userRepository.save(user);
        userRepository.save(user2);
        Optional<User> foundUser1 = userRepository.findByUserName("a".repeat(21).toUpperCase());
        Optional <User> foundUser2 = userRepository.findByUserName("a".repeat(22).toLowerCase());
        //Assert
        Assertions.assertThat(foundUser1).isNull();
        Assertions.assertThat(foundUser2).isNull();
    }

@Test
    public void FindByUserName_UsernameIsNull_ReturnNull(){
        //Arrange
        User user = User.builder().build();
        //Act
        userRepository.save(user);
        Optional <User> foundUser = userRepository.findByUserName(user.getUsername());
        //Assert
        Assertions.assertThat(foundUser).isNull();
    }

    @Test
    public void FindByUserName_UsernameIsEmpty_ReturnUser(){
        //Arrange
        User user = User.builder()
                .username("")
                .build();
        //Act
        userRepository.save(user);
        Optional <User> foundUser = userRepository.findByUserName(user.getUsername());
        //Assert
        Assertions.assertThat(foundUser).isNotNull();
    }

    @Test
    public void FindByUserId_IdExists_ReturnUser(){
        //Id is autoincrement so I don't have to assign value.
        //Arrange
        User user = User.builder()
                .build();
        //Act
        userRepository.save(user);
        User foundUser = userRepository.findByUserId(user.getUserId());
        //Assert
        Assertions.assertThat(foundUser).isNotNull();
    }

    @Test
    public void FindByUserId_IdDoesNotExist_ReturnNull(){
        //Act
        User user = userRepository.findByUserId(0L);
        //Assert
        Assertions.assertThat(user).isNull();
    }

    @Test
    public void FindByUserId_IdIsNull_ReturnUser(){
        //Arrange
        User user = User.builder()
                .userId(null)
                .build();
        //Act
        userRepository.save(user);
        User foundUser = userRepository.findByUserId(user.getUserId());
        //Assert
        Assertions.assertThat(foundUser).isNotNull();
    }

}

