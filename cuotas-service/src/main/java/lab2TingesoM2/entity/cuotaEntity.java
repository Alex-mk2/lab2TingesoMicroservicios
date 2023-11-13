package lab2TingesoM2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cuotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class cuotaEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int idCuota;
    private int idEstudiante;
    private Float monto;
    private String tipoPago;
    private String estadoCuota;
    private Float montoPagado;
    private LocalDate fechaCreacion;
    private LocalDate fechaPago;
    private int mesesAtraso;
}

