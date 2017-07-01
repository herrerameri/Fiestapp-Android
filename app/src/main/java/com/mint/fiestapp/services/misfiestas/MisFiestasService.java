package com.mint.fiestapp.services.misfiestas;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mint.fiestapp.comun.FirebaseAuth;
import com.mint.fiestapp.comun.SampleData;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.services.Servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MisFiestasService extends Servicios implements IMisFiestasService {

    public interface IMisFiestasServiceCallback{
        void callbackObtenerFiestas(RespuestaLista<FiestaModel> respuesta);
        void callbackAgregarFiesta(RespuestaLista<Object> respuesta);
    }

    long cantidadFiestas=0;
    IMisFiestasServiceCallback listener;
    List<FiestaModel> fiestas = new ArrayList<>();

    public MisFiestasService(IMisFiestasServiceCallback callback){
        listener = callback;
    }

    public void obtenerFiestas(){
        String userId = FirebaseAuth.getFacebookIdUsuarioAutenticado();

        usuariosReference.child(userId).child("Fiestas").addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cantidadFiestas = dataSnapshot.getChildrenCount();
                    if(cantidadFiestas > 0){
                        obtenerFiestasDeUsuario(dataSnapshot.getChildren());
                    }
                    else{
                        RespuestaLista<FiestaModel> respuesta = new RespuestaLista<>();
                        respuesta.Exito = true;
                        respuesta.Modelo = new ArrayList<>();
                        listener.callbackObtenerFiestas(respuesta);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    RespuestaLista<FiestaModel> respuesta = new RespuestaLista<>();
                    respuesta.Exito = false;
                    respuesta.Mensaje = "Ocurrió un error al conectar con el servidor.";
                    listener.callbackObtenerFiestas(respuesta);
                }
        });
    }

   private void obtenerFiestasDeUsuario(Iterable<DataSnapshot> fiestasUsuario){
        for (DataSnapshot fiesta: fiestasUsuario){
            String fiestaKey = fiesta.getKey();
            fiestasReference.child(fiestaKey).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    FiestaModel fiesta = snapshot.getValue(FiestaModel.class);
                    addFiesta(fiesta);
                }
                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    RespuestaLista<FiestaModel> respuesta = new RespuestaLista<>();
                    respuesta.Exito = false;
                    respuesta.Mensaje = "Ocurrió un error al conectar con el servidor.";
                    listener.callbackObtenerFiestas(respuesta);
                }
            });
        }
    }

    public void obtenerFiestasPorCodigo(String codigo) {
        fiestasReference.orderByChild("Codigo").equalTo(codigo).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount() > 0){
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                                FiestaModel fiesta = snapshot.getValue(FiestaModel.class);
                                cantidadFiestas++;
                                agregarRelacionUsuarioFiesta(fiesta.key, FirebaseAuth.getFacebookIdUsuarioAutenticado());
                                addFiesta(fiesta);
                            }
                        }
                        else{
                            RespuestaLista<FiestaModel> respuesta = new RespuestaLista<>();
                            respuesta.Exito = false;
                            respuesta.Mensaje = "No existe una fiesta con ese código";
                            listener.callbackObtenerFiestas(respuesta);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        RespuestaLista<FiestaModel> respuesta = new RespuestaLista<>();
                        respuesta.Exito = false;
                        respuesta.Mensaje = "Ocurrió un error al conectar con el servidor.";
                        listener.callbackObtenerFiestas(respuesta);
                    }
                });
    }

    private void addFiesta(FiestaModel fiesta){
        fiestas.add(fiesta);
        if(fiestas.size() == cantidadFiestas){
            RespuestaLista<FiestaModel> respuesta = new RespuestaLista<>();
            respuesta.Exito = true;
            respuesta.Modelo = fiestas;
            listener.callbackObtenerFiestas(respuesta);
        }
    }

    private void agregarRelacionUsuarioFiesta(String keyFiesta, String idUsuario){
        try
        {
            fiestasReference.child("/" + keyFiesta + "/Usuarios/" + idUsuario).setValue(true);
            usuariosReference.child("/" + idUsuario + "/Fiestas/" + keyFiesta).setValue(true);
            RespuestaLista<Object> respuesta = new RespuestaLista<>();
            respuesta.Exito = true;
            respuesta.Mensaje = "¡La fiesta se ha añadido a tu lista de fiestas!";
            listener.callbackAgregarFiesta(respuesta);
        }
        catch(Exception e){
            RespuestaLista<Object> respuesta = new RespuestaLista<>();
            respuesta.Exito = false;
            respuesta.Mensaje = "Ocurrió un error al conectar con el servidor.";
            listener.callbackAgregarFiesta(respuesta);
        }
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