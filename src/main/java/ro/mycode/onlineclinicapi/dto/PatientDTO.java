package ro.mycode.onlineclinicapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PatientDTO {

    @NotEmpty
    private String fullName;
    private String number;
    @NotEmpty
    private String adress;
    @NotEmpty
    private String email;
    private String password;
    @NotEmpty
    private String username;


}
