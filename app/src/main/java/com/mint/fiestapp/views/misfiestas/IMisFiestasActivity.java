package com.mint.fiestapp.views.misfiestas;

import android.support.v7.widget.RecyclerView;

import com.mint.fiestapp.views.IActivity;

import java.io.Serializable;

public interface IMisFiestasActivity extends IActivity {
    void mostrarFiestas(RecyclerView.Adapter adapter);
    void mostrarError(String mensaje);
}
