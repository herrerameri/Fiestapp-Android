package com.mint.fiestapp.models.fotos;

import com.mint.fiestapp.models.IModel;
import com.mint.fiestapp.models.entidades.FotoModel;

import java.util.List;

public interface ISubirFotosModel extends IModel {
    void subirFotos(List<FotoModel> modelo);
}
