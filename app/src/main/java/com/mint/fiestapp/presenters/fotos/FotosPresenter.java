package com.mint.fiestapp.presenters.fotos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.mint.fiestapp.comun.FirebaseAuth;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.UsuarioModel;
import com.mint.fiestapp.models.fotos.FotosModel;
import com.mint.fiestapp.models.fotos.IFotosModel;
import com.mint.fiestapp.views.IActivity;
import com.mint.fiestapp.views.fotos.FotosActivity;
import com.mint.fiestapp.views.fotos.IFotosActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FotosPresenter implements IFotosPresenter, FotosModel.IFotosModelCallback, FotosAdapter.OnClickListener {

    IFotosModel fotosModel = new FotosModel(this);
    FotosAdapter adapterFotos;
    IFotosActivity activity;
    Context contexto;
    String keyFiesta;

    @Override
    public void setActivity(IActivity activity) {
        this.activity = (IFotosActivity)activity;
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
        Intent intent = new Intent(context, FotosActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentKeys.PRESENTER, this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void obtenerFotos(){
        activity.mostrarProgreso();
        fotosModel.obtenerFotos(activity.PAGE_SIZE, activity.getKeyUltimaFoto(), keyFiesta);
    }

    @Override
    public void obtenerMasFotos(){
        activity.mostrarProgreso();
        fotosModel.obtenerFotos(activity.PAGE_SIZE, activity.getKeyUltimaFoto(), keyFiesta);
    }

    public void subirFotos(byte[] nuevaFoto){
        ISubirFotosPresenter subirFotosPresenter = new SubirFotosPresenter();
        subirFotosPresenter.setKeyFiesta(keyFiesta);
        subirFotosPresenter.agregarFoto(nuevaFoto);
        subirFotosPresenter.iniciarActivity(contexto);
    }

    //region FotosModel.IFotosModelCallback
    @Override
    public void mostrarFotos(List<FotoModel> modelo) {
        activity.ocultarProgreso();
        if(modelo.size() > 0){
            activity.setKeyUltimaFoto(modelo.get(modelo.size()-1).FechaHora);
        }
        if(adapterFotos == null){
            adapterFotos = new FotosAdapter(modelo, contexto, this);
            activity.mostrarFotos(adapterFotos);
        }
        else{
            if(modelo.size() < activity.PAGE_SIZE){
                activity.setUltimaPagina(true);
            }
            adapterFotos.addDatos(modelo);
        }
    }

    @Override
    public void removeFotos(){
        activity.setKeyUltimaFoto("");
        adapterFotos = new FotosAdapter(new ArrayList<FotoModel>(), contexto, this);
        activity.mostrarFotos(adapterFotos);
    }

    @Override
    public void mostrarError(String mensaje) {
        activity.ocultarProgreso();
        // TODO
    }

    @Override
    public void setUsuariosFoto(UsuarioModel usuario) {
        adapterFotos.setUsuario(usuario.Id, usuario.Nombre);
    }

    @Override
    public void agregarReaccion(String keyFoto, HashMap<String, UsuarioModel> reaccion) {
        adapterFotos.addReaccionFoto(keyFoto, reaccion);
    }

    @Override
    public void quitarReaccion(String keyFoto, String keyReaccion) {
        adapterFotos.removeReaccionFoto(keyFoto, keyReaccion);
    }
    //endregion

    //region FotosAdapter.OnClickListener
    @Override
    public void onFotoClick(Context context, FotoModel item) {

    }

    @Override
    public void onReaccionClick(FotoModel item) {
        if(item.YoReaccione()){
            String keyReaccion = "";
            Iterator it = item.Reacciones.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                if(((UsuarioModel)pair.getValue()).Id.equals(FirebaseAuth.getFacebookIdUsuarioAutenticado()))
                {
                    keyReaccion = (String)pair.getKey();
                }
            }
            fotosModel.quitarReaccion(item.keyFiesta, item.key, keyReaccion);
        }
        else{
            fotosModel.agregarReaccion(item.keyFiesta, item.key, FirebaseAuth.getFacebookIdUsuarioAutenticado());
        }
    }

    @Override
    public void onCompartirClick(Uri bitmapUri) {
        if (bitmapUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
            shareIntent.setType("image/*");
            contexto.startActivity(Intent.createChooser(shareIntent, "Compartir imagen de la fiesta"));
        }
    }
    //endregion
}
