package com.mint.fiestapp.views.fiesta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.presenters.IPresenter;
import com.mint.fiestapp.presenters.fiesta.IFiestaPresenter;
import com.mint.fiestapp.views.custom.ImageCircleTransform;
import com.squareup.picasso.Picasso;


public class FiestaActivity extends AppCompatActivity implements IFiestaActivity {

    IFiestaPresenter presenter;
    LinearLayout linOpcionesFiesta;
    ImageView imgFotoFiesta;
    TextView texTitulo;
    TextView texDescripcion;
    TextView texCantidadInvitados;
    TextView texCantidadFotos;
    TextView texCantidadDias;

    int COLUMNAS = 3;
    int HEIGHT_FUNCIONALIDAD = 350;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiesta);
        if(getIntent().getExtras() != null){
            iniciarPresenter((IFiestaPresenter)getIntent().getExtras().getSerializable(IntentKeys.PRESENTER));
        }
        binding();
        eventos();
    }

    @Override
    public void binding(){
        linOpcionesFiesta = (LinearLayout) findViewById(R.id.linOpcionesFiesta);
        texTitulo = (TextView)findViewById(R.id.texTitulo);
        texDescripcion = (TextView)findViewById(R.id.texDescripcion);
        texCantidadDias = (TextView)findViewById(R.id.texCantidadDias);
        texCantidadFotos = (TextView)findViewById(R.id.texCantidadFotos);
        texCantidadInvitados = (TextView)findViewById(R.id.texCantidadInvitados);
        imgFotoFiesta = (ImageView)findViewById(R.id.imgFotoFiesta);
    }

    @Override
    public void eventos() {
        iniciarFoto();
        iniciarTextos();
        iniciarGrid();
    }

    private void iniciarFoto(){
        Picasso.with(this).load(presenter.getFoto())
                .transform(new ImageCircleTransform())
                .into(imgFotoFiesta);
    }

    private void iniciarTextos(){
        texTitulo.setText(presenter.getTitulo());
        texDescripcion.setText(presenter.getDescripcion());
        texCantidadDias.setText(presenter.getCantidadDias());
        texCantidadFotos.setText(presenter.getCantidadFotos());
        texCantidadInvitados.setText(presenter.getCantidadInvitados());
    }

    private void iniciarGrid(){
        linOpcionesFiesta.removeAllViews();
        int totalFuncionalidades = presenter.getTotalFuncionalidades();
        int cantidadFilasCompletas = totalFuncionalidades / COLUMNAS;
        int cantidadFuncionalidadesFilaIncompleta = totalFuncionalidades % COLUMNAS;

        int posicionEnFila = 0;
        int indiceFuncionalidad = 0;

        LinearLayout.LayoutParams parametrosLayout = new LinearLayout.LayoutParams(0, HEIGHT_FUNCIONALIDAD, (float)1/COLUMNAS);

        while(cantidadFilasCompletas > 0){
            LinearLayout layoutFila = new LinearLayout(this);
            layoutFila.setOrientation(LinearLayout.HORIZONTAL);

            while(posicionEnFila < COLUMNAS){
                layoutFila.addView(prepararCardFuncionalidad(indiceFuncionalidad), parametrosLayout);
                posicionEnFila++;
                indiceFuncionalidad++;
            }

            linOpcionesFiesta.addView(layoutFila);
            cantidadFilasCompletas--;
            posicionEnFila = 0;
        }

        if (cantidadFuncionalidadesFilaIncompleta != 0) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            parametrosLayout = new LinearLayout.LayoutParams(0, HEIGHT_FUNCIONALIDAD,(float)1/COLUMNAS);
            LinearLayout cardFuncionalidad = null;

            while(posicionEnFila < cantidadFuncionalidadesFilaIncompleta){
                cardFuncionalidad = prepararCardFuncionalidad(indiceFuncionalidad);
                layout.addView(cardFuncionalidad, parametrosLayout);
                posicionEnFila++;
                indiceFuncionalidad++;
            }

            while(posicionEnFila < COLUMNAS){
                cardFuncionalidad = new LinearLayout(this);
                cardFuncionalidad.setVisibility(View.INVISIBLE);
                layout.addView(cardFuncionalidad, parametrosLayout);
                posicionEnFila++;
            }
            linOpcionesFiesta.addView(layout);
        }
    }

    @Override
    public void iniciarPresenter(IPresenter presenter){
        this.presenter = (IFiestaPresenter) presenter;
        iniciarActivityPresenter();
    }

    @Override
    public void iniciarActivityPresenter(){
        presenter.setContext(this);
        presenter.setActivity(this);
    }

    private LinearLayout prepararCardFuncionalidad(final int indice){
        LinearLayout cardFuncionalidad = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_fiesta_funcionalidad, null);
        int icono = presenter.getIdIconoFuncionalidad(indice);
        String titulo = presenter.getTituloFuncionalidad(indice);
        ((ImageView)cardFuncionalidad.findViewById(R.id.imgIconoFuncionalidad)).setImageResource(icono);
        ((TextView)cardFuncionalidad.findViewById(R.id.texTituloFuncionalidad)).setText(titulo);
        cardFuncionalidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.clickOnFuncionalidad(indice);
            }
        });
        return cardFuncionalidad;
    }
}
