package example.apod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.json.JSONException;
import org.json.JSONObject;

//import org.h2.util.json.JSONObject;

//import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApodApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnAnApodUserWhenDataIsSaved() {
	ResponseEntity<String> response = restTemplate.getForEntity("/api/users/99",
	String.class);
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	DocumentContext documentContext = JsonPath.parse(response.getBody());
	//String jsonString = documentContext.jsonString(); // Convert JsonContext to JSON string
	//System.out.println("JSON Data: " + jsonString);
	Number id = documentContext.read("$.id");
	assertThat(id).isEqualTo(99);

	String username = documentContext.read("$.username");
	assertThat(username).isEqualTo("subaig");
	}

	@Test
	void shouldNotReturnAnApodUserWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/api/users/1000", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		assertThat(response.getBody()).isBlank();
	}

	// @Test
	// @DirtiesContext
	// void shouldCreateANewApodUser() {
	// ApodUser newApodUser = new ApodUser(null, "newuser", "pass123");
	// ResponseEntity<Void> createResponse =
	// restTemplate.postForEntity("/api/users", newApodUser, Void.class);
	// assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

	// URI locationOfNewApodUser = createResponse.getHeaders().getLocation();
	// ResponseEntity<String> getResponse =
	// restTemplate.getForEntity(locationOfNewApodUser, String.class);
	// assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

	// DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
	// Number id = documentContext.read("$.id");
	// String username = documentContext.read("$.username");
	// String password = documentContext.read("$.password");

	// assertThat(id).isNotNull();
	// assertThat(username).isEqualTo("newuser");
	// assertThat(password).isEqualTo("pass123");

	// }

	@SuppressWarnings("null")
	@Test
	void shouldReturnApodWhenRequested() {
		MultiValueMap<String, String> loginData = new LinkedMultiValueMap<>();
		loginData.add("username", "subaig");
		loginData.add("password", "abc123");
		ResponseEntity<String> loginResponse = restTemplate.postForEntity("/api/auth/signin", loginData, String.class);
		if (loginResponse.getStatusCode() == HttpStatus.OK) {
			String authToken = loginResponse.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

			// Include authentication token in request headers
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(authToken);

			// Make request to /api/apod endpoint with authentication token
			// ResponseEntity<?> responseEntity = restTemplate.getForEntity("/api/apod",
			// Optional.class, headers);
			ResponseEntity<?> responseEntity = restTemplate.exchange("/api/apod", HttpMethod.GET, new HttpEntity<>(headers),
					Optional.class);
			// ResponseEntity<?> responseEntity = restTemplate.getForEntity("/api/apod",
			// Optional.class);

			assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

			Optional<?> responseBodyOptional = (Optional<?>) responseEntity.getBody();

			if (responseBodyOptional != null) {
				String responseBody = responseBodyOptional.map(Object::toString).orElse("");
				try {
					JSONObject jsonObject = new JSONObject(responseBody);

					String date = jsonObject.getString("date");
					String explanation = jsonObject.getString("explanation");
					String url = jsonObject.getString("url");

					assertThat(date).isNotNull();
					assertThat(explanation).isNotNull();
					assertThat(url).isNotNull();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	void shouldReturnAllApodUsersWhenListIsRequested() {
		ResponseEntity<String> response = restTemplate.getForEntity("/api/users",
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		// DocumentContext documentContext = JsonPath.parse(response.getBody());
		// int cashCardCount = documentContext.read("$.length()");
		// assertThat(cashCardCount).isEqualTo(1);

		// JSONArray ids = documentContext.read("$..id");
		// assertThat(ids).containsExactlyInAnyOrder(99);

		// JSONArray amounts = documentContext.read("$..username");
		// assertThat(amounts).containsExactlyInAnyOrder("subaig");
	}

	@Test
	@DirtiesContext
	void shouldCreateANewApodUserUponSignUp() {
		ApodUser newApodUser = new ApodUser(null, "newuser", "pass123");
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/api/auth/signup", newApodUser, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		// URI locationOfNewApodUser = createResponse.getHeaders().getLocation();
		// ResponseEntity<String> getResponse =
		// restTemplate.getForEntity(locationOfNewApodUser, String.class);
		// assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		// DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		// Number id = documentContext.read("$.id");
		// String username = documentContext.read("$.username");
		// String password = documentContext.read("$.password");

		// assertThat(id).isNotNull();
		// assertThat(username).isEqualTo("newuser");
		// assertThat(password).isEqualTo("pass123");

	}

	@Test
	void shouldAuthenticateAnApodUserUponLoginWhenDataIsSaved() {
		// ResponseEntity<String> response = restTemplate.getForEntity("/api/users/99",
		// String.class);
		ApodUser newApodUser = new ApodUser(null, "subaig", "abc123");
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/api/auth/signup", newApodUser, Void.class);

		// ResponseEntity<String> response = restTemplate.getForEntity("/api/users/99",
		// String.class);
		// assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		// System.out.println("response: " + response);
		if (createResponse.getStatusCode() == HttpStatus.CREATED) {
			ResponseEntity<Void> loginResponse = restTemplate.postForEntity("/api/auth/signin", createResponse, Void.class);
			assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		}

		// DocumentContext documentContext = JsonPath.parse(response.getBody());
		// //String jsonString = documentContext.jsonString(); // Convert JsonContext to
		// JSON string
		// //System.out.println("JSON Data: " + jsonString);
		// Number id = documentContext.read("$.id");
		// assertThat(id).isEqualTo(99);

		// String username = documentContext.read("$.username");
		// assertThat(username).isEqualTo("subaig");
	}

}
