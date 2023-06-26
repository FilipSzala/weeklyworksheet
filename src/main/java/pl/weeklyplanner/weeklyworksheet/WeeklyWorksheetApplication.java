package pl.weeklyplanner.weeklyworksheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class WeeklyWorksheetApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeeklyWorksheetApplication.class, args);
	}
}
