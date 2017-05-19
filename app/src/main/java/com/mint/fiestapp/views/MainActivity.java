package com.mint.fiestapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mint.fiestapp.R;
import com.mint.fiestapp.presenters.misfiestas.IMisFiestasPresenter;
import com.mint.fiestapp.presenters.misfiestas.MisFiestasPresenter;
import com.mint.fiestapp.views.misfiestas.MisFiestasActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnMisFiestas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMisFiestas = (Button)findViewById(R.id.btnFiestas);
        btnMisFiestas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMisFiestasPresenter misFiestasPresenter = new MisFiestasPresenter();
                misFiestasPresenter.iniciarActivity(MainActivity.this);
            }
        });
    }
}
