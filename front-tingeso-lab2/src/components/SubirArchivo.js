import React, { useState } from 'react';
import { Button, Card, CardContent, Typography, Snackbar, Alert } from '@mui/material';

const SubirArchivo = () => {
  const [file, setFile] = useState(null);
  const [alerta, setAlerta] = useState(null);

  const change = (e) => {
    setFile(e.target.files[0]);
  };

  const closeAlert = () => {
    setAlerta(null);
  };

  const submitAlert = (e) => {
    e.preventDefault();
    if (!file) {
      setAlerta({ type: 'error', message: 'Seleccione un archivo' });
      return;
    }

    const formData = new FormData();
    formData.append('file', file);

    fetch('http://localhost:8080/pruebas/upload', {
      method: 'POST',
      body: formData,
    })
      .then((response) => {
        if (response.status === 201) {
          setAlerta({ type: 'success', message: 'Archivo cargado correctamente' });
          setFile(null);
        } else {
          setAlerta({ type: 'error', message: 'Error al cargar el archivo' });
        }
      })
      .catch((error) => {
        setAlerta({ type: 'error', message: error.toString() });
      });
  };

  return (
    <Card>
      <CardContent>
        <Typography variant="h5" component="div" gutterBottom>
          Subir archivo pruebas
        </Typography>
        <form onSubmit={submitAlert}>
          <input type="file" accept=".xlsx, .xls" onChange={change}></input>
          <Button type="submit" variant="contained" disableElevation>
            Subir archivo excel
          </Button>
        </form>
      </CardContent>
      <Snackbar open={!!alerta} autoHideDuration={5000} onClose={closeAlert}>
        <Alert onClose={closeAlert} severity={alerta?.type} sx={{ width: '100%' }}>
          {alerta?.message}
        </Alert>
      </Snackbar>
    </Card>
  );
};

export default SubirArchivo;
