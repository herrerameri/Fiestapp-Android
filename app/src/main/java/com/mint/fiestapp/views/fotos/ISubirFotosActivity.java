package com.mint.fiestapp.views.fotos;


import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.presenters.fotos.FotosAdapter;
import com.mint.fiestapp.views.IActivity;

public interface ISubirFotosActivity extends IActivity {
    void addFoto(FotoModel foto);
}
