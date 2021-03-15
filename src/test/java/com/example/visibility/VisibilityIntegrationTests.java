package com.example.visibility;

import com.example.visibility.service.VisibilityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@SpringBootTest
class VisibilityIntegrationTests {

	@Autowired
	private VisibilityService visibilityService;


	@Test
	void integrationTest() {
		assertThat(visibilityService.getVisibleProducts(), contains(5, 1, 3));
	}
}
