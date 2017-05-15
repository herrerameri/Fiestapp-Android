package com.mint.fiestapp.views.misfiestas;

import android.support.v7.widget.RecyclerView;

public interface IMisFiestasActivity {
    void mostrarFiestas(RecyclerView.Adapter adapter);
    void mostrarError(String mensaje);
}
