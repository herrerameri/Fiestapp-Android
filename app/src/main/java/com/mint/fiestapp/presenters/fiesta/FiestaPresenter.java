package com.mint.fiestapp.presenters.fiesta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.FirebaseAuth;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.models.entidades.UsuarioModel;
import com.mint.fiestapp.models.fotos.FotosModel;
import com.mint.fiestapp.models.fotos.IFotosModel;
import com.mint.fiestapp.presenters.fotos.FotosPresenter;
import com.mint.fiestapp.presenters.fotos.IFotosPresenter;
import com.mint.fiestapp.presenters.fotos.ISubirFotosPresenter;
import com.mint.fiestapp.presenters.fotos.SubirFotosPresenter;
import com.mint.fiestapp.views.IActivity;
import com.mint.fiestapp.views.fiesta.FiestaActivity;
import com.mint.fiestapp.views.fiesta.FotosView;
import com.mint.fiestapp.views.fiesta.IFiestaActivity;
import com.mint.fiestapp.views.fiesta.MeGustasView;
import com.mint.fiestapp.views.fiesta.MusicaView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FiestaPresenter implements IFiestaPresenter, FotosModel.IFotosModelCallback, FotosView.IFotosViewClickListener, MeGustasView.IMeGustasViewClickListener {

    IFotosModel fotosModel = new FotosModel(this);
    IFiestaActivity activity;
    Context contexto;
    FiestaModel model;
    FotosView viewFotos;
    MeGustasView viewMeGustas;
    MusicaView viewMusica;

    @Override
    public void setActivity(IActivity activity) {
        this.activity = (IFiestaActivity)activity;
    }

    @Override
    public void setContext(Context context) {
        contexto = context;
    }

    @Override
    public void setFiesta(FiestaModel model){
        this.model = model;
    }

    @Override
    public int getTotalFuncionalidades() {
        return model.Funcionalidades.size();
    }

    @Override
    public double getLatitud() { return Double.parseDouble(model.Ubicacion.Latitud); }

    @Override
    public double getLongitud() { return Double.parseDouble(model.Ubicacion.Longitud); }

    @Override
    public String getFecha() { return model.DiaMes(); }

    @Override
    public String getHora() { return model.Hora(); }

    @Override
    public String getNombreUbicacion() { return model.Ubicacion.Nombre; }

    @Override
    public String getTitulo() {
        return model.Nombre;
    }

    @Override
    public String getDescripcion() {
        return model.Detalle;
    }

    @Override
    public String getFoto() {
        return model.Imagen;
    }

    @Override
    public String getCantidadFotos() {
        return "10";
    }

    @Override
    public String getCantidadInvitados() {
        return model.CantidadInvitados;
    }

    @Override
    public String getCantidadDias() {
        return String.format("%d", model.CantidadDiasRestantes() );
    }

    @Override
    public void iniciarActivity(Context context) {
        Intent intent = new Intent(context, FiestaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentKeys.PRESENTER, this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public View getLayoutFuncionalidad(int indice) {
        switch (model.Funcionalidades.get(indice))
        {
            case ASISTENCIA:
            case GALERIA:
                viewFotos = new FotosView(this);
                fotosModel.obtenerFotos(8, "", model.key);
                return viewFotos.getLayout(contexto);
            case MENU:
            case MEGUSTAS:
                viewMeGustas = new MeGustasView(this);
                return viewMeGustas.getLayout(contexto, obtenerInvitadosAleatorios(3));
            case VESTIMENTA:
            case REGALOS:
            case MUSICA:
                viewMusica = new MusicaView();
                return viewMusica.getLayout(contexto, Long.parseLong(model.FechaHora) < new Date().getTime());
        }
        return null;
    }

    private List<String> obtenerInvitadosAleatorios(int cantidad){
        List<String> invitados = new ArrayList<>();
        List<String> listaUsuarios = new ArrayList<>();
        String miIdUsuario = FirebaseAuth.getFacebookIdUsuarioAutenticado();
        listaUsuarios.addAll(model.Usuarios.keySet());
        listaUsuarios.remove(miIdUsuario);

        if(listaUsuarios.size() <= cantidad)
        {
            return listaUsuarios;
        }

        Random Dice = new Random();
        for(int i=0; i < cantidad; i++){
            int indice = Dice.nextInt(listaUsuarios.size());
            if(!invitados.contains(listaUsuarios.get(indice))){
                invitados.add(listaUsuarios.get(indice));
            }
            else{
                i--;
            }
        }

        return invitados;
    }

    public void subirFotos(byte[] nuevaFoto){
        ISubirFotosPresenter subirFotosPresenter = new SubirFotosPresenter();
        subirFotosPresenter.setKeyFiesta(model.key);
        subirFotosPresenter.agregarFoto(nuevaFoto);
        subirFotosPresenter.iniciarActivity(contexto);
    }

    //region FotosModel.IFotosModelCallback,
    @Override
    public void mostrarFotos(List<FotoModel> modelo) {
        viewFotos.setFotos(modelo);
    }

    @Override
    public void mostrarError(String mensaje) {
        // TODO
    }

    @Override
    public void setUsuariosFoto(UsuarioModel usuario) {
        // TODO
    }

    @Override
    public void agregarReaccion(String keyFoto, HashMap<String, UsuarioModel> reaccion) {
    }

    @Override
    public void quitarReaccion(String keyFoto, String keyReaccion) {

    }
    //endregion


    //region FotosView.IFotosViewClickListener
    @Override
    public void verGaleriaClickCallback() {
        IFotosPresenter fotosPresenter = new FotosPresenter();
        fotosPresenter.setKeyFiesta(model.key);
        fotosPresenter.iniciarActivity(contexto);
    }

    @Override
    public void subirFotoClickCallback() {
        activity.mostrarDialogoSubirFoto();
    }
    //endregion

    //region MeGustasView.IMeGustasViewClickListener
    @Override
    public void quienGustaClickCallback() {

    }
    //endregion
}
