package lab2TingesoM2.service;
import lab2TingesoM2.entity.cuotaEntity;
import lab2TingesoM2.model.estudianteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lab2TingesoM2.repository.cuotaRepository;

@Service
public class cuotaService{
    private final cuotaRepository cuotaRepository;

    @Autowired
    public cuotaService(cuotaRepository cuotaRepository){
        this.cuotaRepository = cuotaRepository;
    }
    @Autowired
    RestTemplate restTemplate;

    private final static Logger logger = LoggerFactory.getLogger(cuotaService.class);

    public List<cuotaEntity> getAllCuotas(){
        List<cuotaEntity> cuotas = cuotaRepository.findAll();
        logger.info(cuotas.toString());
        return cuotaRepository.findAll();
    }

    public List<cuotaEntity> obtenerCuotasPorRut(String rut){
        estudianteModel estudiante = restTemplate.getForObject("http://localhost:8080/estudiantes/ByRut/" + rut, estudianteModel.class);
        if(estudiante == null){
            List<cuotaEntity> listaCuotasEstudiante = new ArrayList<cuotaEntity>();
            cuotaEntity cuota = new cuotaEntity();
            cuota.setMesesAtraso(-1);
            listaCuotasEstudiante.add(cuota);
            return listaCuotasEstudiante;
        }else{
            return cuotaRepository.findAllByIdEstudiante(estudiante.getIdEstudiante());
        }
    }

    public cuotaEntity buscarCuotaPorId(int idCuota){
        return cuotaRepository.findCuotaByIdCuota(idCuota);
    }

    public cuotaEntity actualizarCuotaEstudiante(int idCuota) {
        cuotaEntity cuotaEstudiante = cuotaRepository.findCuotaByIdCuota(idCuota);
        if (cuotaEstudiante == null) {
            throw new RuntimeException("Cuota de estudiante no encontrada");
        }
        cuotaEstudiante.setEstadoPago(cuotaEstudiante.getEstadoPago().equals("Pagado") ? "Pago atrasado" : "Pagado");
        cuotaEstudiante.setFechaPago(LocalDate.now());
        return cuotaRepository.save(cuotaEstudiante);
    }
    public int maximoCuotasEstudiante(String tipoColegio) {
        if ("Municipal".equals(tipoColegio)) {
            return 10;
        } else if ("Subvencionado".equals(tipoColegio)) {
            return 7;
        } else if ("Privado".equals(tipoColegio)) {
            return 4;
        } else {
            return 0;
        }
    }

    public List<cuotaEntity> errorCuota(List<cuotaEntity> cuotasCreadas, int error){
        cuotaEntity cuotaErronea = new cuotaEntity();
        cuotaErronea.setMesesAtraso(error);
        cuotasCreadas.add(cuotaErronea);
        return cuotasCreadas;
    }

    public float calcularArancelEstudiante(estudianteModel estudiante){
        float arancelReal = 1500000;
        if("Municipal".equals(estudiante.getTipoColegio())){
            arancelReal -= (float) (1500000*0.2);
        }else if("Subvencionado".equals(estudiante.getTipoColegio())){
            arancelReal -= (float) (1500000 * 0.1);
        }
        if(estudiante.getEgresoColegio() == 0){
            arancelReal-= (float) (15000000 * 0.15);
        }else if(estudiante.getEgresoColegio() <= 2){
            arancelReal-= (float) (15000000 * 0.08);
        } else if (estudiante.getEgresoColegio() <= 4){
            arancelReal-= (float) (15000000 * 0.04);
        }
        return arancelReal;
    }

    public cuotaEntity crearCuotaMatricula(estudianteModel estudiante){
        cuotaEntity matricula = new cuotaEntity();
        matricula.setIdEstudiante(estudiante.getIdEstudiante());
        matricula.setMonto((float) 70000);
        matricula.setTipoPago("Contado");
        matricula.setEstadoPago("Pagado");
        matricula.setMontoPagado((float) 70000);
        matricula.setFechaCreacion(LocalDate.now());
        matricula.setFechaPago(LocalDate.now());
        matricula.setMesesAtraso(0);
        return matricula;
    }



    public cuotaEntity crearCuota(estudianteModel estudiante, float arancelReal, int cantidad, String tipoCuota){
        cuotaEntity cuota = new cuotaEntity();
        cuota.setIdEstudiante(estudiante.getIdEstudiante());
        cuota.setMonto(arancelReal / cantidad);
        cuota.setTipoPago(tipoCuota);
        cuota.setEstadoPago("Pendiente");
        cuota.setMontoPagado(arancelReal / cantidad);
        cuota.setFechaCreacion(LocalDate.now());
        cuota.setMesesAtraso(0);
        return cuota;
    }

    public List<cuotaEntity> crearPlanillaCuotasEstudiante(String rut, Integer cantidad, String tipoCuota){
        estudianteModel estudiante = restTemplate.getForObject("http://Localhost:8090/estudiante/ByRut" + rut, estudianteModel.class);
        ArrayList<cuotaEntity> listaCuotas = new ArrayList<>();
        if(estudiante == null){
            return errorCuota(listaCuotas, -6);
        }
        if(!cuotaRepository.findAllByIdEstudiante(estudiante.getIdEstudiante()).isEmpty()){
            return errorCuota(listaCuotas, -2);
        }
        int maximoCuotas = maximoCuotasEstudiante(estudiante.getTipoColegio());
        if(cantidad > maximoCuotas){
            return errorCuota(listaCuotas, -maximoCuotas);
        }
        float arancelReal = calcularArancelEstudiante(estudiante);
        cuotaEntity matricula = crearCuotaMatricula(estudiante);
        listaCuotas.add(matricula);
        cuotaRepository.save(matricula);
        for(int i = 0; i < cantidad;i++){
            cuotaEntity cuota = crearCuota(estudiante, arancelReal, cantidad, tipoCuota);
            cuotaRepository.save(cuota);
            listaCuotas.add(cuota);
        }
        return listaCuotas;
    }

    public void actualizarCuotas(List<cuotaEntity> cuotas){
        cuotaRepository.saveAll(cuotas);
    }
}
