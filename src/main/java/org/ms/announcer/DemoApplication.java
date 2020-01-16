package org.ms.announcer;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// 매월 오전 8시에 실행 
	@Scheduled(cron="0 0 08 * * ?")
	public void tmpFilesDelete() {
		File file = new File("C:\\AudioStorage\\tmp");
			File[] files = file.listFiles();
			for(File eachfile : files) {
				eachfile.delete();
			}
	}
}
	