package com.mint.fiestapp.views;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mint.fiestapp.R;
import com.mint.fiestapp.models.misfiestas.IMisFiestasModel;
import com.mint.fiestapp.models.misfiestas.MisFiestasModel;
import com.mint.fiestapp.presenters.misfiestas.IMisFiestasPresenter;
import com.mint.fiestapp.presenters.misfiestas.MisFiestasPresenter;
import com.mint.fiestapp.services.misfiestas.IMisFiestasService;
import com.mint.fiestapp.services.misfiestas.MisFiestasService;
import com.mint.fiestapp.views.misfiestas.MisFiestasActivity;

public class MainActivity extends AppCompatActivity {
    private final int DURACION_SPLASH = 3000; // 3 segundos

    private Button btnMisFiestas;
    private Button btnIniciarSampleDataFiestas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                IMisFiestasPresenter misFiestasPresenter = new MisFiestasPresenter();
                misFiestasPresenter.iniciarActivity(MainActivity.this);
                finish();
            };
        }, DURACION_SPLASH);

        //btnMisFiestas.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        IMisFiestasPresenter misFiestasPresenter = new MisFiestasPresenter();
        //        misFiestasPresenter.iniciarActivity(MainActivity.this);
        //    }
        //});

        //btnIniciarSampleDataFiestas = (Button)findViewById(R.id.btnIniciarSampleDataFiestas);
        //btnIniciarSampleDataFiestas.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
                //IMisFiestasService misFiestasService = new MisFiestasService();
                //misFiestasService.crearFiestasInitialData();
        //    }
        //});
    }
}
