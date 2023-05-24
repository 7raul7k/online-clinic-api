package ro.mycode.onlineclinicapi.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.onlineclinicapi.models.Username;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsernameRepo extends JpaRepository<Username,Long> {

    @Query("select u from usernames u where u.email = ?1")
    Optional<Username> getUsernameByEmail(String email);

    @Query("select u from usernames u where u.address = ?1")
    List<Username> getUsernameByAddress(String address);

    @Query("select u from usernames u where u.name = ?1")
    Optional<Username> getUsernameByName(String name);

    @Query("select u from usernames u")
    List<Username> getAllUsernames();

}
