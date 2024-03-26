package example.apod;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private ApodUserRepository userRepository;

  // @Autowired
  // private ApodUserDetailsService userDetailsService;

  private AuthController(ApodUserRepository userRepository, AuthenticationManager authenticationManager,
      ApodUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    // this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;

  }

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/signin")
  public ResponseEntity<String> authenticateUser(@RequestBody ApodUser loginData, Principal principal) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return ResponseEntity.ok("User signed-in successfully!");
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
    // Authentication authentication = authenticationManager.authenticate(new
    // UsernamePasswordAuthenticationToken(
    // loginData.getUsername(), loginData.getPassword()));

    // SecurityContextHolder.getContext().setAuthentication(authentication);
    // return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody ApodUser signUpData, UriComponentsBuilder ucb) {
    // add check for username exists in a DB
    if (userRepository.existsByUsername(signUpData.getUsername())) {
      return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
    }

    // create user object
    ApodUser user = new ApodUser(null, signUpData.getUsername(), passwordEncoder.encode(signUpData.getPassword()));

    userRepository.save(user);
    // URI locationOfNewApodUser = ucb
    // .path("/api/users/{id}")
    // .buildAndExpand(user.id())
    // .toUri();
    // return ResponseEntity.created(locationOfNewApodUser).body("User registered
    // successfully");

    return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);

  }

}
