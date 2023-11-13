import React, { useEffect, useState } from "react";
import { Card, CardContent, Grid, Typography, Box } from "@mui/material";
import EstudianteService from "../services/EstudianteService";

const ListaEstudiantes = () => {
  const [estudiantes, setEstudiantes] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    EstudianteService.getAllEstudients().then((res) => {
        console.log("Response data Estudiante:", res.data);
        setEstudiantes(res.data);
        
    });
}, []);

  return (
    <Card>
      <CardContent>
        <Typography variant="h5" component="div" gutterBottom>
          Lista de estudiantes
        </Typography>
        {error && (
          <Typography variant="subtitle1" color="error" gutterBottom>
            {error}
          </Typography>
        )}
        <Grid container spacing={2}>
          {estudiantes.map(estudiante => (
            <Grid item xs={12} key={estudiante.rut}>
              <Card>
                <Box bgcolor="primary.main" color={"primary.contrastText"}>
                  <CardContent>
                    <Typography variant="subtitle1" gutterBottom>
                      Nombres: {estudiante.nombres}
                    </Typography>
                    <Typography variant="subtitle2">
                      Apellidos: {estudiante.apellidos}
                    </Typography>
                    <Typography variant="subtitle2">
                      Rut: {estudiante.rut}
                    </Typography>
                    <Typography variant="subtitle2">
                      Fecha de Nacimiento: {estudiante.fechaNacimiento}
                    </Typography>
                    <Typography variant="subtitle2">
                      Tipo de Colegio: {estudiante.tipoColegio}
                    </Typography>
                    <Typography variant="subtitle2">
                      Nombre del Colegio: {estudiante.nombreColegio}
                    </Typography>
                    <Typography variant="subtitle2">
                      Año de Egreso: {estudiante.egresoColegio}
                    </Typography>
                  </CardContent>
                </Box>
              </Card>
            </Grid>
          ))}
        </Grid>
      </CardContent>
    </Card>
  );
};

export default ListaEstudiantes;
