package lab2TingesoM1.controller;
import lab2TingesoM1.entity.estudianteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lab2TingesoM1.service.estudianteService;

@RestController
@RequestMapping("/estudiante")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST}, allowedHeaders = "*")
public class estudianteController{

    @Autowired
    estudianteService estudianteService;

    @GetMapping
    public ResponseEntity<List<estudianteEntity>> getAll() {
        List<estudianteEntity> estudianteEntities = estudianteService.findAll();
        if (estudianteEntities.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(estudianteEntities);
    }

    @GetMapping("/byId/{idEstudiante}")
    public ResponseEntity<estudianteEntity> getById(@PathVariable("idEstudiante") int idEstudiante) {
        estudianteEntity estudianteEntity = estudianteService.findByIdEstudiante(idEstudiante);
        if (estudianteEntity == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estudianteEntity);
    }

    @GetMapping("/byRut/{rut}")
    public ResponseEntity<estudianteEntity> getByRut(@PathVariable("rut") String rut) {
        estudianteEntity estudiante = estudianteService.findByRut(rut);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estudiante);
    }

    @PostMapping()
    public ResponseEntity<estudianteEntity> save(@RequestBody estudianteEntity estudianteEntity) {
        return estudianteService.save(estudianteEntity);
    }

    @GetMapping("/exist/{rut}")
    public ResponseEntity<Boolean> existEstudentByRut(@PathVariable("rut") String rut) {
        return ResponseEntity.ok().body(estudianteService.exist(rut));
    }
}
