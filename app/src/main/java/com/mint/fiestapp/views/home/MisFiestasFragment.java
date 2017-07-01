package com.mint.fiestapp.views.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mint.fiestapp.R;


public class MisFiestasFragment extends Fragment {

    //region Controles
    RecyclerView lisFiestas;
    View layout;
    LinearLayout linNoHayFiestas;
    boolean showProgreso = false;
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(layout != null) {
            return layout;
        }
        ((HomeActivity)getActivity()).mostrarProgreso();
        showProgreso = true;
        layout = inflater.inflate(R.layout.fragment_mis_fiestas, container, false);
        binding();
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(showProgreso){
            ((HomeActivity)getActivity()).mostrarProgreso();
        }
        else{
            ((HomeActivity)getActivity()).ocultarProgreso();
        }
    }

    public void binding(){
        lisFiestas = (RecyclerView) layout.findViewById(R.id.lisFiestas);
        linNoHayFiestas = (LinearLayout) layout.findViewById(R.id.linNoFiestas);
        lisFiestas.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        showProgreso = false;
        lisFiestas.setAdapter(adapter);
        showFiestas(adapter.getItemCount() > 0);
    }

    public void showFiestas(boolean hayFiestas){
        if(hayFiestas){
            linNoHayFiestas.setVisibility(View.GONE);
            lisFiestas.setVisibility(View.VISIBLE);
        }
        else{
            linNoHayFiestas.setVisibility(View.VISIBLE);
            lisFiestas.setVisibility(View.GONE);
        }
    }
}
