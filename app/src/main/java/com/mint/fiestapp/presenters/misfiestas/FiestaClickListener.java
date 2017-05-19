package com.mint.fiestapp.presenters.misfiestas;

import android.content.Context;
import android.content.Intent;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.presenters.fiesta.FiestaPresenter;
import com.mint.fiestapp.presenters.fiesta.IFiestaPresenter;
import com.mint.fiestapp.presenters.misfiestas.MisFiestasAdapter.OnFiestaClickListener;
import com.mint.fiestapp.views.fiesta.FiestaActivity;


public class FiestaClickListener implements OnFiestaClickListener {
    @Override
    public void onFiestaClick(Context context, FiestaModel item) {
        IFiestaPresenter fiestaPresenter = new FiestaPresenter();
        fiestaPresenter.setFiesta(item);
        fiestaPresenter.iniciarActivity(context);
    }
}
