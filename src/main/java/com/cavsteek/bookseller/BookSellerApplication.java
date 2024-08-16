package com.cavsteek.bookseller;

import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.model.User;
import com.cavsteek.bookseller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
@RequiredArgsConstructor
public class BookSellerApplication implements CommandLineRunner {

	private final UserRepository userRepository;



	public static void main(String[] args) {
		try{
			SpringApplication.run(BookSellerApplication.class, args);
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public void run(String... args) throws Exception {
		User adminAccount = userRepository.findByRole(Role.ADMIN);
			if (null == adminAccount) {
				User admin = new User();
				admin.setUsername("Admin");
				admin.setFirstName("Hober");
				admin.setLastName("Malo");
				admin.setEmail("admin@gmail.com");
				admin.setRole(Role.ADMIN);
				admin.setCreateTime(LocalDateTime.now());
				admin.setPassword(new BCryptPasswordEncoder().encode("iadmin"));
				userRepository.save(admin);
				System.out.println("New admin created successfully!");
			} else {
				System.out.println("Admin with username 'Admin' already exists. Skipping creation.");
			}
	}
}
