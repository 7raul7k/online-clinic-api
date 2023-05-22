package ro.mycode.onlineclinicapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.onlineclinicapi.models.Clinic;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicRepo  extends JpaRepository<Clinic,Long> {

    @Query("select c from Clinic c where c.name = ?1")
    Optional<Clinic> getClinicByName(String name);

    @Query("select c from Clinic c where c.type = ?1")
    List<Clinic> getClinicByType(String type);

    @Query("select c from Clinic c where c.place = ?1")
    List<Clinic> getClinicByPlace(String place);
}
