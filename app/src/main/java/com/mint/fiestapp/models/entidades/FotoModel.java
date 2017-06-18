package com.mint.fiestapp.models.entidades;

import com.mint.fiestapp.comun.EnumFuncionalidades;
import com.mint.fiestapp.comun.FirebaseAuth;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FotoModel implements Serializable{
    public String FechaHora;
    public String Descripcion;
    public String Imagen;
    public byte[] Bytes;
    public HashMap<String, UsuarioModel> Reacciones;
    public UsuarioModel Usuario;
    public String key;
    public String keyFiesta;

    public void setKey(String key){
        this.key = key;
    }

    public String Reacciones(){
        if(Reacciones == null)
            return "0";
        return Reacciones.size() + "";
    }

    public boolean YoReaccione(){
        if(Reacciones == null)
            return false;
        Iterator it = Reacciones.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(((UsuarioModel)pair.getValue()).Id.equals(FirebaseAuth.getFacebookIdUsuarioAutenticado()))
            {
                return true;
            }
        }
        return false;
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
