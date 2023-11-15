package lab2TingesoM1.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estudiante")

public class estudianteEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstudiante", nullable = false)
    private int idEstudiante;
    private String nombres;
    private String apellidos;
    private String rut;
    private String fechaNacimiento;
    private String tipoColegio;
    private String nombreColegio;
    private int egresoColegio;
}