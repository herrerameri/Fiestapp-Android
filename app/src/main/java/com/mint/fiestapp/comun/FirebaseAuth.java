package com.mint.fiestapp.comun;

import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuth {

    public static FirebaseUser getUsuarioAutenticado(){
        try {
            return com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public static String getFacebookIdUsuarioAutenticado(){
        try {
            return getUsuarioAutenticado().getProviderData().get(1).getUid();
        }
        catch(Exception e)
        {
            return "";
        }
    }
}
