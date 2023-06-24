
package pl.weeklyplanner.weeklyworksheet;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.weeklyplanner.weeklyworksheet.model.Task;
import pl.weeklyplanner.weeklyworksheet.service.UserService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Component
public class Summary {
    private String username=null;
    private int numberOfTaskFinished =0;
    private List <Task> listOfTaskFinishedOneTimeType =new ArrayList<>();
    private List <Task> listOfTaskFinishedWeeklyType =new ArrayList<>();
    private int numberOfTaskNotFinish =0;
    private List<Task> listOfTaskNotFinishOneTimeType =new ArrayList<>();
    private List <Task> listOfTaskNotFinishWeeklyType=new ArrayList<>();;
    private List <Task> listOfAllTasksOneTimeType = new ArrayList<>();
    private List <Task> listofAllTasksWeeklyType = new ArrayList<>();
    private int numberOfAllPoints = 0;
    private int numberOfPointsScored = 0;
    private double productivityResult = 0;
    private List<Integer> listOfYear = new ArrayList<>();
    private int year =0;
    private List<Month> listOfMonth = new ArrayList<>();
    private Enum month=null;
    private List<LocalDate> listofWeek = new ArrayList<>();
    private LocalDate targetDate=null;
    private String targetWeekDate=null;
    private String currentWeek =null;
    private List<Task> targetTasks = new ArrayList<>();

    @Autowired
    private UserService userService;

    public Summary(UserService userService) {
        this.userService = userService;
    }
    public Summary() {
    }
    public void setUsername (HttpSession httpSession){
        this.username = userService.findUserByUserId(httpSession).getUsername();
    }
    public void setNumberOfTaskFinished (HttpSession httpSession, LocalDate monday) {
        List <Task> tasks = targetTasks.isEmpty()? userService.findTasksByMonday(httpSession,monday): targetTasks;

            this.numberOfTaskFinished = (int) tasks.stream()
                    .filter(task -> task.getCheckboxValue().equals(true))
                    .count();
    }
    public void setListOfTaskFinishedOneTimeType(HttpSession httpSession,LocalDate monday){
        List <Task> tasks = targetTasks.isEmpty()? userService.findTasksByMonday(httpSession,monday): targetTasks;

        this.listOfTaskFinishedOneTimeType = tasks.stream()
                .filter(task -> task.getType().equals(TaskType.ONETIMETASK))
                .filter(task -> task.getCheckboxValue().equals(true))
                .collect(Collectors.toList());
    }
    public void setListOfTaskFinishedWeeklyType(HttpSession httpSession,LocalDate monday){
        List <Task> tasks = targetTasks.isEmpty()? userService.findTasksByMonday(httpSession,monday): targetTasks;

        this.listOfTaskFinishedWeeklyType = tasks.stream()
                .filter(task -> task.getType().equals(TaskType.WEEKLYTASK))
                .filter(task -> task.getCheckboxValue().equals(true))
                .collect(Collectors.toList());
    }
    public void setNumberOfTaskNotFinish(HttpSession httpSession,LocalDate monday) {
        List <Task> tasks = targetTasks.isEmpty()? userService.findTasksByMonday(httpSession,monday): targetTasks;

        this.numberOfTaskNotFinish =(int) tasks.stream()
                .filter(task -> task.getCheckboxValue().equals(false))
                .count();
    }
    public void setListOfTaskNotFinishOneTimeType(HttpSession httpSession,LocalDate monday) {
        List <Task> tasks = targetTasks.isEmpty()? userService.findTasksByMonday(httpSession,monday): targetTasks;

        this.listOfTaskNotFinishOneTimeType = tasks.stream()
                .filter(task -> task.getType().equals(TaskType.ONETIMETASK))
                .filter(task -> task.getCheckboxValue().equals(false))
                .collect(Collectors.toList());
    }
    public void setListOfTaskNotFinishWeeklyType(HttpSession httpSession,LocalDate monday) {
        List <Task> tasks = targetTasks.isEmpty()? userService.findTasksByMonday(httpSession,monday): targetTasks;

        this.listOfTaskNotFinishWeeklyType = tasks.stream()
                .filter(task -> task.getType().equals(TaskType.WEEKLYTASK))
                .filter(task -> task.getCheckboxValue().equals(false))
                .collect(Collectors.toList());
    }

    public void setListOfAllTasksOneTimeType(HttpSession httpSession,LocalDate monday) {
        this.listOfAllTasksOneTimeType = userService.findTasksByMonday(httpSession,monday).stream()
                .filter(task -> task.getType().equals(TaskType.ONETIMETASK))
                .collect(Collectors.toList());
    }
    public void setListofAllTasksWeeklyType(HttpSession httpSession,LocalDate monday) {
        this.listofAllTasksWeeklyType = userService.findTasksByMonday(httpSession,monday).stream()
                .filter(task -> task.getType().equals(TaskType.WEEKLYTASK))
                .collect(Collectors.toList());
    }

    public void setNumberOfAllPoints(HttpSession httpSession, LocalDate monday) {
        List <Task> tasks = targetTasks.isEmpty()? userService.findTasksByMonday(httpSession,monday): targetTasks;

        this.numberOfAllPoints = tasks.stream()
                .map(task->task.getCategory().getValue())
                .collect(Collectors.summingInt(x->x));
    }
    public void setNumberOfPointsScored(HttpSession httpSession, LocalDate monday) {
        List <Task> tasks = targetTasks.isEmpty()? userService.findTasksByMonday(httpSession,monday): targetTasks;

        this.numberOfPointsScored =  tasks.stream()
                .filter(task -> task.getCheckboxValue().equals(true))
                .map(task->task.getCategory().getValue())
                .collect(Collectors.summingInt(x->x));
    }
    public void setProductivityResult() {
        this.productivityResult = Math.round((double) getNumberOfPointsScored() /(double)getNumberOfAllPoints()*100);
    }
    public void setListOfYear(HttpSession httpSession){

        this.listOfYear =  userService.findUserByUserId(httpSession).getTaskList().stream()
                .map(task -> task.getMonday().getYear())
                .distinct()
                .collect(Collectors.toList());
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setListofMonth(HttpSession httpSession){
        this.listOfMonth = userService.findUserByUserId(httpSession).getTaskList().stream()
                .filter(task -> task.getMonday().getYear()==this.year)
                .map (task -> task.getMonday().getMonth())
                .distinct()
                .collect(Collectors.toList());
    }
    public void setMonth(Enum month) {
        this.month = month;
    }
    public void setListofWeek(Enum month, HttpSession httpSession) {
        this.month = month;
        this.listofWeek = userService.findUserByUserId(httpSession).getTaskList().stream()
                .filter(task -> task.getMonday().getYear() == this.year)
                .filter(task -> task.getMonday().getMonth() == this.month)
                .map(task -> task.getMonday())
                .distinct()
                .flatMap(weekStart ->Stream.of(weekStart,weekStart.with(DayOfWeek.SUNDAY)))
                .sorted()
                .collect(Collectors.toList());
    }
    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }
    public void setCurrentWeek(){
        LocalDate today = LocalDate.now();
        LocalDate monday = today==today.with(DayOfWeek.MONDAY)? today.with(DayOfWeek.MONDAY):today.with(DayOfWeek.MONDAY.minus(7));
        LocalDate sunday = today.with(DayOfWeek.SUNDAY);
        this.currentWeek = "Date " + monday.toString() + " to " + sunday.toString();
    }
    public void setTargetTask(HttpSession httpSession){
        this.targetTasks = userService.findUserByUserId(httpSession).getTaskList().stream()
                .filter(task -> task.getMonday().equals(this.targetDate))
                .collect(Collectors.toList());
    }
    public void setTargetWeekDate() {
        if(targetDate!=null) {
            this.targetWeekDate = "Date " + targetDate.toString() + " to " + targetDate.plusDays(6);
        }else
            this.targetWeekDate=null;
    }

    public String getUsername() {
        return username;
    }
    public int getNumberOfAllPoints() {
        return numberOfAllPoints;
    }
    public int getNumberOfPointsScored() {
        return numberOfPointsScored;
    }
    public List<Integer> getListOfYear() {
        return listOfYear;
    }
    public List<Month> getListOfMonth() {
        return listOfMonth;
    }
    public List<LocalDate> getListofWeek() {
        return listofWeek;
    }
    public LocalDate getTargetDate() {
        return targetDate;
    }
    public int getNumberOfTaskFinished() {
        return numberOfTaskFinished;
    }
    public List<Task> getListOfTaskFinishedOneTimeType() {
        return listOfTaskFinishedOneTimeType;
    }
    public List<Task> getListOfTaskFinishedWeeklyType() {
        return listOfTaskFinishedWeeklyType;
    }
    public int getNumberOfTaskNotFinish() {
        return numberOfTaskNotFinish;
    }
    public List <Task> getListOfTaskNotFinishOneTimeType() {
        return listOfTaskNotFinishOneTimeType;
    }
    public List <Task> getListOfTaskNotFinishWeeklyType() {
        return listOfTaskNotFinishWeeklyType;
    }
    public double getProductivityResult() {
        return productivityResult;
    }
    public int getYear() {
        return year;
    }
    public Enum getMonth() {
        return month;
    }
    public String getCurrentWeek() {
        return currentWeek;
    }
    public List<Task> getTargetTask() {
        return targetTasks;
    }
    public String getTargetWeekDate() {
        return targetWeekDate;
    }
    public List<Task> getListOfAllTasksOneTimeType() {
        return listOfAllTasksOneTimeType;
    }
    public List<Task> getListofAllTasksWeeklyType() {
        return listofAllTasksWeeklyType;
    }

    public void setFields(HttpSession httpSession, LocalDate monday, int year, Enum month, LocalDate targetDate){
        setCurrentWeek();
        monday = LocalDate.parse(getCurrentWeek().substring(5,15));
        setUsername(httpSession);
        setListOfYear(httpSession);
        setYear(year);
        setListofMonth(httpSession);
        setMonth(month);
        setListofWeek(month,httpSession);
        setTargetDate(targetDate);
        setTargetWeekDate();
        setTargetTask(httpSession);
        setListofAllTasksWeeklyType(httpSession,monday);
        setListOfAllTasksOneTimeType(httpSession,monday);
        setNumberOfTaskFinished(httpSession, monday);
        setListOfTaskFinishedOneTimeType(httpSession,monday);
        setListOfTaskFinishedWeeklyType(httpSession,monday);
        setNumberOfTaskNotFinish(httpSession, monday);
        setListOfTaskNotFinishOneTimeType(httpSession,monday);
        setListOfTaskNotFinishWeeklyType(httpSession,monday);
        setNumberOfAllPoints(httpSession, monday);
        setNumberOfPointsScored(httpSession, monday);
        setProductivityResult();
    }
}

