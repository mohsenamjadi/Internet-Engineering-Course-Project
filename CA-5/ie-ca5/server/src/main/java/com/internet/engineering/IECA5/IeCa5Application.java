package com.internet.engineering.IECA5;

import com.internet.engineering.IECA5.services.CourseEnrolmentService;
import com.internet.engineering.IECA5.utils.schedulers.SecJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class IeCa5Application {


	public static void main(String[] args) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		try {
			CourseEnrolmentService.getInstance().importCoursesFromWeb();
			CourseEnrolmentService.getInstance().importStudentsFromWeb();
			scheduler.scheduleAtFixedRate(new SecJob(), 0, 60, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication.run(IeCa5Application.class, args);
	}
}
