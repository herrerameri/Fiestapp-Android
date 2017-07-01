package com.mint.fiestapp.views.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mint.fiestapp.R;


public class AgregarFiestaFragment extends Fragment {

    //region Controles
    View layout;
    Button btnAgregarFiesta;
    EditText ediCodigoFiesta;
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(layout != null) {
            return layout;
        }
        ((HomeActivity)getActivity()).ocultarProgreso();
        layout = inflater.inflate(R.layout.fragment_agregar_fiesta, container, false);
        binding();
        eventos();
        return layout;
    }

    public void binding(){
        ediCodigoFiesta = (EditText) layout.findViewById(R.id.ediCodigoFiesta);
        btnAgregarFiesta = (Button) layout.findViewById(R.id.btnAgregarFiesta);
    }

    public void eventos(){
        btnAgregarFiesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = ediCodigoFiesta.getText().toString();
                if(codigo.length() == 0){
                    ((HomeActivity)getActivity()).mostrarMensaje(getResources().getString(R.string.codigo_vacio));
                }
                else{
                    ((HomeActivity)getActivity()).agregarFiesta(codigo);
                }
            }
        });
    }
}
