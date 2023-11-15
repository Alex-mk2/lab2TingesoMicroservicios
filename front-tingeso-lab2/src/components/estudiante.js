import React, { useState } from "react";
import EstudianteService from '../services/EstudianteService';
import {
  Button,
  Card,
  CardContent,
  Grid,
  Typography,
  Snackbar,
  Alert,
  TextField,
} from "@mui/material";

const Estudiante = () => {
  const [nombres, setNombres] = useState("");
  const [apellidos, setApellidos] = useState("");
  const [rut, setRut] = useState("");
  const [fechaNacimiento, setFechaNacimiento] = useState("");
  const [tipoColegio, setTipoColegio] = useState("");
  const [nombreColegio, setNombreColegio] = useState("");
  const [egresoColegio, setEgresoColegio] = useState(null);
  const [alerta, setAlerta] = useState(null);

  const estudianteService = EstudianteService;  

  const handleNombreChange = (e) => {
    setNombres(e.target.value);
  };

  const handleCloseAlert = () => {
    setAlerta(null);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
  
    const estudianteData = {
      nombres: nombres,
      apellidos: apellidos,
      rut: rut,
      fechaNacimiento: fechaNacimiento,
      tipoColegio: tipoColegio,
      nombreColegio: nombreColegio,
      egresoColegio: egresoColegio,
    };
  
    estudianteService.createEstudiante(estudianteData)
      .then((response) => {
        if (response.ok) {
          setAlerta({ type: "success", message: "Estudiante creado con éxito" });
          setNombres("");
          setApellidos("");
          setRut("");
          setFechaNacimiento("");
          setTipoColegio("");
          setNombreColegio("");
          setEgresoColegio("");
        } else {
          setAlerta({
            type: "error",
            message: "Error en la creación del estudiante",
          });
        }
      })
      .catch((error) => {
        console.error("Error de conexión", error);
        setAlerta({
          type: "error",
          message: "Error al conectar con el servidor",
        });
      });
    }      

  return (
    <Card>
      <CardContent>
        <Typography variant="h5" component="div" gutterBottom>
          Crear un nuevo estudiante
        </Typography>
        <form onSubmit={handleSubmit}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                label="Nombre"
                fullWidth
                value={nombres}
                onChange={(e) => setNombres(e.target.value)}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                label="Apellidos"
                fullWidth
                value={apellidos}
                onChange={(e) => setApellidos(e.target.value)}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                label="Rut"
                fullWidth
                value={rut}
                onChange={(e) => setRut(e.target.value)}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                label="Fecha de Nacimiento"
                fullWidth
                value={fechaNacimiento}
                onChange={(e) => setFechaNacimiento(e.target.value)}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                label="Tipo de Colegio"
                fullWidth
                value={tipoColegio}
                onChange={(e) => setTipoColegio(e.target.value)}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                label="Nombre del Colegio"
                fullWidth
                value={nombreColegio}
                onChange={(e) => setNombreColegio(e.target.value)}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                label="Egreso del Colegio"
                fullWidth
                value={egresoColegio}
                onChange={(e) => setEgresoColegio(e.target.value)}
              />
            </Grid>
          </Grid>
          <Button type="submit" variant="contained" disableElevation fullWidth>
            Crear Estudiante
          </Button>
        </form>
      </CardContent>
      <Snackbar
        open={!!alerta}
        autoHideDuration={5000}
        onClose={handleCloseAlert}
      >
        <Alert
          onClose={handleCloseAlert}
          severity={alerta?.type}
          sx={{ width: "100%" }}
        >
          {alerta?.message}
        </Alert>
      </Snackbar>
    </Card>
  );
};

export default Estudiante;
