package ro.mycode.onlineclinicapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.onlineclinicapi.dto.CreateVisitRequest;
import ro.mycode.onlineclinicapi.exceptions.*;
import ro.mycode.onlineclinicapi.models.Appointment;
import ro.mycode.onlineclinicapi.models.Doctor;
import ro.mycode.onlineclinicapi.models.Patient;
import ro.mycode.onlineclinicapi.repo.AppointmentRepo;
import ro.mycode.onlineclinicapi.repo.DoctorRepo;
import ro.mycode.onlineclinicapi.repo.PatientRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    AppointmentRepo appointmentRepo;

    @Mock
    PatientRepo patientRepo;

    @Mock
    DoctorRepo doctorRepo;

    @InjectMocks
    AppointmentService appointmentService;

    @Captor
    ArgumentCaptor<Appointment> argumentCaptor;


    @Test
    public void getAllAppointment() {
        Appointment appointment = Appointment.builder().description("test").date(LocalDate.of(2023, 6, 20)).number("07326482134").type("clinic").build();
        Appointment appointment1 = Appointment.builder().description("test1").date(LocalDate.of(2023, 6, 20)).number("07326482134").type("clinic").build();
        Appointment appointment2 = Appointment.builder().description("test2").date(LocalDate.of(2023, 6, 20)).number("07325324421").type("clinic").build();
        Appointment appointment3 = Appointment.builder().description("test3").date(LocalDate.of(2023, 6, 20)).number("07326542343").type("clinic").build();


        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        appointmentList.add(appointment1);
        appointmentList.add(appointment2);
        appointmentList.add(appointment3);

        doReturn(appointmentList).when(appointmentRepo).getAllAppointment();

        assertEquals(appointmentList, this.appointmentRepo.getAllAppointment());

    }

    @Test
    public void getAllAppointmentException() {
        doReturn(new ArrayList<>()).when(appointmentRepo).getAllAppointment();

        assertThrows(ListEmptyException.class, () -> {
            this.appointmentService.getAllAppointment();
        });
    }

    @Test
    public void addAppointment() {
        Appointment appointment = Appointment.builder().description("test").date(LocalDate.of(2023, 6, 20)).number("07326482134").type("clinic").build();

        doReturn(Optional.empty()).when(appointmentRepo).getAppointmentByNumber(appointment.getNumber());

        this.appointmentService.addAppointment(appointment);

        verify(appointmentRepo, times(1)).save(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(), appointment);


    }

    @Test
    public void addAppointmentException() {

        doReturn(Optional.of(new Appointment())).when(appointmentRepo).getAppointmentByNumber("test");

        assertThrows(AppointmentWasFoundException.class, () -> {
            this.appointmentService.addAppointment(Appointment.builder().number("test").build());
        });


    }

    @Test
    public void removeAppointment() {
        Appointment appointment = Appointment.builder().description("test").date(LocalDate.of(2023, 6, 20)).number("07326482134").type("clinic").build();

        doReturn(Optional.of(appointment)).when(appointmentRepo).getAppointmentByNumber(appointment.getNumber());

        this.appointmentService.deleteAppointment(appointment.getNumber());

        verify(appointmentRepo, times(1)).delete(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(), appointment);
    }

    @Test
    public void removeAppointmentException() {
        doReturn(Optional.empty()).when(appointmentRepo).getAppointmentByNumber("test");

        assertThrows(AppointmentNotFoundException.class, () -> {
            this.appointmentService.deleteAppointment("test");
        });
    }

    @Test
    public void getAppointmentbyNumber() {
        Appointment appointment = Appointment.builder().description("test").date(LocalDate.of(2023, 6, 20)).number("07326482134").type("clinic").build();

        doReturn(Optional.of(appointment)).when(appointmentRepo).getAppointmentByNumber(appointment.getNumber());


        assertEquals(appointment, this.appointmentService.getAppointmentByNumber("07326482134"));

    }

    @Test
    public void getAppointmentbyNumberException() {

        doReturn(Optional.empty()).when(appointmentRepo).getAppointmentByNumber("test");


        assertThrows(AppointmentNotFoundException.class, () -> {
            this.appointmentService.getAppointmentByNumber("test");
        });

    }

    @Test
    public void getAppointmentByType() {

        Appointment appointment = Appointment.builder().description("test").date(LocalDate.now()).number("07326482134").type("clinic").build();
        Appointment appointment1 = Appointment.builder().description("test1").date(LocalDate.now()).number("07326482134").type("clinic").build();
        Appointment appointment2 = Appointment.builder().description("test2").date(LocalDate.now()).number("07325324421").type("clinic").build();
        Appointment appointment3 = Appointment.builder().description("test3").date(LocalDate.now()).number("07326542343").type("clinic").build();


        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        appointmentList.add(appointment1);
        appointmentList.add(appointment2);
        appointmentList.add(appointment3);

        doReturn(appointmentList).when(appointmentRepo).getAppointmentByType(appointment.getType());

        assertEquals(appointmentList, this.appointmentService.getAppointmentbyType(appointment.getType()));
    }

    @Test
    public void getAppointmentByTypeException() {
        doReturn(new ArrayList<>()).when(appointmentRepo).getAppointmentByType("test");

        assertThrows(ListEmptyException.class, () -> {
            this.appointmentService.getAppointmentbyType("test");
        });

    }

    @Test
    public void getAppointmentbyDate() {

        Appointment appointment = Appointment.builder().description("test").date(LocalDate.of(2023, 6, 20)).number("07326482134").type("clinic").build();
        Appointment appointment1 = Appointment.builder().description("test1").date(LocalDate.of(2023, 6, 20)).number("07326482134").type("clinic").build();
        Appointment appointment2 = Appointment.builder().description("test2").date(LocalDate.of(2023, 6, 20)).number("07325324421").type("clinic").build();
        Appointment appointment3 = Appointment.builder().description("test3").date(LocalDate.of(2023, 6, 20)).number("07326542343").type("clinic").build();

        appointmentRepo.save(appointment);
        appointmentRepo.save(appointment1);
        appointmentRepo.save(appointment2);
        appointmentRepo.save(appointment3);

        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        appointmentList.add(appointment1);
        appointmentList.add(appointment2);
        appointmentList.add(appointment3);

        doReturn(appointmentList).when(appointmentRepo).getAppointmentByDate(LocalDate.of(2023, 6, 20));

        assertEquals(appointmentList,this.appointmentService.getAppointmentbyDate(LocalDate.of(2023, 6, 20)));
    }

    @Test
    public void getAppointmentByDateException(){
        doReturn(new ArrayList<>()).when(appointmentRepo).getAppointmentByDate(LocalDate.of(2023,1,10));

        assertThrows(ListEmptyException.class,()->{
            this.appointmentService.getAppointmentbyDate(LocalDate.of(2023,1,10));
        });
    }

    @Test
    public void createVisit(){

        Doctor doctor = Doctor.builder().fullName("David Moisescu").adress("Bucuresti").email("davidmoisescu@gmail.com").password("avidmoisescu@gmail.com2023").username("davidmoise").mobile("0732952713").build();

        Patient patient = Patient.builder().fullName("Cristi Dinescu").adress("Baia Mare").number("0758932325").email("cristidinescu@gmail.com").password("cristidinescu@gmail.com2023").username("cristicristian").build();


        CreateVisitRequest visitRequest  = CreateVisitRequest.builder().description("test").doctorName(doctor.getFullName()).patientNumber(patient.getNumber()).date(LocalDate.now()).patientEmail(patient.getEmail()).build();

        doReturn(Optional.of(patient)).when(patientRepo).getPatientByEmail(patient.getEmail());

        doReturn(Optional.of(doctor)).when(doctorRepo).getDoctorByFullName("David Moisescu");

        appointmentService.createVisit(visitRequest);

        Appointment appointment = Appointment.builder().doctor(doctor)
                .date(visitRequest.getDate())
                .description(visitRequest.getDescription())
                .type(visitRequest.getType())
                .number(visitRequest.getPatientNumber())
                .build();

        verify(appointmentRepo,times(1)).save(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),appointment);
    }

    @Test
    public void createVisitPatientException(){
        doReturn(Optional.empty()).when(patientRepo).getPatientByEmail("test");

        assertThrows(PatientNotFoundException.class,()->{
            this.appointmentService.createVisit(CreateVisitRequest.builder().patientEmail("test").build());
        });
    }

    @Test
    public void createVisitDoctorException(){
        doReturn(Optional.of(new Patient())).when(patientRepo).getPatientByEmail("test");
        doReturn(Optional.empty()).when(doctorRepo).getDoctorByFullName("test");

        assertThrows(DoctorNotFoundException.class,()->{
            this.appointmentService.createVisit(CreateVisitRequest.builder().patientEmail("test").doctorName("test").build());
        });
    }
}