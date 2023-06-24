package pl.weeklyplanner.weeklyworksheet.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.weeklyplanner.weeklyworksheet.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from users where username = :username", nativeQuery = true)
    User findByUserName(@Param("username") String username);

    @Query(value = "select * from users where user_Id = :userId", nativeQuery = true)
    User findByUserId(@Param("userId") Long userId);

}