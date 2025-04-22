package com.training.expenseTracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"frontend.origin=http://example.com"})
public class ExpenseTrackerApplicationTest {

	@Value("${frontend.origin}")
	private String frontendOrigin;

	private final ApplicationContext context;

	public ExpenseTrackerApplicationTest(ApplicationContext context) {
		this.context = context;
	}

	@Test
	public void contextLoads() {
		assertNotNull(context);
	}

	@Test
	public void testCorsConfigurerBean() {
		ExpenseTrackerApplication app = new ExpenseTrackerApplication();
		WebMvcConfigurer configurer = app.corsConfigurer();
		assertNotNull(configurer);

		// Test that addCorsMappings can be called without error
		CorsRegistry registry = new CorsRegistry();
		configurer.addCorsMappings(registry);

		// Since CorsRegistry does not expose its mappings publicly,
		// we check that the configurer is not null and no exceptions are thrown.
		assertNotNull(configurer);
	}
}
