package com.mint.fiestapp.models.entidades;

import com.mint.fiestapp.comun.EnumFuncionalidades;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FotoModel implements Serializable{
    public String key;
    public String Descripcion;
    public String FechaHora;
    public String Imagen;

    public void setKey(String key){
        this.key = key;
    }

    public String FechaHora(){
        SimpleDateFormat formato = new SimpleDateFormat ("dd/MM/yyyy | HH:mm'HS'");
        return formato.format(getFechaDate());
    }

    private Date getFechaDate(){
        Timestamp stamp = new Timestamp(Long.parseLong(FechaHora));
        Date fecha = new Date(stamp.getTime());
        return fecha;
    }
}
