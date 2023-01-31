package com.cr.config;

import com.cr.repository.UserDetailsRepository;
import com.cr.service.CustomUserService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses= UserDetailsRepository.class)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	CustomUserService userService;

	@Autowired
	JWTTokenHelper jWTTokenHelper;

	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.inMemoryAuthentication().withUser("no1saroj@gmail.com").password(passwordEncoder().encode("admin")).authorities("USER", "ADMIN");
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint).and()
				.authorizeRequests((request) -> request.antMatchers("/h2-console/**", "/api/login", "/api/signup", "/api/passwordReset"
								,"/api/uploadStates", "/api/uploadCities", "/api/uploadBranch", "/api/uploadRanks", "/api/uploadRankImage").permitAll()
						.antMatchers(HttpMethod.GET, "/**").permitAll()
						.antMatchers("/swagger-ui/**", "/javainuse-openapi/**").permitAll().anyRequest().authenticated())
				.addFilterBefore(new JWTAuthenticationFilter(userService, jWTTokenHelper),
						UsernamePasswordAuthenticationFilter.class);

		http.csrf().disable().cors().and().headers().frameOptions().disable();

	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
				.info(new Info()
						.title("Credit Service")
						.description("Credit Service API")
						.version("v1.0")
						.contact(new Contact()
								.name("Saroj")
								.email("88sarojsahu@gmail.com"))
						.termsOfService("TOC")
						.license(new License().name("License").url("#"))
				);
	}
}