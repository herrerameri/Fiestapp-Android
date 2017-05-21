package com.mint.fiestapp.models.misfiestas;

import com.mint.fiestapp.comun.EnumFuncionalidades;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;

import java.util.ArrayList;

public class MisFiestasModel implements IMisFiestasModel {

    public RespuestaLista<FiestaModel> obtenerFiestas(){
        RespuestaLista<FiestaModel> respuesta = new RespuestaLista<FiestaModel>();
        respuesta.Exito = true;
        respuesta.Modelo = new ArrayList<>();

        FiestaModel fiesta1 = new FiestaModel();
        fiesta1.Nombre = "Casamiento Meri & Nico";
        fiesta1.Fecha = "23/10/2018";
        fiesta1.Detalle= "Nos amamos y nos casamos";
        fiesta1.Hora = "20:30HS";
        fiesta1.CantidadFotos = 20;
        fiesta1.CantidadDias = 3;
        fiesta1.CantidadInvitados = 100;
        fiesta1.Imagen = "http://i.imgur.com/DvpvklR.png";
        fiesta1.Funcionalidades = new ArrayList<>();
        fiesta1.Funcionalidades.add(EnumFuncionalidades.GALERIA);
        fiesta1.Funcionalidades.add(EnumFuncionalidades.ASISTENCIA);
        fiesta1.Funcionalidades.add(EnumFuncionalidades.INFORMACION);
        fiesta1.Funcionalidades.add(EnumFuncionalidades.MUSICA);

        FiestaModel fiesta2 = new FiestaModel();
        fiesta2.Nombre = "Fiesta Jerárquicos";
        fiesta2.Fecha = "09/09/2018";
        fiesta2.Hora = "21:30HS";
        fiesta2.Detalle= "22 años!";
        fiesta2.CantidadFotos = 40;
        fiesta2.CantidadDias = 4007;
        fiesta2.CantidadInvitados = 1200;
        fiesta2.Funcionalidades = new ArrayList<>();
        fiesta2.Funcionalidades.add(EnumFuncionalidades.GALERIA);
        fiesta2.Funcionalidades.add(EnumFuncionalidades.ASISTENCIA);
        fiesta2.Imagen = "https://scontent-cdg2-1.xx.fbcdn.net/v/t31.0-8/18193074_10213099921951291_749172309182896641_o.jpg?oh=f5448a7ec56a12ffe6cfc7e64018491f&oe=59B749CD";

        FiestaModel fiesta3 = new FiestaModel();
        fiesta3.Nombre = "Cumple 15 Abril";
        fiesta3.Fecha = "23/10/2020";
        fiesta3.Detalle = "No te la pierdas";
        fiesta3.Hora = "21:00HS";
        fiesta3.CantidadFotos = 27;
        fiesta3.CantidadDias = 237;
        fiesta3.Funcionalidades = new ArrayList<>();
        fiesta3.Funcionalidades.add(EnumFuncionalidades.MEGUSTAS);
        fiesta3.Funcionalidades.add(EnumFuncionalidades.ASISTENCIA);
        fiesta3.Funcionalidades.add(EnumFuncionalidades.MENU);
        fiesta3.CantidadInvitados = 140;
        fiesta3.Imagen = "https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/13876688_10210286842106053_5831177286007372963_n.jpg?oh=add7bcb28e2f1238b54085c489a1d9cc&oe=5977C500";

        respuesta.Modelo.add(fiesta1);
        respuesta.Modelo.add(fiesta2);
        respuesta.Modelo.add(fiesta3);
        respuesta.Modelo.add(fiesta1);
        respuesta.Modelo.add(fiesta2);

        return respuesta;
    }
}
