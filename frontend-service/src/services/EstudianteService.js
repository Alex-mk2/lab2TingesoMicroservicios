import axios from 'axios';

const ESTUDIANTE_API_URL= "http://localhost:8080/estudiante/";

class EstudianteService{

    getAllEstudients(){
        return axios.get(ESTUDIANTE_API_URL);
    }
    findEstudentById(idEstudiante){
        return axios.get(ESTUDIANTE_API_URL+ "byId/" + idEstudiante);
    }

    findEstudentByRut(rut){
        return axios.get(ESTUDIANTE_API_URL+ "byRut/" + rut);
    }

    createEstudiante(estudiante){
        return axios.post(ESTUDIANTE_API_URL, estudiante);
    }
}

export default new EstudianteService()