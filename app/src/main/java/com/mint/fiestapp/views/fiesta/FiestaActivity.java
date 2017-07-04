package com.mint.fiestapp.views.fiesta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FiestaActivity extends BaseActivity implements IFiestaActivity, OnMapReadyCallback {

    IFiestaPresenter presenter;

    //region Controles
    @BindView(R.id.linOpcionesFiesta) LinearLayout linOpcionesFiesta;
    @BindView(R.id.imgFotoFiesta) ImageView imgFotoFiesta;
    @BindView(R.id.texTitulo) TextView texTitulo;
    @BindView(R.id.texDescripcion) TextView texDescripcion;
    @BindView(R.id.texCantidadInvitados) TextView texCantidadInvitados;
    @BindView(R.id.texTipo) TextView texTipo;
    @BindView(R.id.texCantidadDias) TextView texCantidadDias;
    @BindView(R.id.texFecha) TextView texFecha;
    @BindView(R.id.texHora) TextView texHora;
    @BindView(R.id.texNombreUbicacion) TextView texNombreUbicacion;
    //endregion

    static SupportMapFragment frgMapUbicacionFiesta;
    private static final int REQUEST_FOTO_CAMARA = 111;
    private static final int REQUEST_FOTO_GALERIA = 222;
    Uri imageUri;

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
        ButterKnife.bind(this);
        frgMapUbicacionFiesta = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapUbicacionFiesta);
        frgMapUbicacionFiesta.getMapAsync(this);
    }

    @Override
    public void eventos() {
        iniciarFoto();
        iniciarTextos();
        iniciarFuncionalidadesFiesta();
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
        texTipo.setText(presenter.getTipoFiesta());
        texCantidadInvitados.setText(presenter.getCantidadInvitados());
        texFecha.setText(presenter.getFecha());
        texHora.setText(presenter.getHora());
        texNombreUbicacion.setText(presenter.getNombreUbicacion());
    }

    private void iniciarFuncionalidadesFiesta(){
        int totalFuncionalidades = presenter.getTotalFuncionalidades();
        for(int indice = 0; indice < totalFuncionalidades; indice++){
            linOpcionesFiesta.addView(presenter.getLayoutFuncionalidad(indice));
        }
    }

    private void irAGoogleMaps(){
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", presenter.getLatitud(), presenter.getLongitud());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
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
    public void mostrarDialogoSubirFoto(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Subir foto");
        alertDialogBuilder
                .setMessage("¿De dónde deseas tomar la foto?")
                .setCancelable(false)
                .setPositiveButton("Cámara",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "Fiestapp");
                        imageUri = getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, REQUEST_FOTO_CAMARA);

                        dialog.cancel();
                    }
                })
                .setNegativeButton("Galería",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent intentNuevaFoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        if (intentNuevaFoto.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intentNuevaFoto, REQUEST_FOTO_GALERIA);
                        }
                        dialog.cancel();
                    }
                });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    alertDialog.dismiss();
                }
                return true;
            }
        });
        alertDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FOTO_CAMARA && resultCode == RESULT_OK) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                byte[] byteArray = stream.toByteArray();
                presenter.subirFotos(byteArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_FOTO_GALERIA && resultCode == RESULT_OK && null != data) {
            try{
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                byte[] byteArray = stream.toByteArray();
                presenter.subirFotos(byteArray);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mostrarProgreso(){}

    @Override
    public void ocultarProgreso(){}
}
