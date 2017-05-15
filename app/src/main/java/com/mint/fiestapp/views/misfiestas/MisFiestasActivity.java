package com.mint.fiestapp.views.misfiestas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.mint.fiestapp.R;
import com.mint.fiestapp.presenters.misfiestas.IMisFiestasPresenter;
import com.mint.fiestapp.presenters.misfiestas.MisFiestasPresenter;


public class MisFiestasActivity extends AppCompatActivity implements IMisFiestasActivity{

    IMisFiestasPresenter presenter = new MisFiestasPresenter(this, this);
    private RecyclerView lisFiestas;
    private RecyclerView.LayoutManager layoutManager;
    private BottomNavigationView navMisFiestas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misfiestas);
        binding();
        events();
        presenter.ObtenerFiestas();
    }

    private void binding(){
        lisFiestas = (RecyclerView) findViewById(R.id.lisFiestas);
    }

    private void events(){
        iniciarListaFiestas();
        iniciarBottomNav();
    }

    private void iniciarListaFiestas(){
        layoutManager = new LinearLayoutManager(this);
        lisFiestas.setLayoutManager(layoutManager);
    }

    private void iniciarBottomNav(){
        navMisFiestas = (BottomNavigationView) findViewById(R.id.navMisFiestas);
        navMisFiestas.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_misfiestas:

                            break;
                        case R.id.action_nuevafiesta:

                            break;
                        case R.id.action_notificaciones:

                            break;
                    }
                    return false;
                }
            });
    }

    @Override
    public void mostrarFiestas(RecyclerView.Adapter adapter){
        lisFiestas.setAdapter(adapter);
    }

    @Override
    public void mostrarError(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
