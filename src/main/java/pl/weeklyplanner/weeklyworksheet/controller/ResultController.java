package pl.weeklyplanner.weeklyworksheet.controller;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.weeklyplanner.weeklyworksheet.Summary;

import java.time.LocalDate;
import java.time.Month;

@Controller
@Getter
public class ResultController {

    private Summary summary;
    @Autowired
    public ResultController(Summary summary) {
        this.summary = summary;
    }

    //I can't use mapping - Put/Patch/Delete, because it isn't available in html elements (form and input).
    @GetMapping("/result")
    public String displayResultPage(Model model, HttpSession httpSession, LocalDate monday){
        summary.setFields(httpSession,monday,0,null,null);
        resultAttribiutes(model);
        return "result";
    }
    @GetMapping("/searchYear/{year}")
    public String searchYear(@PathVariable("year") int year, Model model, HttpSession httpSession, LocalDate monday){
        summary.setFields(httpSession,monday,year,null,null);
        resultAttribiutes(model);
        return "result";
    }
    @GetMapping("/searchMonth/{month}")
    public String searchMonth(@PathVariable("month") String month, Model model, HttpSession httpSession,LocalDate monday){
        summary.setFields(httpSession,monday,getSummary().getYear(), Month.valueOf(month),null);
        resultAttribiutes(model);
        return "result";
    }
    @GetMapping("/searchDate/{date}")
    public String searchDate (@PathVariable("date") LocalDate targetDate,LocalDate monday, Model model, HttpSession httpSession){
        summary.setFields(httpSession,monday,getSummary().getYear(),getSummary().getMonth(),targetDate);
        resultAttribiutes(model);
        return "result";
    }
    public void resultAttribiutes (Model model) {
        model.addAttribute("username",getSummary().getUsername());
        model.addAttribute("finishedTasksWithOneTimeTask",getSummary().getListOfTaskFinishedOneTimeType());
        model.addAttribute("finishedTasksWithWeeklyType",getSummary().getListOfTaskFinishedWeeklyType());
        model.addAttribute("notFinishTasksWitOneTimeTask",getSummary().getListOfTaskNotFinishOneTimeType());
        model.addAttribute("notFinishTasksWithWeeklyType",getSummary().getListOfTaskNotFinishWeeklyType());
        model.addAttribute("years", getSummary().getListOfYear());
        model.addAttribute("months",getSummary().getListOfMonth());
        model.addAttribute("summary", getSummary());
        model.addAttribute("currentWeek",getSummary().getCurrentWeek());
        model.addAttribute("weekDate",getSummary().getTargetWeekDate());
        model.addAttribute("weeks",getSummary().getListofWeek());
    }

}
