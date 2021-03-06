package com.mint.fiestapp.presenters.fotos;

import android.graphics.Bitmap;
import android.view.View;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.presenters.IPresenter;

public interface IFotosPresenter extends IPresenter {
    void setKeyFiesta(String key);
    void obtenerFotos();
    void obtenerMasFotos();
    void removeFotos();

    void subirFotos(byte[] nuevaFoto);
}
