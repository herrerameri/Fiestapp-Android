package com.mint.fiestapp.models.entidades;

import com.mint.fiestapp.comun.EnumFuncionalidades;
import com.mint.fiestapp.comun.FirebaseAuth;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FotoModel implements Serializable{
    public String FechaHora;
    public String Descripcion;
    public String Imagen;
    public HashMap<String, Boolean> Reacciones;
    public UsuarioModel Usuario;
    public String key;

    public String Reacciones(){
        if(Reacciones == null)
            return "0";
        return Reacciones.size() + "";
    }

    public boolean YoReaccione(){
        if(Reacciones == null)
            return false;
        return Reacciones.containsKey(FirebaseAuth.getFacebookIdUsuarioAutenticado());
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
