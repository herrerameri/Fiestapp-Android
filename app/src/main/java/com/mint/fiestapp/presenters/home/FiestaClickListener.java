package com.mint.fiestapp.presenters.home;

import android.content.Context;

import com.mint.fiestapp.models.entidades.FiestaModel;
import com.mint.fiestapp.presenters.fiesta.FiestaPresenter;
import com.mint.fiestapp.presenters.fiesta.IFiestaPresenter;
import com.mint.fiestapp.presenters.home.MisFiestasAdapter.OnFiestaClickListener;


public class FiestaClickListener implements OnFiestaClickListener {
    @Override
    public void onFiestaClick(Context context, FiestaModel item) {
        IFiestaPresenter fiestaPresenter = new FiestaPresenter();
        fiestaPresenter.setFiesta(item);
        fiestaPresenter.iniciarActivity(context);
    }
}
