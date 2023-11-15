import React, { useState, useEffect } from "react";
import { Helmet } from "react-helmet";
import Form from 'react-bootstrap/Form';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import CuotasService from '../services/CuotasService';

function CuotasEstudianteComponent() {
  const [rut, setRut] = useState("");
  const [cuotas, setCuotas] = useState([]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await CuotasService.getCuotasByRut(rut);
      setCuotas(response.data);
    } catch (error) {
      console.error("Error al obtener las cuotas:", error);
    }
  };

   const handlePagarCuota = async (cuotaId) => {
    try {
      await CuotasService.PayCuota(cuotaId);
    } catch (error) {
      console.error("Error al pagar la cuota:", error);
    }
  };

  return (
    <div className="container-Cuotas">
      <Helmet>
        <title>Cuotas de Alumno</title>
        <link rel="icon" href="./images/Logo.png"></link>
      </Helmet>
      <h2>Consulta de Cuotas</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="rut">
          <Form.Label>Rut:</Form.Label>
          <Form.Control
            type="text"
            placeholder="Ingresa el Rut"
            value={rut}
            onChange={(e) => setRut(e.target.value)}
          />
        </Form.Group>
        <Button type="submit" variant="primary">
          Consultar Cuotas
        </Button>
      </Form>

      {cuotas.length == 0 && (
        <p className="text">{"No se encontraron cuotas para el Rut especificado."}</p>
      )}

      {cuotas.length > 0 && (
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>ID</th>
              <th>Monto a pagar</th>
              <th>Estado</th>
              <th>Tipo de pago</th>
              <th>Fecha emisión</th>
              <th>Fecha Pago</th>
              <th>Meses atraso</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {cuotas.map((cuota) => (
              <tr key={cuota.idCuota}>
                <td>{cuota.idCuota}</td>
                <td>{cuota.monto}</td>
                <td>{cuota.estadoCuota}</td>
                <td>{cuota.tipoPago}</td>
                <td>{cuota.fechaCreacion}</td>
                <td>{cuota.fechaPago}</td>
                <td>{cuota.mesesAtraso}</td>
                <td>
                  {cuota.estado === "Pendiente" && (
                    <Button
                      variant="primary"
                      onClick={() => handlePagarCuota(cuota.idCuota)}
                    >
                      Pagar
                    </Button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
    </div>
  );
}

export default CuotasEstudianteComponent;