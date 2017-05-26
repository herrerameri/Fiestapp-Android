package com.mint.fiestapp.views;


import com.mint.fiestapp.presenters.IPresenter;

import java.io.Serializable;

public interface IActivity extends Serializable {
    void binding();
    void eventos();
    void iniciarPresenter(IPresenter presenter);
    void iniciarActivityPresenter();
    void mostrarProgreso();
}
