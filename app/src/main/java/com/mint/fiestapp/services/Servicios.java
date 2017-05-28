package com.mint.fiestapp.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mint.fiestapp.models.IModel;

public class Servicios {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public DatabaseReference fiestasReference = database.getReference("fiestas");
    public DatabaseReference usuariosReference = database.getReference("usuarios");
    public DatabaseReference fotosReference = database.getReference("fotos");
}
