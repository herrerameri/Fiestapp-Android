package com.mint.fiestapp.presenters.misfiestas;

import android.content.Context;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.presenters.IPresenter;

import java.util.List;

public interface IMisFiestasPresenter extends IPresenter {
    void obtenerFiestas();
    void mostrarFiestas(List<FiestaModel> modelo);
    void mostrarError(String mensaje);
}
