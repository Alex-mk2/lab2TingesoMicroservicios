import React, { useState, useEffect } from 'react';
import { Helmet } from 'react-helmet';
import ArchivosService from '../services/ArchivosService';

function CargarPruebasComponent() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [message, setMessage] = useState('');
  const [pruebas, setPruebas] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await ArchivosService.VerPruebas();
        console.log('Response data:', response.data);

        // Verificar si la respuesta es un array antes de actualizar el estado
        if (Array.isArray(response.data)) {
          setPruebas(response.data);
        } else {
          console.error('La respuesta no es un array:', response.data);
        }
      } catch (error) {
        console.error('Error al obtener pruebas:', error);
      }
    };

    fetchData();
  }, []);

  const handleFileChange = event => {
    setSelectedFile(event.target.files[0]);
    setMessage('');
  };

  const handleFileUpload = async () => {
    if (selectedFile) {
      const formData = new FormData();
      formData.append('file', selectedFile);

      try {
        const response = await ArchivosService.SubirPruebas(formData);

        setMessage('Archivo cargado exitosamente');
        console.log('Archivo cargado exitosamente', response);

        // Actualizar pruebas después de cargar el archivo
        const newResponse = await ArchivosService.VerPruebas();
        console.log('New response data:', newResponse.data);

        if (Array.isArray(newResponse.data)) {
          setPruebas(newResponse.data);
        } else {
          console.error('La respuesta no es un array:', newResponse.data);
        }
      } catch (error) {
        setMessage('Error al cargar el archivo: ' + error);
        console.error('Error al cargar el archivo:', error);
      }
    } else {
      setMessage('Ningún archivo seleccionado');
      console.error('Ningún archivo seleccionado');
    }
  };

  return (
    <div>
      <Helmet>
        <title>Subir Pruebas</title>
      </Helmet>

      <div className="container">
        <h2>Cargar Archivo de Pruebas</h2>
        <input type="file" name="file" onChange={handleFileChange} accept=".csv" />
        <button onClick={handleFileUpload}>Cargar Archivo</button>
        <p>{message}</p>
      </div>

      <div className="container">
        <h2>Pruebas Recibidas</h2>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Puntaje</th>
              <th>Fecha Realización</th>
            </tr>
          </thead>
          <tbody>
            {pruebas.map((prueba) => (
              <tr key={prueba.idPrueba}>
                <td>{prueba.idPrueba}</td>
                <td>{prueba.puntajeObtenido}</td>
                <td>{prueba.fechaExamenRealizado}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default CargarPruebasComponent;
