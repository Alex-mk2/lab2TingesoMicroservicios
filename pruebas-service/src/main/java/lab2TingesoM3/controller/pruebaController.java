package lab2TingesoM3.controller;
import lab2TingesoM3.entity.pruebaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lab2TingesoM3.service.pruebaService;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/pruebas")
public class pruebaController{

    @Autowired
    private pruebaService pruebaService;

    @GetMapping
    public ResponseEntity<List<pruebaEntity>> getAllPruebas(){
        List<pruebaEntity> pruebas = pruebaService.getAllPruebas();
        if(pruebas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pruebas);
    }

    @PostMapping("/upload")

    public String upload(@RequestParam("file") MultipartFile file){
        pruebaService.GuardarNombreArchivo(file);
        String mensaje = pruebaService.VerificarArchivo("Pruebas.csv");
        if(!mensaje.equals("")){
            return mensaje;
        }else{
            pruebaService.LeerArchivoCsv("Pruebas.csv");
            return "Archivo cargado con exito";
        }
    }
}
