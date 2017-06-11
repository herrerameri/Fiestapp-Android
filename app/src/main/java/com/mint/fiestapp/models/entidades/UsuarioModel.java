package com.mint.fiestapp.models.entidades;

import java.io.Serializable;

public class UsuarioModel implements Serializable {

    public UsuarioModel(){}

    public UsuarioModel(String id, String nombre){
        Id = id;
        Nombre = nombre;
    }

    public String Id;
    public String Nombre;
}
