package lab2TingesoM2.controller;
import lab2TingesoM2.entity.cuotaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import lab2TingesoM2.service.cuotaService;



@RestController
@RequestMapping("/cuotas")
public class cuotaController {
    @Autowired
    cuotaService cuotaService;

    @GetMapping
    public ResponseEntity<ArrayList<cuotaEntity>> ObtenerTodas(){
        ArrayList<cuotaEntity> Cuotas = cuotaService.ObtenerTodas();
        if(Cuotas == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Cuotas);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<ArrayList<cuotaEntity>> BuscarPorRut(@PathVariable("rut") String rut){
        ArrayList<cuotaEntity> cuotas = cuotaService.ObtenerCuotasPorRutEstudiante(rut);
        if(cuotas.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else if(cuotas.get(0).getMesesAtraso() == -1){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cuotas);
    }

    @GetMapping("/Detail/{id}")
    public ResponseEntity<cuotaEntity> getById(@PathVariable("id") int id) {
        cuotaEntity cuota = cuotaService.BuscarPorID(id);
        if(cuota == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cuota);
    }

    @PostMapping("/Pay/{id}")
    public ResponseEntity<cuotaEntity> RegistrarPago(@PathVariable("id") int id){
        cuotaEntity cuota = cuotaService.RegistrarEstadoDePagoCuota(id);
        return ResponseEntity.ok(cuota);
    }

    @PostMapping("/GuardarCuotas")
    public ResponseEntity<String> GenerarCuotas(@RequestParam("rut") String rut,
                                                @RequestParam("cant_cuotas") Integer cantCuotas,
                                                @RequestParam("tipo_pago") String TipoPago) {
        /*Cuota de error*/
        ArrayList<cuotaEntity> cuotas;

        /*Se guardan Cuotas*/
        cuotas = cuotaService.GenerarCuotasDeEstudiante(rut,cantCuotas,TipoPago);

        /*Mensajes de error*/
        if(cuotas.get(0).getMesesAtraso() == -1){
            return ResponseEntity.ok("Pago al contado es unico");
        }
        else if(cuotas.get(0).getMesesAtraso() == -2){
            return ResponseEntity.ok("Ya hay cuotas asociadas al rut");
        }
        else if(cuotas.get(0).getMesesAtraso() == -3){
            return ResponseEntity.ok("Un alumno de un colegio municipal solo " +
                    "opta a máximo 10 cuotas");
        }
        else if(cuotas.get(0).getMesesAtraso() == -4){
            return ResponseEntity.ok("Un alumno de un colegio subvencionado solo " +
                    "opta a máximo 7 cuotas");
        }
        else if(cuotas.get(0).getMesesAtraso() == -5){
            return ResponseEntity.ok("Un alumno de un colegio privado solo " +
                    "opta a máximo 4 cuotas");
        }
        else if(cuotas.get(0).getMesesAtraso() == -6){
            return ResponseEntity.ok("Rut dado no esta registrado");
        }

        return ResponseEntity.ok("Cuotas generadas satisfactoriamente.");
    }
    @PutMapping("/actualizar")
    public void actualizarCuotas(@RequestBody ArrayList<cuotaEntity> cuotas) {
        cuotaService.ActualizarCuotas(cuotas);
    }
}
