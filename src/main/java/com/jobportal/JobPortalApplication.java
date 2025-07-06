package com.jobportal;

import com.jobportal.model.User;
import com.jobportal.model.Role;
import com.jobportal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JobPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(UserRepository userRepository) {
		return args -> {
			if (userRepository.findByEmail("admin@example.com").isEmpty()) {
				User user = new User();
				user.setEmail("admin@example.com");
				user.setName("Admin");
				user.setRole(Role.RECRUITER);
				user.setPassword(new BCryptPasswordEncoder().encode("password123"));
				userRepository.save(user);
				System.out.println("✅ Default user created: admin@example.com / password123");
			}
		};
	}
}
