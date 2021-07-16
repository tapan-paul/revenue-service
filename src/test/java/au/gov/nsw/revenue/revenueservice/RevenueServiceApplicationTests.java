package au.gov.nsw.revenue.revenueservice;

import au.gov.nsw.revenue.revenueservice.controller.PersonController;
import au.gov.nsw.revenue.revenueservice.controller.VehicleController;
import au.gov.nsw.revenue.revenueservice.domain.Person;
import au.gov.nsw.revenue.revenueservice.domain.PersonVehicleRequest;
import au.gov.nsw.revenue.revenueservice.domain.Vehicle;
import au.gov.nsw.revenue.revenueservice.exception.RevenueException;
import au.gov.nsw.revenue.revenueservice.repository.PersonVehicleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RevenueServiceApplicationTests {

	@Autowired
	private VehicleController vehicleController;

	@Autowired
	private PersonController personController;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private HttpHeaders headers;
	private final ObjectMapper objectMapper = new ObjectMapper();


	@BeforeEach
	void contextLoads() {
		assertThat(vehicleController).isNotNull();
		assertThat(personController).isNotNull();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
				String.class)).contains("Welcome to REVENUE NSW");
	}

	@Test
	public void addPerson() throws JsonProcessingException {
		Person person = getPerson();
		HttpEntity<Person> request =
				new HttpEntity<Person>(person, headers);

		String personResultAsJsonStr =
				restTemplate.postForObject("http://localhost:" + port + "/add-person", request, String.class);
		JsonNode root = objectMapper.readTree(personResultAsJsonStr);
		assertThat(root.asBoolean()).isEqualTo(true);
	}

	private Person getPerson() {
		Person person = new Person();
		person.setId(1);
		person.setFirstName("ABC");
		person.setLastName("XYZ");
		person.setSalutation("Mr");
		return person;
	}

	@Test
	public void addVehicle() throws JsonProcessingException {
		Vehicle vehicle = getVehicleBMW();
		HttpEntity<Vehicle> request =
				new HttpEntity<Vehicle>(vehicle, headers);

		String vehicleResultAsJsonStr =
				restTemplate.postForObject("http://localhost:" + port + "/add-vehicle", request, String.class);
		JsonNode root = objectMapper.readTree(vehicleResultAsJsonStr);
		assertThat(root.asBoolean()).isEqualTo(true);
	}

	@Test
	public void addVehicleAlreadyExisting() throws JsonProcessingException {
		Vehicle vehicle = getVehicleBMW();

		HttpEntity<Vehicle> request =
				new HttpEntity<Vehicle>(vehicle, headers);

		String vehicleResultAsJsonStr =
				restTemplate.postForObject("http://localhost:" + port + "/add-vehicle", request, String.class);
		JsonNode root = objectMapper.readTree(vehicleResultAsJsonStr);
		assertThat(root.asBoolean()).isEqualTo(false);
	}



	@Test
	public void linkTwoVehiclesToCustomer() throws JsonProcessingException {

		PersonVehicleRequest request = new
				PersonVehicleRequest();
		request.setVehicle(getVehicleBMW());
		request.setPerson(getPerson());

		HttpEntity<PersonVehicleRequest> httpEntity =
				new HttpEntity<PersonVehicleRequest>(request, headers);

		restTemplate.postForObject("http://localhost:" + port + "/register-vehicle",httpEntity, String.class);

		request = new
				PersonVehicleRequest();
		request.setVehicle(getVehicleToyota());
		request.setPerson(getPerson());

		httpEntity =
				new HttpEntity<PersonVehicleRequest>(request, headers);

		restTemplate.postForObject("http://localhost:" + port + "/register-vehicle",httpEntity, String.class);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromUriString("http://localhost:" + port + "/get-vehicles")
				.queryParam("id", "1");

		Collection response = restTemplate.getForObject(builder.toUriString(), Collection.class);
		assertThat(response).size().isEqualTo(2); // linked two vehicles to the same person

	}

	@Test
	public void unlinkVehicleFromCustomerAfterRegistering() {

		PersonVehicleRequest request = new
				PersonVehicleRequest();
		request.setVehicle(getVehicleBMW());
		request.setPerson(getPerson());

		HttpEntity<PersonVehicleRequest> httpEntity =
				new HttpEntity<PersonVehicleRequest>(request, headers);

		restTemplate.postForObject("http://localhost:" + port + "/register-vehicle",httpEntity, String.class);

		request = new
				PersonVehicleRequest();
		request.setVehicle(getVehicleToyota());
		request.setPerson(getPerson());

		httpEntity =
				new HttpEntity<PersonVehicleRequest>(request, headers);

		restTemplate.postForObject("http://localhost:" + port + "/register-vehicle", httpEntity, String.class);

		boolean s = restTemplate.postForObject("http://localhost:" + port + "/unregister-vehicle", httpEntity, Boolean.class);
		assertThat(s).isEqualTo(true);

	}

	@Test()
	public void addEmptyPersonShouldThrowException() {
		PersonVehicleRepository repository = new PersonVehicleRepository();
		Optional<Person> person= Optional.empty();
		Assertions.assertThrows(RevenueException.class, ()->repository.addPerson(person));
	}


	private Vehicle getVehicleBMW() {
		Vehicle vehicle = new Vehicle();
		vehicle.setMake("BMW");
		vehicle.setRego("123-XYZ");
		vehicle.setModel("140i");
		vehicle.setYear(2017);
		return vehicle;
	}

	private Vehicle getVehicleToyota() {
		Vehicle vehicle = new Vehicle();
		vehicle.setMake("Toyota");
		vehicle.setRego("456-XYZ");
		vehicle.setModel("Camry");
		vehicle.setYear(2017);
		return vehicle;
	}
}
