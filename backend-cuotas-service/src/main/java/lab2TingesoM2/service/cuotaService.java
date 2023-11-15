package lab2TingesoM2.service;

import lab2TingesoM2.entity.cuotaEntity;
import lab2TingesoM2.model.estudianteModel;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lab2TingesoM2.repository.cuotaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class cuotaService {
    @Autowired
    cuotaRepository cuotaRepository;

    @Autowired
    RestTemplate restTemplate;

    public ArrayList<cuotaEntity> ObtenerCuotasPorRutEstudiante(String Rut) {
        /* Búsqueda de ID de estudiante */
        ResponseEntity<estudianteModel> responseEntity = restTemplate.exchange(
                "http://backend-gateway-service:8080/estudiante/byRut/" + Rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<estudianteModel>(){}
        );

        /* Se verifica que el estudiante exista */
        if (responseEntity.getStatusCode() != HttpStatus.OK || responseEntity.getBody() == null) {
            /* Se crea estructura con 1 elemento */
            ArrayList<cuotaEntity> listafinal = new ArrayList<cuotaEntity>();
            cuotaEntity cuotaEntity = new cuotaEntity();
            cuotaEntity.setMesesAtraso(-1);
            listafinal.add(cuotaEntity);

            return listafinal;
        } else {
            /* Búsqueda de conjunto de cuotas por ID estudiante */
            return cuotaRepository.findAllByIdEstudiante(responseEntity.getBody().getIdEstudiante());
        }
    }

    public cuotaEntity BuscarPorID(int idCuota){ return cuotaRepository.findByIdCuota(idCuota);}

    public cuotaEntity RegistrarEstadoDePagoCuota(int idCuota){
        /*Se busca cuentas existentes*/
        cuotaEntity CuotaExistente = cuotaRepository.findByIdCuota(idCuota);

        /*Se verifica que no se modifique una cuota en estado pagado*/
        if("Pagado".equals(CuotaExistente.getEstadoCuota())) { /*Cuota ya pagada*/
            return CuotaExistente;
        }

        /*Se verifica estado de la cuota*/
        if("Atrasada".equals(CuotaExistente.getEstadoCuota())) { /*Cuota atrasada*/
            /*Actualización de estado*/
            CuotaExistente.setEstadoCuota("Pagado (con atraso)");
        }
        else {
            CuotaExistente.setEstadoCuota("Pagado");
        }

        /*Cambio de fechas*/
        CuotaExistente.setFechaCreacion(CuotaExistente.getFechaPago());
        CuotaExistente.setFechaPago(LocalDate.now());

        /*Retorno*/
        return cuotaRepository.save(CuotaExistente);
    }

    public ArrayList<cuotaEntity> GenerarCuotasDeEstudiante(String Rut, Integer Cantidad, String Tipo) {
        /*Elementos Internos*/
        cuotaEntity errorCuota = new cuotaEntity(); //Modelo de cuotas para errores.
        cuotaEntity ModeloCuota;    //Entidad que sirve como modelo de cuota.
        ArrayList<cuotaEntity> cuotasGeneradas = new ArrayList<>(); //Arreglo de salida.
        cuotaEntity Matricula; //Registro de matricula como primer pago
        float ArancelReal;

        /* Se busca usuario para generar cuotas */
        ResponseEntity<estudianteModel> responseEntity = restTemplate.exchange(
                "http://backend-gateway-service:8080/estudiante/byRut/" + Rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<estudianteModel>(){}
        );

        /*Control de entrada*/
        //Existencia previa de cuotas.
        /*Rut no registrado*/
        if(responseEntity.getStatusCode() != HttpStatus.OK || responseEntity.getBody() == null){
            errorCuota.setMesesAtraso(-6);
            cuotasGeneradas.add(errorCuota);
            return cuotasGeneradas;
        }
        //Existencia previa de cuotas.
        else if(!cuotaRepository.findAllByIdEstudiante(responseEntity.getBody().getIdEstudiante()).isEmpty()){
            errorCuota.setMesesAtraso(-2);
            cuotasGeneradas.add(errorCuota);
            return cuotasGeneradas;
        }
        else if(Cantidad > 1 && Tipo.equals("Contado")){ //Más de una cuota al contado.
            /*Se entrega un arreglo de 1 elemento que establece el error*/
            errorCuota.setMesesAtraso(-1);
            cuotasGeneradas.add(errorCuota);
            return cuotasGeneradas;
        }
        //Bloque de condiciones por número máximo de cuotas.
        else if(Cantidad > 10 && responseEntity.getBody().getTipoColegio().equals("Municipal")){
            errorCuota.setMesesAtraso(-3);
            cuotasGeneradas.add(errorCuota);
            return cuotasGeneradas;
        }
        else if(Cantidad > 7 && responseEntity.getBody().getTipoColegio().equals("Subvencionado")){
            errorCuota.setMesesAtraso(-4);
            cuotasGeneradas.add(errorCuota);
            return cuotasGeneradas;
        }
        else if(Cantidad > 4 && responseEntity.getBody().getTipoColegio().equals("Privado")){
            errorCuota.setMesesAtraso(-5);
            cuotasGeneradas.add(errorCuota);
            return cuotasGeneradas;
        }

        /*Se establece primera cuota como pago de matricula*/
        Matricula = new cuotaEntity();
        Matricula.setIdEstudiante(responseEntity.getBody().getIdEstudiante());
        Matricula.setMonto((float) (70000));   //Valor de matricula.
        Matricula.setTipoPago("Contado");
        Matricula.setEstadoCuota("Pagado");
        Matricula.setMontoPagado((float) 70000);
        Matricula.setFechaCreacion(LocalDate.now());
        Matricula.setFechaPago(LocalDate.now());
        Matricula.setMesesAtraso(0);
        cuotasGeneradas.add(Matricula);
        cuotaRepository.save(Matricula);

        /*Se establecen valores de descuentos*/
        ArancelReal = (float) 1500000;

        /*Descuento por procedencia*/
        switch (responseEntity.getBody().getTipoColegio()) {
            case "Municipal" -> ArancelReal = ArancelReal - ((float) (1500000 * 0.20));
            case "Subvencionado" -> ArancelReal = ArancelReal - ((float) (1500000 * 0.10));
            default -> {
            }
        }

        /*Descuento por años de egreso en el colegio*/
        if(responseEntity.getBody().getEgresoColegio() == 0){
            ArancelReal = ArancelReal -  ((float) (1500000*0.15));
        }
        else if(responseEntity.getBody().getEgresoColegio() <= 2){
            ArancelReal = ArancelReal -  ((float) (1500000*0.08));
        }
        else if(responseEntity.getBody().getEgresoColegio() <= 4){
            ArancelReal = ArancelReal -  ((float) (1500000*0.04));
        }

        /* Se ingresan cuotas a la lista */
        if(Objects.equals(Tipo, "Contado")) {
            ModeloCuota = new cuotaEntity();
            ModeloCuota.setIdEstudiante(responseEntity.getBody().getIdEstudiante());
            ModeloCuota.setMonto((float) (1500000 / 2));
            ModeloCuota.setTipoPago("Contado");
            ModeloCuota.setEstadoCuota("Pendiente");
            ModeloCuota.setMontoPagado((float) 1500000/2);
            ModeloCuota.setFechaCreacion(LocalDate.now());
            ModeloCuota.setMesesAtraso(0);

            cuotaRepository.save(ModeloCuota); // Guarda la cuota en la base de datos
            cuotasGeneradas.add(ModeloCuota); // Agrega la cuota a la lista de cuotas generadas
        }
        else {
            /*Otros casos*/
            for (int i = 0; i < Cantidad; i++) {
                /* Se establece modelo de cuotas */
                ModeloCuota = new cuotaEntity();
                ModeloCuota.setIdEstudiante(responseEntity.getBody().getIdEstudiante());
                ModeloCuota.setMonto((float) (1500000 / Cantidad));
                ModeloCuota.setTipoPago("Cuotas");
                ModeloCuota.setEstadoCuota("Pendiente");
                ModeloCuota.setMontoPagado(ArancelReal / Cantidad);
                ModeloCuota.setFechaCreacion(LocalDate.now());
                ModeloCuota.setMesesAtraso(0);

                cuotaRepository.save(ModeloCuota); // Guarda la cuota en la base de datos
                cuotasGeneradas.add(ModeloCuota); // Agrega la cuota a la lista de cuotas generadas
            }
        }

        /* Se retorna la lista de cuotas generadas */
        return cuotasGeneradas;
    }

    public ArrayList<cuotaEntity> ObtenerTodas(){
        return (ArrayList<cuotaEntity>) cuotaRepository.findAll();
    }

    @Generated
    public ArrayList<cuotaEntity> ActualizarCuotas(ArrayList<cuotaEntity> Cuotas){
        return (ArrayList<cuotaEntity>) cuotaRepository.saveAll(Cuotas);
    }
}