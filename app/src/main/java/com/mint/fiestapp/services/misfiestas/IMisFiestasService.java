package com.mint.fiestapp.services.misfiestas;

import com.mint.fiestapp.services.IService;

public interface IMisFiestasService extends IService{
    void obtenerFiestas();
    void crearFiestasInitialData();
    void obtenerFiestasPorCodigo(String codigo);
}
