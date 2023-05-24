package ro.mycode.onlineclinicapi.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.mycode.onlineclinicapi.OnlineClinicApiApplication;
import ro.mycode.onlineclinicapi.models.Appointment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineClinicApiApplication.class)
class AppointmentRepoTest {

    @Autowired
    public AppointmentRepo appointmentRepo;

    @BeforeEach
    public void clean(){appointmentRepo.deleteAll();}

    @Test
    public void getAllAppointment(){
        Appointment appointment = Appointment.builder().description("test").date(LocalDate.now()).number("07326482134").type("clinic").build();
        Appointment appointment1 = Appointment.builder().description("test1").date(LocalDate.now()).number("07326482134").type("clinic").build();
        Appointment appointment2 = Appointment.builder().description("test2").date(LocalDate.now()).number("07325324421").type("clinic").build();
        Appointment appointment3 = Appointment.builder().description("test3").date(LocalDate.now()).number("07326542343").type("clinic").build();

        appointmentRepo.save(appointment);
        appointmentRepo.save(appointment1);
        appointmentRepo.save(appointment2);
        appointmentRepo.save(appointment3);

        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        appointmentList.add(appointment1);
        appointmentList.add(appointment2);
        appointmentList.add(appointment3);

        assertEquals(appointmentList,this.appointmentRepo.getAllAppointment());


    }

    @Test
    public void getAppointmentByType(){


        Appointment appointment = Appointment.builder().description("test").date(LocalDate.now()).number("07326482134").type("clinic").build();
        Appointment appointment1 = Appointment.builder().description("test1").date(LocalDate.now()).number("07326482134").type("clinic").build();
        Appointment appointment2 = Appointment.builder().description("test2").date(LocalDate.now()).number("07325324421").type("clinic").build();
        Appointment appointment3 = Appointment.builder().description("test3").date(LocalDate.now()).number("07326542343").type("clinic").build();

        appointmentRepo.save(appointment);
        appointmentRepo.save(appointment1);
        appointmentRepo.save(appointment2);
        appointmentRepo.save(appointment3);

        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        appointmentList.add(appointment1);
        appointmentList.add(appointment2);
        appointmentList.add(appointment3);

        assertEquals(appointmentList,this.appointmentRepo.getAppointmentByType("clinic"));

    }

    @Test
    public void getAppointmentByDate(){
        Appointment appointment = Appointment.builder().description("test").date(LocalDate.of(2023,6,20)).number("07326482134").type("clinic").build();
        Appointment appointment1 = Appointment.builder().description("test1").date(LocalDate.of(2023,6,20)).number("07326482134").type("clinic").build();
        Appointment appointment2 = Appointment.builder().description("test2").date(LocalDate.of(2023,6,20)).number("07325324421").type("clinic").build();
        Appointment appointment3 = Appointment.builder().description("test3").date(LocalDate.of(2023,6,20)).number("07326542343").type("clinic").build();

        appointmentRepo.save(appointment);
        appointmentRepo.save(appointment1);
        appointmentRepo.save(appointment2);
        appointmentRepo.save(appointment3);

        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        appointmentList.add(appointment1);
        appointmentList.add(appointment2);
        appointmentList.add(appointment3);

        assertEquals(appointmentList,this.appointmentRepo.getAppointmentByDate(LocalDate.of(2023,6,20)));
    }

    @Test
    public void getAppointmentByNumber(){
        Appointment appointment = Appointment.builder().description("test").date(LocalDate.of(2023,6,20)).number("07326482134").type("clinic").build();

        appointmentRepo.save(appointment);



        assertEquals(appointment,this.appointmentRepo.getAppointmentByNumber("07326482134").get());
    }
}