package com.mint.fiestapp.presenters.fotos;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;
import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.FirebaseAuth;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.UsuarioModel;
import com.mint.fiestapp.models.fotos.ISubirFotosModel;
import com.mint.fiestapp.models.fotos.SubirFotosModel;
import com.mint.fiestapp.services.Servicios;
import com.mint.fiestapp.views.IActivity;
import com.mint.fiestapp.views.fotos.FotosActivity;
import com.mint.fiestapp.views.fotos.ISubirFotosActivity;
import com.mint.fiestapp.views.fotos.SubirFotosActivity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.attr.data;

public class SubirFotosPresenter implements ISubirFotosPresenter, SubirFotosModel.ISubirFotosModelCallback {

    ISubirFotosModel fotosModel = new SubirFotosModel(this);
    ISubirFotosActivity activity;
    Context contexto;
    String keyFiesta;
    List<FotoModel> fotosASubir;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyManager;
    int ID_NOTIFICACION_PROGRESO = 1;

    @Override
    public void setActivity(IActivity activity) {
        this.activity = (ISubirFotosActivity)activity;
    }

    @Override
    public void setContext(Context context) {
        contexto = context;
    }

    @Override
    public void setKeyFiesta(String key){
        this.keyFiesta = key;
    }

    @Override
    public void iniciarActivity(Context context) {
        Intent intent = new Intent(context, SubirFotosActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentKeys.PRESENTER, this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void agregarFoto(byte[] bytesFoto){
        FotoModel nuevaFoto = new FotoModel();
        nuevaFoto.Bytes = bytesFoto;

        if(fotosASubir == null){
            fotosASubir = new ArrayList<FotoModel>();
        }
        fotosASubir.add(nuevaFoto);
    }

    @Override
    public void getPrimeraFoto(){
        this.activity.addFoto(fotosASubir.get(0));
    }

    @Override
    public void subirFotos(String descripcion){
        activity.mostrarProgreso();
        for(FotoModel foto : fotosASubir){
            foto.keyFiesta = keyFiesta;
            foto.Descripcion = descripcion;
            foto.Usuario = new UsuarioModel(FirebaseAuth.getFacebookIdUsuarioAutenticado(),"");
            foto.FechaHora = new Date().getTime() + "";
        }
        fotosModel.subirFotos(fotosASubir);
        mostrarNotificacionProgreso();
        activity.finishActivity();
    }

    private void mostrarNotificacionProgreso(){
        activity.mostrarProgreso();

        mNotifyManager =
                (NotificationManager) contexto.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(contexto);
        mBuilder.setContentTitle("Fiestapp!")
                .setContentText("La foto se está subiendo")
                .setSmallIcon(R.mipmap.ic_launcher);

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        mBuilder.setProgress(0, 0, true);
                        mNotifyManager.notify(ID_NOTIFICACION_PROGRESO, mBuilder.build());
                }}
        ).start();
    }

    @Override
    public void callbackSubirFoto(boolean exito) {
        mBuilder.setContentText("¡La foto ya está en la galería de la fiesta!").setProgress(0,0,false);
        mNotifyManager.notify(ID_NOTIFICACION_PROGRESO, mBuilder.build());
    }
}
