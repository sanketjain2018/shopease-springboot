package in.sj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringbootPageSortApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootPageSortApplication.class, args);
		
		/*
		 * BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		 * System.out.println(encoder.encode("admin123"));  username : admin  pass : admin123 
		 */


	}

}
