package pl.weeklyplanner.weeklyworksheet.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Component
@Builder
@Entity
@Table(name="users")
@NoArgsConstructor

public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long userId;
    private String username;
    private String password;
    private char[] passwordChar;

    @OneToMany
    @JoinColumn(name="userId")
    private List<Task> taskList;
}