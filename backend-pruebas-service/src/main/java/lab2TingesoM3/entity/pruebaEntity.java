package lab2TingesoM3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pruebas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class pruebaEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int idPrueba;
    private int idEstudiante;
    private Integer puntajeObtenido;
    private LocalDate fechaExamenRealizado;
    private LocalDate fechaResultados;
}