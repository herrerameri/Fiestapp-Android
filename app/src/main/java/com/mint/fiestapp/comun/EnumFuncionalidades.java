package com.mint.fiestapp.comun;

import java.io.Serializable;

public enum EnumFuncionalidades implements Serializable {

    GALERIA("Fotos"),
    MEGUSTAS("¡Me gustás!"),
    MENU("Menú"),
    REGALOS("Lista de Regalos"),
    ASISTENCIA("Asistencia"),
    INFORMACION("Información"),
    VESTIMENTA("Vestimenta"),
    MUSICA("Música");

    private String nombre;

    EnumFuncionalidades(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
