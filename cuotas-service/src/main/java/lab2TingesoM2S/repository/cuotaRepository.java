package lab2TingesoM2S.repository;

import lab2TingesoM2S.entity.cuotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface cuotaRepository extends JpaRepository<cuotaEntity, Integer> {

    List<cuotaEntity> findAllByIdEstudiante(int idEstudiante);

    cuotaEntity findCuotaByIdCuota(int idCuota);
}
