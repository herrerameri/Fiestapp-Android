package com.mint.fiestapp.models.entidades;

import java.io.Serializable;

public class FiestaModel implements Serializable{
    public String Nombre;
    public String Detalle;
    public String Fecha;
    public String Hora;
    public String Imagen;

    public String FechaHora(){
        return Fecha + " | " + Hora;
    }
}
