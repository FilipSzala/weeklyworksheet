package pl.weeklyplanner.weeklyworksheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class WeeklyWorksheetApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeeklyWorksheetApplication.class, args);
	}
}
