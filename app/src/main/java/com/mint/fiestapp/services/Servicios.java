package com.mint.fiestapp.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mint.fiestapp.models.IModel;

public class Servicios {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference fiestasReference = database.getReference("fiestas");
}
