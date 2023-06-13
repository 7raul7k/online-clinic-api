package ro.mycode.onlineclinicapi.service;


import org.springframework.stereotype.Service;
import ro.mycode.onlineclinicapi.dto.AppointmentDTO;
import ro.mycode.onlineclinicapi.dto.CreateVisitRequest;
import ro.mycode.onlineclinicapi.exceptions.*;
import ro.mycode.onlineclinicapi.models.Appointment;
import ro.mycode.onlineclinicapi.models.Doctor;
import ro.mycode.onlineclinicapi.models.Patient;
import ro.mycode.onlineclinicapi.repo.AppointmentRepo;
import ro.mycode.onlineclinicapi.repo.DoctorRepo;
import ro.mycode.onlineclinicapi.repo.PatientRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    public AppointmentRepo appointmentRepo;

    public PatientRepo patientRepo;

    public DoctorRepo doctorRepo;


    public AppointmentService(AppointmentRepo appointmentRepo, PatientRepo patientRepo, DoctorRepo doctorRepo) {
        this.appointmentRepo = appointmentRepo;
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
    }

    public List<Appointment> getAllAppointment() {
        List<Appointment> appointments = this.appointmentRepo.getAllAppointment();

        if (appointments.isEmpty()) {
            throw new ListEmptyException();
        }

        return appointments;
    }


    public void deleteAppointment(String number){
        Optional<Appointment> appointmentOptional = this.appointmentRepo.getAppointmentByNumber(number);

        if (appointmentOptional.isEmpty()) {
            throw new AppointmentNotFoundException();
        }else{
            this.appointmentRepo.delete(appointmentOptional.get());
        }
    }

    public Appointment getAppointmentByNumber(String number){
        Optional<Appointment> appointmentOptional = this.appointmentRepo.getAppointmentByNumber(number);

        if(appointmentOptional.isEmpty()){
            throw new AppointmentNotFoundException();
        }

        return appointmentOptional.get();

    }

    public List<Appointment> getAppointmentbyType(String type){
        List<Appointment> appointmentList = this.appointmentRepo.getAppointmentByType(type);

        if(appointmentList.isEmpty()){
            throw new ListEmptyException();
        }

        return appointmentList;
    }

    public List<Appointment> getAppointmentbyDate(LocalDate date){
        List<Appointment> appointmentList = this.appointmentRepo.getAppointmentByDate(date);

        if(appointmentList.isEmpty()){
            throw new ListEmptyException();
        }

        return appointmentList;
    }

    public void createVisit(CreateVisitRequest visitRequest){
        Optional<Patient> patient = this.patientRepo.getPatientByEmail(visitRequest.getPatientEmail());

        if(patient.isEmpty()){
            throw new PatientNotFoundException();
        }

        Optional<Doctor> doctor = this.doctorRepo.getDoctorByFullName(visitRequest.getDoctorName());

        if(doctor.isEmpty()){
            throw new DoctorNotFoundException();
        }

        Appointment appointment = Appointment.builder().doctor(doctor.get())
                .date(visitRequest.getDate())
                .description(visitRequest.getDescription())
                .type(visitRequest.getType())
                .number(visitRequest.getPatientNumber())
                .build();

        this.appointmentRepo.save(appointment);
    }

}
