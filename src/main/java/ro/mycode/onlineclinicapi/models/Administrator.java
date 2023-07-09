package ro.mycode.onlineclinicapi.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "admins")
@Entity(name = "Admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Administrator {

    @Id
    @SequenceGenerator(name = "admin_sequence",
            sequenceName = "admin_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,
            generator = "admin_sequence")
    @Column(name = "id")
    private Long id;
    @Column(name="full_name",
            nullable = false,
    columnDefinition = "TEXT")
    private String fullName;
    @Column(name="email",
    nullable = false,
    columnDefinition = "TEXT")
    private String email;
    @Column(name="dob",
    nullable = false,
    columnDefinition = "TEXT")
    private LocalDate dob;
    @Column(name ="username",
    nullable = false,
    columnDefinition = "TEXT")
    private String username;

    @Override
    public String toString(){
        return fullName+" "+email+" "+dob+" "+username;
    }

    @Override
    public boolean equals(Object obj){

        Administrator administrator = (Administrator) obj;

        if(administrator.fullName.equals(this.fullName)&& administrator.username.equals(this.username)&& administrator.email.equals(this.email)){
            return true;
        }

        return false;
    }
}
