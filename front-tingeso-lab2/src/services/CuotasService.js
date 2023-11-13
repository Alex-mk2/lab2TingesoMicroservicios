import axios from 'axios';

const CUOTAS_API_URL = "http://localhost:8080/cuotas/";

class CuotasService {

    getCuotasByRut(rut){
        return axios.get(CUOTAS_API_URL + rut);
    }

    getDetailCuota(id){
        return axios.get(CUOTAS_API_URL + "Detail/" + id);
    }

    PayCuota(id){
        return axios.post(CUOTAS_API_URL + "Pay/" + id);
    }

    SaveCuotas(rut, tipoPago, cantidadCuotas) {
        return axios.post(`${CUOTAS_API_URL}/GuardarCuotas`, null, {
          params: {
            rut: rut,
            tipo_pago: tipoPago,
            cant_cuotas: cantidadCuotas
          }
        });
    }
}

export default new CuotasService()

