package com.mint.fiestapp.models.misfiestas;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;

import java.util.ArrayList;

public class MisFiestasModel implements IMisFiestasModel {

    public RespuestaLista<FiestaModel> ObtenerFiestas(){
        RespuestaLista<FiestaModel> respuesta = new RespuestaLista<FiestaModel>();
        respuesta.Exito = true;
        respuesta.Modelo = new ArrayList<FiestaModel>();

        FiestaModel fiesta1 = new FiestaModel();
        fiesta1.Nombre = "Casamiento Meri & Nico";
        fiesta1.Fecha = "23/10/2018";
        fiesta1.Hora = "20:30HS";
        fiesta1.Imagen = "https://goo.gl/images/qAuKWX";

        FiestaModel fiesta2 = new FiestaModel();
        fiesta2.Nombre = "Fiesta Jerárquicos";
        fiesta2.Fecha = "09/09/2018";
        fiesta2.Hora = "21:30HS";
        fiesta2.Imagen = "https://goo.gl/images/qAuKWX";

        FiestaModel fiesta3 = new FiestaModel();
        fiesta3.Nombre = "Cumple 15 Abril";
        fiesta3.Fecha = "23/10/2020";
        fiesta3.Hora = "21:00HS";
        fiesta3.Imagen = "https://goo.gl/images/qAuKWX";


        respuesta.Modelo.add(fiesta1);
        respuesta.Modelo.add(fiesta2);
        respuesta.Modelo.add(fiesta3);
        respuesta.Modelo.add(fiesta1);
        respuesta.Modelo.add(fiesta2);
        respuesta.Modelo.add(fiesta3);
        respuesta.Modelo.add(fiesta1);

        return respuesta;
    }
}
