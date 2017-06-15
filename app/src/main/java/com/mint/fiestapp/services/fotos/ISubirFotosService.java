package com.mint.fiestapp.services.fotos;

import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.services.IService;

import java.util.List;

public interface ISubirFotosService extends IService {
    void subirFotos(List<FotoModel> fotos);
}
