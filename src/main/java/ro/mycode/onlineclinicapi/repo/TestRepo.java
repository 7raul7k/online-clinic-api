package ro.mycode.onlineclinicapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.onlineclinicapi.models.Test;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepo extends JpaRepository<Test,Long> {


    @Query("select t from Test t where t.patient.fullName = ?1")
    Optional<Test> getTestByPatientFullName(String patientName);

    @Query("select t from Test t where t.name = ?1")
    Optional<Test> getTestByName(String name);

    @Query("select t from Test t where t.report = ?1")
    List<Test> getTestByReport(String report);
    @Query("select t from Test t where t.type = ?1")
    List<Test> getTestByType(String type);

}
