package ro.mycode.onlineclinicapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.onlineclinicapi.models.TestPatient;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestPatientRepo extends JpaRepository<TestPatient,Long> {


    @Query("select t from Test t where t.name = ?1")
    Optional<TestPatient> getTestByName(String name);

    @Query("select t from Test t where t.report = ?1")
    List<TestPatient> getTestByReport(String report);
    @Query("select t from Test t where t.type = ?1")
    List<TestPatient> getTestByType(String type);

    @Query("select t from Test t")
    List<TestPatient> getAllTest();

}
