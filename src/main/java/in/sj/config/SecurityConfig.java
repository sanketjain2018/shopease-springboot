
package in.sj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())

		.authorizeHttpRequests(auth -> auth

			    // PUBLIC
			    .requestMatchers("/", "/login", "/register","/forgot-password", "/reset-password", "/css/**", "/js/**", "/uploads/**").permitAll()

			    // SHOP (NORMAL USER)
			    .requestMatchers("/shop").hasAnyRole("USER", "ADMIN")

			    // USER PAGES (ORDERS, CART, ETC)
			    .requestMatchers("/user/**").hasRole("USER")

			    // ADMIN PRODUCT MANAGEMENT
			    .requestMatchers("/admin/**", "/products-ui/add", "/products-ui/save", "/products-ui/edit/**",
			            "/products-ui/update", "/products-ui/delete/**")
			    .hasRole("ADMIN")

			    // PRODUCT LIST (TABLE VIEW)
			    .requestMatchers("/products-ui").hasAnyRole("ADMIN")

			    .anyRequest().authenticated())


				.formLogin(form -> form.loginPage("/login").successHandler((request, response, authentication) -> {

					var authorities = authentication.getAuthorities();

					boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

					if (isAdmin) {
						response.sendRedirect("/admin/dashboard");
					} else {
						response.sendRedirect("/shop");
					}
				}).permitAll())

				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
						.deleteCookies("JSESSIONID").permitAll());

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
