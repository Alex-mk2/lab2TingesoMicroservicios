import React, { useState } from 'react';
import AppBar from '@mui/material/AppBar';
import Button from '@mui/material/Button';
import Estudiante from './estudiante';
import Registrar from './registrarCuotas';
import CargarArchivo from './SubirArchivo';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import ListaEstudiantes from './listaEstudiantes';
import GenerarCuotas from './generarCuotas';
import { createTheme, ThemeProvider } from '@mui/material/styles';

const theme = createTheme();

// Funcion inicio, es necesario cargar valores en las vistas para hacer switch//
export default function Inicio() {
  const [vista, setVista] = useState(0);
  const [opcion, setOpcion] = useState(null);

  const handleGenerarTablasPorClick = () => {
    const valorElegido = 1;
    setOpcion(valorElegido);
    setVista(5);
  };

  const handleButtonClick = (number) => {
    setVista(number);
    console.log(vista);
  };

  let componentToDisplay = null;

  switch (vista) {
    case 1:
      componentToDisplay = <Estudiante />;
      break;
    case 2:
      componentToDisplay = <ListaEstudiantes/>;
      break;
    case 3:
      componentToDisplay = <Registrar/>;
      break;
    case 4:
      componentToDisplay = <GenerarCuotas/>;
      break;
    case 5:
      componentToDisplay = <CargarArchivo/>;
      break;

    default:
      // Por defecto, si no hay una vista específica, no se muestra ningún componente.
  }

  return (
    <ThemeProvider theme={theme}>
      {/* Aquí va el resto de tu código para renderizar el componente */}
    </ThemeProvider>
  );
}
