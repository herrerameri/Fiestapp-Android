package com.mint.fiestapp.services.fotos;

import com.mint.fiestapp.services.IService;

public interface IFotosService extends IService {
    void obtenerUltimasFotos(int cantidad, String keyFiesta);
}
