package com.mint.fiestapp.services.fotos;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.Respuesta;
import com.mint.fiestapp.models.entidades.RespuestaLista;
import com.mint.fiestapp.models.entidades.UsuarioModel;
import com.mint.fiestapp.services.Servicios;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

public class SubirFotosService extends Servicios implements ISubirFotosService {


    public interface ISubirFotosServiceCallback{
        void callbackSubirFotos(Respuesta<Object> respuesta);
    }

    ISubirFotosServiceCallback listener;

    public SubirFotosService(ISubirFotosServiceCallback listener){
        this.listener = listener;
    }

    @Override
    public void subirFotos(List<FotoModel> fotos) {
        for (final FotoModel foto: fotos) {
            UploadTask uploadTask = fotosStorage.child(foto.FechaHora + " " + foto.Usuario.Id).putBytes(foto.Bytes);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Respuesta<Object> respuesta = new Respuesta<Object>();
                    respuesta.Exito = false;
                    respuesta.Mensaje = "Ocurrió un error al subir la foto a la galería";
                    listener.callbackSubirFotos(respuesta);

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    FotoModel nuevaFoto = new FotoModel();
                    nuevaFoto.Descripcion = foto.Descripcion;
                    nuevaFoto.Imagen = downloadUrl.toString();
                    nuevaFoto.Usuario = new UsuarioModel(foto.Usuario.Id, "");
                    nuevaFoto.FechaHora = foto.FechaHora;
                    nuevaFoto.keyFiesta = foto.keyFiesta;
                    agregarRegistroFoto(nuevaFoto);
                }
            });
        }
    }

    public void agregarRegistroFoto(FotoModel foto){
        String key = fotosReference.child(foto.keyFiesta).push().getKey();
        foto.setKey(key);
        fotosReference.child(foto.keyFiesta).child(key).setValue(foto);

        Respuesta<Object> respuesta = new Respuesta<Object>();
        respuesta.Exito = true;
        listener.callbackSubirFotos(respuesta);
    }
}
