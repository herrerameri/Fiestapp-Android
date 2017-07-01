package com.mint.fiestapp.services.fotos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.Respuesta;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.models.entidades.UsuarioModel;
import com.mint.fiestapp.services.Servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FotosService extends Servicios implements IFotosService {

    public interface IFotosServiceCallback{
        void callbackObtenerFotos(RespuestaLista<FotoModel> respuesta);
        void callbackAgregarReaccion(String keyFoto, Respuesta<HashMap<String, UsuarioModel>> respuesta);
        void callbackQuitarReaccion(String keyFoto, Respuesta<String> respuesta);
    }

    IFotosServiceCallback listener;

    public FotosService(IFotosServiceCallback listener){
        this.listener = listener;
    }

    @Override
    public void obtenerUltimasFotos(int cantidad, String keyFiesta){
        fotosReference.child(keyFiesta).orderByChild("FechaHora").limitToLast(cantidad).addListenerForSingleValueEvent(
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
                    Collections.reverse(fotos);
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
        fotosReference.child(keyFiesta).orderByChild("FechaHora").limitToLast(cantidad+1).endAt(keyUltimaFoto).addListenerForSingleValueEvent(
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
                        Collections.reverse(fotos);
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
    public void agregarReaccion(String keyFiesta, String keyFoto, String idUsuario){
        Respuesta<HashMap<String, UsuarioModel>> respuesta = new Respuesta<>();
        try
        {
            String key = fotosReference.child(keyFiesta).child(keyFoto).child("Reacciones").push().getKey();
            UsuarioModel usuarioReaccion = new UsuarioModel(idUsuario,"");
            fotosReference.child(keyFiesta).child(keyFoto).child("Reacciones").child(key).setValue(usuarioReaccion);

            HashMap<String, UsuarioModel> reaccion = new HashMap<>();
            reaccion.put(key, usuarioReaccion);
            respuesta.Exito = true;
            respuesta.Modelo = reaccion;
        }
        catch(Exception e){
            respuesta.Exito = false;
            respuesta.Mensaje = "Ocurrió un error al conectar con el servidor.";
        }
        listener.callbackAgregarReaccion(keyFoto, respuesta);
    }


    @Override
    public void quitarReaccion(String keyFiesta, String keyFoto, String keyReaccion){
        Respuesta<String> respuesta = new Respuesta<>();
        try
        {
            fotosReference.child(keyFiesta).child(keyFoto).child("Reacciones").child(keyReaccion).removeValue();

            respuesta.Exito = true;
            respuesta.Modelo = keyReaccion;
        }
        catch(Exception e){
            respuesta.Exito = false;
            respuesta.Mensaje = "Ocurrió un error al conectar con el servidor.";
        }
        listener.callbackQuitarReaccion(keyFoto, respuesta);
    }
}
