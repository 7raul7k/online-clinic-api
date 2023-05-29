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
public class DoctorDTO {

    @NotEmpty
    private String fullName;
    private String mobile;
    private String email;
    @NotEmpty
    private String adress;
    private String password;
    @NotEmpty
    private String username;


}
