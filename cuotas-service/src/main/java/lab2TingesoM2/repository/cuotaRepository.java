package lab2TingesoM2.repository;
import lab2TingesoM2.entity.cuotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;


@Repository
public interface cuotaRepository extends JpaRepository<cuotaEntity, Integer> {


    ArrayList<cuotaEntity> findAllByIdEstudiante(int idEstudiante);

    cuotaEntity findByIdCuota(int idCuota);


}
