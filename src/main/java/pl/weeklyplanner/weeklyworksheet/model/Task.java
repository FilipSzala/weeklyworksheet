package pl.weeklyplanner.weeklyworksheet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.weeklyplanner.weeklyworksheet.TaskCategory;
import pl.weeklyplanner.weeklyworksheet.TaskType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class Task {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Long userId;
    private String name;
    private TaskCategory category;
    private TaskType type;
    private LocalDate today= LocalDate.now();
    private LocalDate monday = setMonday();
    private Boolean checkboxValue =false;
    @ElementCollection
    private List<LocalDate> weekDate= setWeekDate();


    public Task() {
    }
    public Task(LocalDate monday) {
        this.monday = monday;
    }
    public LocalDate setMonday(){
        if (today==today.with(DayOfWeek.MONDAY)){
            this.monday = today;
        }
        else {
            this.monday = today.with(DayOfWeek.MONDAY.minus(7));
        }
        return monday;
    }

    public List<LocalDate> setWeekDate() {
        ArrayList<LocalDate> currentWeekDate = new ArrayList<>();
        currentWeekDate.add(getMonday());
        currentWeekDate.add(today.with(DayOfWeek.SUNDAY));
        return currentWeekDate;
    }
}