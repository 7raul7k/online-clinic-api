package ro.mycode.onlineclinicapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateVisitRequest {

    @NotEmpty
    private String patientEmail;

    @NotEmpty
    private String patientNumber;

    private String description;

    private LocalDate date;

    @NotEmpty
    private String doctorName;

    private String type;
}
