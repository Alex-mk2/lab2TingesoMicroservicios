package lab2TingesoM3.repository;
import lab2TingesoM3.entity.pruebaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface pruebaRepository extends JpaRepository<pruebaEntity, Long> {
    List<pruebaEntity> findAllByIdEstudiante(int idEstudiante);
}

