package com.internet.engineering.IECA8;

import com.internet.engineering.IECA8.repository.Repository;
import com.internet.engineering.IECA8.services.CourseEnrolmentService;
import com.internet.engineering.IECA8.utils.schedulers.SecJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class IeCa5Application {


	public static void main(String[] args) {
		Repository.getInstance().createAllTables();
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		try {
			CourseEnrolmentService.getInstance().importCoursesFromWeb();
			CourseEnrolmentService.getInstance().importStudentsFromWeb();
			scheduler.scheduleAtFixedRate(new SecJob(), 0, 15, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication.run(IeCa5Application.class, args);
	}
}
