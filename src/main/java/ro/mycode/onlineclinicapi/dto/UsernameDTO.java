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
public class UsernameDTO {

    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    private LocalDate dob;
    @NotEmpty
    private String address;
}
