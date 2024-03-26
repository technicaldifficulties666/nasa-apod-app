package example.apod;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class SecurityConfig {

  // private final ApodUserDetailsService userDetailsService;

  // @Autowired
  // public SecurityConfig(ApodUserDetailsService userDetailsService) {
  //   this.userDetailsService = userDetailsService;
  // }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(authorize -> authorize// .requestMatchers("/api/**")
            .requestMatchers(HttpMethod.POST, "/api/auth/signin").hasRole("USER")// .anyRequest().authenticated()
            .requestMatchers("/api/apod").authenticated() //permitAll()
            .requestMatchers("/api/auth/signup").permitAll()
            .requestMatchers(HttpMethod.GET,"/api/users/**").permitAll())
        .httpBasic(Customizer.withDefaults())
        .cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable());
    // .authenticationManager(new CustomAuthenticationManager());
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // @Bean
  // public InMemoryUserDetailsManager userDetailsService() {
  // //userDetailsService.loadUserByUsername(null)
  // UserDetails user1 =
  // org.springframework.security.core.userdetails.User.withUsername("subaig")
  // .password(passwordEncoder().encode("abc123"))
  // .roles("USER")
  // .build();
  // return new InMemoryUserDetailsManager(user1);
  // }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

}
