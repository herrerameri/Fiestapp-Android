package com.mint.fiestapp.views.fotos;


import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.presenters.fotos.FotosAdapter;
import com.mint.fiestapp.views.IActivity;

import java.util.List;

public interface IFotosActivity extends IActivity {
    void mostrarFotos(FotosAdapter adapter);
    String getKeyUltimaFoto();
    void setUltimaPagina(boolean esUltimaPagina);
    void setKeyUltimaFoto(String key);
    int PAGE_SIZE = 4;
}
