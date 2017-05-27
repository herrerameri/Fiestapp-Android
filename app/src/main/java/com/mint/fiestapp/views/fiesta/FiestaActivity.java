package com.mint.fiestapp.views.fiesta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.presenters.IPresenter;
import com.mint.fiestapp.presenters.fiesta.IFiestaPresenter;
import com.mint.fiestapp.views.BaseActivity;
import com.mint.fiestapp.views.custom.ImageCircleTransform;
import com.squareup.picasso.Picasso;


public class FiestaActivity extends BaseActivity implements IFiestaActivity, OnMapReadyCallback {

    IFiestaPresenter presenter;
    private LinearLayout linOpcionesFiesta;
    private ImageView imgFotoFiesta;
    private TextView texTitulo;
    private TextView texDescripcion;
    private TextView texCantidadInvitados;
    private TextView texCantidadFotos;
    private TextView texCantidadDias;

    private TextView texFecha;
    private TextView texHora;
    private TextView texNombreUbicacion;
    private static SupportMapFragment frgMapUbicacionFiesta;

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
        linOpcionesFiesta = (LinearLayout)findViewById(R.id.linOpcionesFiesta);
        texTitulo = (TextView)findViewById(R.id.texTitulo);
        texDescripcion = (TextView)findViewById(R.id.texDescripcion);
        texCantidadDias = (TextView)findViewById(R.id.texCantidadDias);
        texCantidadFotos = (TextView)findViewById(R.id.texCantidadFotos);
        texCantidadInvitados = (TextView)findViewById(R.id.texCantidadInvitados);
        imgFotoFiesta = (ImageView)findViewById(R.id.imgFotoFiesta);

        texFecha = (TextView)findViewById(R.id.texFecha);
        texHora = (TextView)findViewById(R.id.texHora);
        texNombreUbicacion = (TextView)findViewById(R.id.texNombreUbicacion);
        frgMapUbicacionFiesta = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapUbicacionFiesta);
        frgMapUbicacionFiesta.getMapAsync(this);
    }

    @Override
    public void eventos() {
        iniciarFoto();
        iniciarTextos();
        //iniciarFuncionalidadesFiesta();
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
        texFecha.setText(presenter.getFecha());
        texHora.setText(presenter.getHora());
        texNombreUbicacion.setText(presenter.getNombreUbicacion());
    }

    private void iniciarFuncionalidadesFiesta(){
        linOpcionesFiesta.removeAllViews();
        int totalFuncionalidades = presenter.getTotalFuncionalidades();

        for(int indice = 0; indice < totalFuncionalidades; indice++){
            linOpcionesFiesta.addView(presenter.getLayoutFuncionalidad(indice));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng ubicacionFiesta = new LatLng(presenter.getLatitud(), presenter.getLongitud());
        googleMap.addMarker(new MarkerOptions().position(ubicacionFiesta).title(presenter.getNombreUbicacion()));
        googleMap.setMinZoomPreference(getResources().getInteger(R.integer.maps_zoom_default));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacionFiesta));
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

    @Override
    public void mostrarProgreso(){}
}
