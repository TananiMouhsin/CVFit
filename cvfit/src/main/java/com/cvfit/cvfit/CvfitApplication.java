	package com.cvfit.cvfit;
	import org.springframework.boot.autoconfigure.domain.EntityScan;

	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;

	@SpringBootApplication
	@EntityScan(basePackages = "com.cvfit.cvfit.Backend.Entities")
	public class CvfitApplication {

		public static void main(String[] args) {
			SpringApplication.run(CvfitApplication.class, args);
		}

	}
