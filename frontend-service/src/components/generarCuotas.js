import React, { useState } from 'react';
import CuotasService from '../services/CuotasService';

function GenerarCuotasComponent() {
  const [rut, setRut] = useState("");
  const [tipoPago, setTipoPago] = useState("Contado");
  const [cantidadCuotas, setCantidadCuotas] = useState(1);
  const [message, setMessage] = useState("");

  const formatRut = (input) => {
    setRut(input.value);
  };

  const handleRutChange = (event) => {
    formatRut(event.target);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    // Llamar a la función SaveCuotas del servicio CuotasService
    CuotasService.SaveCuotas(rut, tipoPago, cantidadCuotas)
      .then(response => {
        // Manejar el mensaje recibido del servidor y mostrarlo en la vista
        setMessage(response.data);
      })
      .catch(error => {
        // Manejar errores aquí, si es necesario
        console.error('Error al enviar los datos:', error);
      });
  };

  return (
    <div className="container">
      <h2>Generar Cuotas</h2>
      <form onSubmit={handleSubmit}>
        <div className="input-group">
          <label htmlFor="rut">RUT Estudiante:</label>
          <input
            type="text"
            id="rut"
            name="rut"
            required
            pattern="\d{1,2}\.\d{3}\.\d{3}-\d{1|K|k}"
            value={rut}
            onChange={handleRutChange}
          />
        </div>
        <div className="input-group">
          <label htmlFor="tipo_pago">Tipo de cuota:</label>
          <select
            id="tipo_pago"
            name="tipo_pago"
            required
            value={tipoPago}
            onChange={(event) => setTipoPago(event.target.value)}
          >
            <option value="Contado">Contado</option>
            <option value="Cuotas">Cuotas</option>
          </select>
        </div>
        <div className="input-group">
          <label htmlFor="cant_cuotas">Cantidad de cuotas</label>
          <input
            type="number"
            id="cant_cuotas"
            name="cant_cuotas"
            min="1"
            required
            value={cantidadCuotas}
            onChange={(event) => setCantidadCuotas(event.target.value)}
          />
        </div>

        <button type="submit">Generar Cuotas</button>
      </form>
      <p>{message.data}</p>
    </div>
  );
}

export default GenerarCuotasComponent;