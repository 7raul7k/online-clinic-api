package ro.mycode.onlineclinicapi.service;


import org.springframework.stereotype.Service;
import ro.mycode.onlineclinicapi.exceptions.AppointmentNotFoundException;
import ro.mycode.onlineclinicapi.exceptions.AppointmentWasFoundException;
import ro.mycode.onlineclinicapi.exceptions.ListEmptyException;
import ro.mycode.onlineclinicapi.models.Appointment;
import ro.mycode.onlineclinicapi.repo.AppointmentRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    public AppointmentRepo appointmentRepo;

    public AppointmentService(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    public List<Appointment> getAllAppointment() {
        List<Appointment> appointments = this.appointmentRepo.getAllAppointment();

        if (appointments.isEmpty()) {
            throw new ListEmptyException();
        }

        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        Optional<Appointment> appointmentOptional = this.appointmentRepo.getAppointmentByNumber(appointment.getNumber());

        if (appointmentOptional.isEmpty()) {
            this.appointmentRepo.save(appointment);
        } else {
            throw new AppointmentWasFoundException();
        }
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
            throw new ListEmptyException();
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

}
