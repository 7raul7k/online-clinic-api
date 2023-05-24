package ro.mycode.onlineclinicapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name="tests")
@Entity(name = "Test")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TestPatient {
    @Id
    @SequenceGenerator(name ="tests_sequence",
    sequenceName = "tests_sequence",
    allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,
    generator = "tests_sequence")
    @Column(name ="id")
    private Long id;
    @Column(name = "name",
    nullable = false,
    columnDefinition = "TEXT")
    private String name;
    @Column(name= "type",
    nullable = false,
    columnDefinition = "TEXT")
    private String type;
    @Column(name = "description",
    nullable = false,
    columnDefinition = "TEXT")
    private String description;
    @Column(name="cost",
    nullable = false,
    columnDefinition = "DOUBLE")
    private double cost;
    @Column(name = "report",
    nullable = false,
    columnDefinition = "TEXT")
    private String report;

    @ManyToOne
    @JoinColumn( name = "patients_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "patients_id_fk"))
    @JsonBackReference(value = "test2")
    private Patient patient;

}
