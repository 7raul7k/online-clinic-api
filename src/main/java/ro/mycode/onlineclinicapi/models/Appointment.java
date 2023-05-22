package ro.mycode.onlineclinicapi.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name ="appointments")
@Entity(name = "Appointment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Appointment {
    @Id
    @SequenceGenerator(name = "appointments_sequence",
    sequenceName = "appointments_sequence",
    allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,
    generator = "appointments_sequence")
    @Column(name = "id")
    private Long id;
    @Column(name ="number",
    nullable = false,
    columnDefinition = "TEXT")
    private String number;
    @Column(name = "description",
    nullable = false,
    columnDefinition = "TEXT")
    private String description;
    @Column(name = "date",
    nullable = false,
    columnDefinition = "DATE")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "doctors_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "doctors_id_fk"))
    @JsonBackReference(value = "test5")
    private Doctor doctor;

}
