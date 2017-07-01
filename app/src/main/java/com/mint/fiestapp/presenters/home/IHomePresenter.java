package com.mint.fiestapp.presenters.home;

import com.mint.fiestapp.presenters.IPresenter;

public interface IHomePresenter extends IPresenter {
    void obtenerFiestas();
    void volverALogin();
    void agregarFiesta(String codigo);
}
