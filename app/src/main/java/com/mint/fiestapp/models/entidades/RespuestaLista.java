package com.mint.fiestapp.models.entidades;

import java.util.List;

public class RespuestaLista<T> {
    public int Codigo;
    public String Mensaje;
    public boolean Exito;
    public List<T> Modelo;
}
