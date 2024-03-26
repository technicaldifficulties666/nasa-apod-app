package example.apod;

import org.springframework.data.annotation.Id;

record ApodUser(@Id Long id, String username, String password) {
  // Accessor method for username
  public String getUsername() {
    return username;
  }

  // Accessor method for password
  public String getPassword() {
    return password;
  }

}