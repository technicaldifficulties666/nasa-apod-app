package example.apod;

import org.springframework.data.repository.CrudRepository;

/**
 * UserRepository
 */
interface ApodUserRepository extends CrudRepository<ApodUser, Long> {
  //Optional<ApodUser> findById(Long id);
  //User findById(Long id);
  //User findByIdAndUsername(Long id, String username);

  //boolean existsByIdAndUsername(Long id, String owner);
  boolean existsByUsername(String username);

  ApodUser findByUsername(String username);

  //boolean existsByUsername(Object username);
  
}

