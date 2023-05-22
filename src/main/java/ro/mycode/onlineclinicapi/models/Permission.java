package ro.mycode.onlineclinicapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name ="permissions")
@Entity(name = "Permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Permission {
    @Id
    @SequenceGenerator(name = "permission_sequence",
    sequenceName = "permission_sequence",
    allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,
    generator = "permission_sequence")
    @Column(name = "id")
    private Long id;
    @Column(name ="title",
    nullable = false,
    columnDefinition = "TEXT")
    private String title;
    @Column(name = "module",
    nullable = false,
    columnDefinition = "TEXT")
    private String module;
    @Column(name = "description",
    nullable = false,
    columnDefinition = "TEXT")
    private String description;

    @Override
    public String toString(){
        return id+","+title+","+module+","+description;
    }

    @Override
    public boolean equals(Object obj){
        Permission p = (Permission) obj;

        if(p.title.equals(this.title)&&p.module.equals(this.module)&&p.description.equals(this.description)){
            return true;
        }
        return false;
    }

    @ManyToOne
    @JoinColumn(name = "roles_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "roles_id_fk"))
    @JsonBackReference(value = "test5")
    private Role role;


}
