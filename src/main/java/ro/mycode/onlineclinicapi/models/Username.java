package ro.mycode.onlineclinicapi.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "usernames")
@Table(name = "Username")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Username {
    @Id
    @SequenceGenerator(name = "username_sequence",
    sequenceName = "username_sequence",
    allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "username_sequence"
    )
    @Column(name = "id")
    private Long id;
    @Column(name = "user_name",
    nullable = false,
    columnDefinition = "TEXT")
    private String name;
    @Column(name = "user_email",
    nullable = false,
    columnDefinition = "TEXT")
    private String email;
    @Column(name = "user_dob",
    nullable = false,
    columnDefinition = "DATE")
    private LocalDate dob;
    @Column(name = "user_address",
    nullable = false,
    columnDefinition = "TEXT")
    private String address;

    @Override
    public String toString(){
        return id+","+name+","+email+","+dob+","+address;
    }

    @Override
    public boolean equals(Object obj){
        Username u = (Username) obj;

        if(u.name.equals(this.name)&&u.address.equals(this.address)&&u.email.equals(this.email)&&u.dob.equals(this.dob)){
            return true;
        }
        return false;
    }

    @ManyToOne
    @JoinColumn( name= "roles_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "roles_id_fk"))

    @JsonBackReference(value = "test4")
    private Role role;
}
