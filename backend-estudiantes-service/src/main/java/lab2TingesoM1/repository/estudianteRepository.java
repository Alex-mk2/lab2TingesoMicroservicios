package lab2TingesoM1.repository;

import lab2TingesoM1.entity.estudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface estudianteRepository extends JpaRepository<estudianteEntity, Integer> {
    estudianteEntity findByRut(String rut);

    boolean existsByRut(String rut);

    estudianteEntity findByIdEstudiante(int idEstudiante);
}
