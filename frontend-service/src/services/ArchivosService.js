import axios from 'axios';

const PRUEBAS_API_URL = "http://localhost:8080/pruebas/";


class ArchivosService {

    VerPruebas(){
        return axios.get(PRUEBAS_API_URL);
    }

    SubirPruebas(archivo){
        return axios.post(PRUEBAS_API_URL + 'upload/', archivo, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
    }
}

export default new ArchivosService();