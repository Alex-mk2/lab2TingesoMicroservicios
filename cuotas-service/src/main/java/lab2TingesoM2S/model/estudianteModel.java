package lab2TingesoM2S.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class estudianteModel {
    private int idEstudiante;
    private String nombres;
    private String apellidos;
    private String rut;
    private String fechaNacimiento;
    private String tipoColegio;
    private String nombreColegio;
    private int egresoColegio;
}
