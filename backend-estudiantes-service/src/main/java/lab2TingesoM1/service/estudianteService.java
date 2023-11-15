package lab2TingesoM1.service;
import lab2TingesoM1.entity.estudianteEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.util.List;
import lab2TingesoM1.repository.estudianteRepository;

@Service
public class estudianteService{

    private final estudianteRepository estudianteRepository;

    @Autowired
    public estudianteService(estudianteRepository estudianteRepository){
        this.estudianteRepository = estudianteRepository;
    }

    private final static Logger logger = LoggerFactory.getLogger(estudianteService.class);
    public List<estudianteEntity> findAll(){
        List<estudianteEntity> estudiantes = estudianteRepository.findAll();
        logger.info(estudiantes.toString());
        return estudianteRepository.findAll();
    }

    public estudianteEntity findByRut(String rut){return estudianteRepository.findByRut(rut);
    }

    public ResponseEntity<estudianteEntity> save(estudianteEntity estudiante){
        try {
            if(exist(estudiante.getRut())){
                throw new RuntimeException();
            }
            estudianteEntity nuevoEstudiante = estudianteRepository.save(estudiante);
            return ResponseEntity.created(new URI("estudiante/save" + nuevoEstudiante.getRut())).body(nuevoEstudiante);
        }catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    public boolean exist(String rut){
        return estudianteRepository.existsByRut(rut);
    }

    public estudianteEntity findByIdEstudiante(int idEstudiante){
        return estudianteRepository.findByIdEstudiante(idEstudiante);
    }
}
