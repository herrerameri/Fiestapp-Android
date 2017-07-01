package com.mint.fiestapp.views.home;

import android.support.v7.widget.RecyclerView;

import com.mint.fiestapp.views.IActivity;

public interface IHomeActivity extends IActivity {
    void mostrarFiestas(RecyclerView.Adapter adapter);
    void mostrarMensaje(String mensaje);
    void agregarFiesta(String codigo);
}
