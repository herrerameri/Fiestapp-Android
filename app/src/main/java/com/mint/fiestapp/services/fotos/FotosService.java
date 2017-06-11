package com.mint.fiestapp.services.fotos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.services.Servicios;

import java.util.ArrayList;
import java.util.List;

public class FotosService extends Servicios implements IFotosService {

    public interface IFotosServiceCallback{
        void callbackObtenerFotos(RespuestaLista<FotoModel> respuesta);
    }

    IFotosServiceCallback listener;

    public FotosService(IFotosServiceCallback listener){
        this.listener = listener;
    }

    @Override
    public void obtenerUltimasFotos(int cantidad, String keyFiesta){
        fotosReference.child(keyFiesta).orderByKey().limitToLast(cantidad).addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<FotoModel> fotos = new ArrayList<>();
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        FotoModel foto = snapshot.getValue(FotoModel.class);
                        fotos.add(foto);
                    }
                    RespuestaLista<FotoModel> respuesta = new RespuestaLista<>();
                    respuesta.Exito = true;
                    respuesta.Modelo = fotos;
                    listener.callbackObtenerFotos(respuesta);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    RespuestaLista<FotoModel> respuesta = new RespuestaLista<>();
                    respuesta.Exito = false;
                    respuesta.Mensaje = "Ocurrió un error al conectar con el servidor.";
                    listener.callbackObtenerFotos(respuesta);
                }
            });
    }

    @Override
    public void obtenerPaginaFotos(int cantidad, String keyUltimaFoto,String keyFiesta){
        fotosReference.child(keyFiesta).orderByKey().endAt(keyUltimaFoto).limitToLast(cantidad+1).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<FotoModel> fotos = new ArrayList<>();
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            FotoModel foto = snapshot.getValue(FotoModel.class);
                            fotos.add(foto);
                        }
                        // La primer foto se repite (es keyUltimaFoto)
                        if(fotos.size() > 0){
                            fotos.remove(fotos.size()-1);
                        }
                        RespuestaLista<FotoModel> respuesta = new RespuestaLista<>();
                        respuesta.Exito = true;
                        respuesta.Modelo = fotos;
                        listener.callbackObtenerFotos(respuesta);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        RespuestaLista<FotoModel> respuesta = new RespuestaLista<>();
                        respuesta.Exito = false;
                        respuesta.Mensaje = "Ocurrió un error al conectar con el servidor.";
                        listener.callbackObtenerFotos(respuesta);
                    }
                });
    }
}
