package com.mint.fiestapp.services.misfiestas;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mint.fiestapp.comun.SampleData;
import com.mint.fiestapp.models.IModel;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.models.misfiestas.IMisFiestasModel;
import com.mint.fiestapp.services.Servicios;

import java.util.ArrayList;
import java.util.List;


public class MisFiestasService extends Servicios implements IMisFiestasService {

    IMisFiestasModel model;
    public MisFiestasService(IModel modelo){
        model = (IMisFiestasModel) modelo;
    }

    public void obtenerFiestas(){
        fiestasReference.addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<FiestaModel> fiestas = new ArrayList<>();
                    for (DataSnapshot fiestasDataSnapshot: dataSnapshot.getChildren()){
                        FiestaModel fiesta = fiestasDataSnapshot.getValue(FiestaModel.class);
                        fiestas.add(fiesta);
                    }
                    RespuestaLista<FiestaModel> respuesta = new RespuestaLista<>();
                    respuesta.Exito = true;
                    respuesta.Modelo = fiestas;
                    model.callbackObtenerFiestas(respuesta);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    RespuestaLista<FiestaModel> respuesta = new RespuestaLista<>();
                    respuesta.Exito = false;
                    respuesta.Mensaje = "Ocurrió un error al conectar con la base de datos.";
                    model.callbackObtenerFiestas(respuesta);
                }
        });
    }

    public void crearFiestasInitialData(){
        List<FiestaModel> fiestas = SampleData.getDataFiestas();
        for (FiestaModel unaFiesta: fiestas){
            String key = fiestasReference.push().getKey();
            unaFiesta.setKey(key);
            fiestasReference.child(key).setValue(unaFiesta);
        }
    }
}
