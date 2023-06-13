package ro.mycode.onlineclinicapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ro.mycode.onlineclinicapi.models.Doctor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AppointmentDTO {

    private String number;
    private String type;
    private String description;
    private LocalDate date;

    private Doctor doctor;


}
