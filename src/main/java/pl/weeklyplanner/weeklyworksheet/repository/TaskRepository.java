package pl.weeklyplanner.weeklyworksheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.weeklyplanner.weeklyworksheet.model.Task;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository <Task,Long> {

    List<Task> findAllByType(Enum type);
    @Query ("SELECT t FROM Task t WHERE t.monday = :monday")
    List <Task> findTasksByMonday(@Param("monday")LocalDate monday);
}
