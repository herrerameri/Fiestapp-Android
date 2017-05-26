package com.mint.fiestapp.models.entidades;

import com.mint.fiestapp.comun.EnumFuncionalidades;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FiestaModel implements Serializable{
    public String key;
    public String Nombre;
    public String Detalle;
    public String FechaHora;
    public String Imagen;
    public UbicacionModel Ubicacion;
    public int CantidadFotos;
    public int CantidadDias;
    public int CantidadInvitados;
    public ArrayList<EnumFuncionalidades> Funcionalidades;

    public void setKey(String key){
        this.key = key;
    }

    public String FechaHora(){
        SimpleDateFormat formato = new SimpleDateFormat ("dd/MM/yyyy | HH:mm'HS'");
        return formato.format(getFechaDate());
    }

    public String DiaMes(){
        SimpleDateFormat formatoDia = new SimpleDateFormat ("dd 'de' ");
        SimpleDateFormat formatoMes = new SimpleDateFormat ("MM");
        Date fecha = getFechaDate();
        String mes = getNombreMes(Integer.parseInt(formatoMes.format(fecha)));
        return formatoDia.format(fecha) + mes;
    }

    public String Hora(){
        SimpleDateFormat formato = new SimpleDateFormat ("HH:mm'HS'");
        return formato.format(getFechaDate());
    }

    private Date getFechaDate(){
        Timestamp stamp = new Timestamp(Long.parseLong(FechaHora));
        Date fecha = new Date(stamp.getTime());
        return fecha;
    }

    private String getNombreMes(int numeroMes){
        switch (numeroMes){
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            default:
                return "Diciembre";
        }
    }
}
