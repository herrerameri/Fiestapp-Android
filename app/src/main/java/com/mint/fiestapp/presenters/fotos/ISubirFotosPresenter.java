package com.mint.fiestapp.presenters.fotos;

import com.mint.fiestapp.presenters.IPresenter;

public interface ISubirFotosPresenter extends IPresenter {
    void setKeyFiesta(String key);
    void agregarFoto(byte[] bytesFoto);
}
