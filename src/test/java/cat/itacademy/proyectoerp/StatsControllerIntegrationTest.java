package cat.itacademy.proyectoerp;

import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This class testing the Stats Endpoints. We load some data directly to the test database to work with it. 
 * And drop the test database after it.
 */

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Sql(scripts = "/stats-integration-test-insert-data.sql")
@Sql(scripts = "/stats-integration-test-clean-up-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StatsControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	IOrderRepository orderRepository;

	@Test
	public void checkSQLData() {
		assertThat(userRepository.existsById((long) 900000001)).isTrue();
		assertThat(orderRepository.existsById(UUID.fromString("11110000-0000-0000-0000-000000000000"))).isTrue();
	}
	
	//TODO GET: /api/stats/employees/sells
	
	//TODO GET: /api/stats/employees/toptensales
	
	//TODO GET: /api/stats/employees/bestsales
	
	//TODO GET: /api/stats/employees/worstsales
	
	//TODO [Pendiente Pull Request B-71] GET: /api/stats/profits/{year}/
	
	//TODO [Pendiente Pull Request B-71] GET: /api/stats/profits/{year}/{month}
	
	//TODO [Pendiente Pull Request B-71] GET: /api/stats/salaries/year
	
	//TODO [Pendiente Pull Request B-71] GET: /api/stats/salaries/month
}
