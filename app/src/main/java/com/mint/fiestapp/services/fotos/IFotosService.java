package com.mint.fiestapp.services.fotos;

import com.mint.fiestapp.services.IService;

public interface IFotosService extends IService {
    void obtenerUltimasFotos(int cantidad, String keyFiesta);
    void obtenerFotosConectado(int cantidad, String keyFiesta);
    void obtenerPaginaFotos(int cantidad, String keyUltimaFoto, String keyFiesta);
    void agregarReaccion(String keyFiesta, String keyFoto, String idUsuario);
    void quitarReaccion(String keyFiesta, String keyFoto, String idUsuario);
}
