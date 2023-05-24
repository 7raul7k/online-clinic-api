package ro.mycode.onlineclinicapi.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.onlineclinicapi.models.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {


    @Query("select a from Appointment a where a.number = ?1")
    Optional<Appointment> getAppointmentByNumber(String number);

    @Query("select a from Appointment a where a.date = ?1")
    List<Appointment> getAppointmentByDate(LocalDate date);

    @Query("select a from Appointment a where a.type = ?1")
    List<Appointment> getAppointmentByType(String type);

    @Query("select a from Appointment a")
    List<Appointment> getAllAppointment();

}
