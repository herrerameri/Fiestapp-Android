package com.mint.fiestapp.comun;

public class Facebook {

    public static String getFotoPerfil(String facebookId) {
        return "https://graph.facebook.com/"+facebookId+"/picture?width=200&height=200";
    }
}

