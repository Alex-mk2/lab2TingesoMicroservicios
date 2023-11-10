package lab2TingesoM2S.controller;
import lab2TingesoM2S.entity.cuotaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lab2TingesoM2S.service.cuotaService;



@RestController
@CrossOrigin("*")
@RequestMapping("/cuotas")
public class cuotaController{
    @Autowired
    private cuotaService cuotaService;



    @GetMapping
    public ResponseEntity<List<cuotaEntity>> obtenerCuotas(){
        List<cuotaEntity> cuotasEstudiantes = cuotaService.getAllCuotas();
        if(cuotasEstudiantes == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cuotasEstudiantes);
    }

    @GetMapping("/estudiante/ByRut/{rut}")
    public ResponseEntity<List<cuotaEntity>> buscarCuotasPorRut(@PathVariable("rut") String rut) {
        List<cuotaEntity> cuotas = cuotaService.obtenerCuotasPorRut(rut);
        if (cuotas.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (cuotas.get(0).getMesesAtraso() == -1) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cuotas);
    }

    @GetMapping("/byIdCuota/{idCuota}")
    public ResponseEntity<cuotaEntity> getByIdEstudiante(@PathVariable("idCuota") int idCuota){
        cuotaEntity cuota = cuotaService.buscarCuotaPorId(idCuota);
        if(cuota == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cuota);
    }

    @PostMapping("/GuardarCuotas")
    public ResponseEntity<String> generarCuotasEstudiante(@RequestParam("rut") String rut, @RequestParam("cantidadCuotas") Integer cantidadCuotas, @RequestParam("tipoPago") String tipoPago) {
        List<cuotaEntity> cuotas = cuotaService.crearPlanillaCuotasEstudiante(rut, cantidadCuotas, tipoPago);
        int mesesAtraso = cuotas.get(0).getMesesAtraso();
        if(mesesAtraso == -1) {
            return ResponseEntity.ok("Pago único");
        }else if (mesesAtraso == -2) {
            return ResponseEntity.ok("Existen cuotas asociadas al rut");
        }else if (mesesAtraso == -3) {
            return ResponseEntity.ok("Estudiante municipal dispone de 10 cuotas");
        }else if (mesesAtraso == -4) {
            return ResponseEntity.ok("Estudiante subvencionado dispone de 7 cuotas");
        }else if (mesesAtraso == -5) {
            return ResponseEntity.ok("Estudiante privado dispone de 4 cuotas");
        }else if (mesesAtraso == -6) {
            return ResponseEntity.ok("No existe estudiante asociado con ese rut");
        }
        return ResponseEntity.ok("Cuotas generadas");
    }


    @PutMapping("/actualizar")
    public void actualizarCuotasEstudiante(@RequestBody List<cuotaEntity> cuotas){
        cuotaService.actualizarCuotas(cuotas);
    }
}
