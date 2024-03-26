package example.apod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
class ApodUserController {
  private final ApodUserRepository apodUserRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  RestTemplate restTemplate = new RestTemplate();

  private ApodUserController(ApodUserRepository apodUserRepository, PasswordEncoder passwordEncoder) {
    this.apodUserRepository = apodUserRepository;
    this.passwordEncoder = passwordEncoder;

  }

  @GetMapping(value = "/apod", produces = "application/json")
  private ResponseEntity<?> fetchApod(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
      Authentication authentication) {
    final String apiKey = "yjH9IERTr02Yi7k3c4jLeD0qOIVfgwJhqYi1FXFY"; //apiKeyProvider.getApiKey();//
    final String baseUrl = "https://api.nasa.gov/planetary/apod";
    // final String uri =
    // "https://api.nasa.gov/planetary/apod?api_key=yjH9IERTr02Yi7k3c4jLeD0qOIVfgwJhqYi1FXFY";
    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
    } else {

      try {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .queryParam("api_key", apiKey);
        if (date != null) {
          String formattedDate = date.format(DateTimeFormatter.ISO_DATE);
          builder.queryParam("date", formattedDate);
        }
        String uri = builder.toUriString();
        Optional<?> result = Optional.ofNullable(restTemplate.getForObject(uri, Optional.class)); // restTemplate.getForObject(uri,
                                                                                                  // Optional.class);
        return ResponseEntity.ok(result);
      } catch (Exception e) {
        return ResponseEntity.notFound().build();
      }
    }
  }

  @GetMapping("/users/{requestedId}") //get one
  private ResponseEntity<ApodUser> findById(@PathVariable Long requestedId) {
    @SuppressWarnings("null")
    Optional<ApodUser> userOptional = apodUserRepository.findById(requestedId);
    if (userOptional.isPresent()/* user != null */) {
      return ResponseEntity.ok(userOptional.get());
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // notFound().build();
    }
  }

  @GetMapping("/users") // Get all
  private ResponseEntity<Iterable<ApodUser>> findAll(/* , Principal principal */) {
    // Page<ApodUser> page = ApodUserRepository.findAll(); //
    // findByOwner(principal.getName(), // cashCardRepository.findAll(
    Iterable<ApodUser> users = apodUserRepository.findAll();
    return ResponseEntity.ok(users);
  }

  @PostMapping("/users")
  private ResponseEntity<String> createAnApodUser(@RequestBody ApodUser newApodUserRequest, UriComponentsBuilder ucb) {
    if (apodUserRepository.existsByUsername(newApodUserRequest.username())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body("Username should be unique. Choose a different username and try again");
    } else {
      ApodUser processRequest = new ApodUser(null, newApodUserRequest.getUsername(), passwordEncoder.encode(newApodUserRequest.getPassword()));
      ApodUser savedApodUser = apodUserRepository.save(processRequest);
      URI locationOfNewApodUser = ucb
          .path("/api/users/{id}")
          .buildAndExpand(savedApodUser.id())
          .toUri();
      return ResponseEntity.created(locationOfNewApodUser).build();
    }
  }

}
