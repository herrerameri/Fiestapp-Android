package com.mint.fiestapp.models.entidades;

import com.mint.fiestapp.comun.EnumFuncionalidades;

import java.io.Serializable;
import java.util.ArrayList;

public class FiestaModel implements Serializable{
    public String Nombre;
    public String Detalle;
    public String Fecha;
    public String Hora;
    public String Imagen;
    public int CantidadFotos;
    public int CantidadDias;
    public int CantidadInvitados;
    public ArrayList<EnumFuncionalidades> Funcionalidades;

    public String FechaHora(){
        return Fecha + " | " + Hora;
    }

}
