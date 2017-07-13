package com.mint.fiestapp.views.fotos;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.Imagenes;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.presenters.IPresenter;
import com.mint.fiestapp.presenters.fotos.FotosAdapter;
import com.mint.fiestapp.presenters.fotos.IFotosPresenter;
import com.mint.fiestapp.views.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class FotosActivity extends BaseActivity implements IFotosActivity {

    IFotosPresenter presenter;

    //region Controles
    @BindView(R.id.lisFotos) RecyclerView lisFotos;
    @BindView(R.id.fabNuevaFoto) FloatingActionButton fabNuevaFoto;
    @BindView(R.id.fabCamara) FloatingActionButton fabCamara;
    @BindView(R.id.fabGaleria) FloatingActionButton fabGaleria;
    @BindView(R.id.swpRefresh) SwipeRefreshLayout swpRefresh;
    // endregion

    private Animation animFabOpen;
    private Animation animFabClose;
    private Animation animRotateForward;
    private Animation animRotateBackward;
    private LinearLayoutManager layoutManager;

    private boolean estaAbiertoFabNuevaFoto = false;
    private boolean estaCargando = false;
    private boolean esUltimaPagina = false;
    private String keyUltimaFoto = "";
    private static final int REQUEST_FOTO_CAMARA = 111;
    private static final int REQUEST_FOTO_GALERIA = 222;
    private static final int PERMISO_GALERIA = 1000;
    private static final int PERMISO_CAMARA = 2000;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        if(getIntent().getExtras() != null){
            iniciarPresenter((IFotosPresenter)getIntent().getExtras().getSerializable(IntentKeys.PRESENTER));
        }
        binding();
        eventos();
        presenter.obtenerFotos();
    }

    @Override
    public void binding(){
        ButterKnife.bind(this);
        animFabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        animFabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        animRotateForward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        animRotateBackward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        swpRefresh.setColorSchemeResources(
                R.color.fucsia,
                R.color.colorPrimaryDark,
                R.color.colorPrimary,
                R.color.colorAccent);

    }

    @Override
    public void eventos() {
        iniciarListaFotos();
        pullRefresh();
    }

    private void pullRefresh(){
        swpRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.removeFotos();
                presenter.obtenerFotos();
                swpRefresh.setRefreshing(false);
            }
        });
    }

    private void iniciarListaFotos(){
        layoutManager = new GridLayoutManager(getApplicationContext(),1);
        lisFotos.setLayoutManager(layoutManager);
    }

    @OnClick(R.id.fabCamara)
    public void nuevaFotoCamara(){
        if (ContextCompat.checkSelfPermission(FotosActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(FotosActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(FotosActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISO_CAMARA);
            }
        }
        else{
            intentCamaraFotos();
        }
    }

    @OnClick(R.id.fabGaleria)
    public void nuevaFotoGaleria(){
        if (ContextCompat.checkSelfPermission(FotosActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(FotosActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(FotosActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISO_GALERIA);
            }
        }
        else{
            intentGaleriaFotos();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISO_GALERIA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    intentGaleriaFotos();
                }
                return;
            }
            case PERMISO_CAMARA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    intentCamaraFotos();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void intentGaleriaFotos(){
        Intent intentNuevaFoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intentNuevaFoto.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentNuevaFoto, REQUEST_FOTO_GALERIA);
        }
    }

    private void intentCamaraFotos(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Fiestapp");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_FOTO_CAMARA);
    }
    public String getRealPathFromURI(Uri uri){
        String filePath = "";
        String[] filePahColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, filePahColumn, null, null, null);
        if (cursor != null) {
            if(cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndex(filePahColumn[0]);
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FOTO_CAMARA && resultCode == RESULT_OK ) {
            try {
                Bitmap imagenBitmap = BitmapFactory.decodeFile(getRealPathFromURI(imageUri));
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                imagenBitmap.compress(Bitmap.CompressFormat.JPEG, 10, out);
                byte[] byteArray = out.toByteArray();
                presenter.subirFotos(byteArray);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_FOTO_GALERIA && resultCode == RESULT_OK && null != data) {
            try{
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap imagenBitmap = BitmapFactory.decodeFile(getRealPathFromURI(data.getData()));

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                imagenBitmap.compress(Bitmap.CompressFormat.JPEG, 15, out);

                byte[] byteArray = out.toByteArray();
                presenter.subirFotos(byteArray);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.fabNuevaFoto)
    public void animacionNuevaFoto(){
        if(estaAbiertoFabNuevaFoto){
            cerrarFabNuevaFoto();
        } else {
            abrirFabNuevaFoto();
        }
    }

    private void cerrarFabNuevaFoto(){
        fabNuevaFoto.startAnimation(animRotateBackward);
        fabCamara.startAnimation(animFabClose);
        fabGaleria.startAnimation(animFabClose);
        fabCamara.setClickable(false);
        fabGaleria.setClickable(false);
        estaAbiertoFabNuevaFoto = false;
    }

    private void abrirFabNuevaFoto(){
        fabNuevaFoto.startAnimation(animRotateForward);
        fabCamara.startAnimation(animFabOpen);
        fabGaleria.startAnimation(animFabOpen);
        fabCamara.setClickable(true);
        fabGaleria.setClickable(true);
        estaAbiertoFabNuevaFoto = true;
    }

    @Override
    public void iniciarPresenter(IPresenter presenter){
        this.presenter = (IFotosPresenter) presenter;
        iniciarActivityPresenter();
    }

    @Override
    public void iniciarActivityPresenter(){
        presenter.setContext(this);
        presenter.setActivity(this);
    }

    @Override
    public void mostrarFotos(FotosAdapter adapter){
        lisFotos.setAdapter(adapter);
        lisFotos.setItemAnimator(new SlideInUpAnimator());
        lisFotos.addOnScrollListener(recyclerViewOnScrollListener);
    }

    @Override
    public void mostrarProgreso(){
        estaCargando = true;
    }

    @Override
    public void ocultarProgreso(){
        estaCargando = false;
    }

    @Override
    public void setKeyUltimaFoto(String key){
        keyUltimaFoto = key;
    }

    public String getKeyUltimaFoto(){
        return keyUltimaFoto;
    }

    @Override
    public void setUltimaPagina(boolean esUltimaPagina){
        this.esUltimaPagina = esUltimaPagina;
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(estaAbiertoFabNuevaFoto){
                cerrarFabNuevaFoto();
            }
        }
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!estaCargando && !esUltimaPagina) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    presenter.obtenerMasFotos();
                }
            }
        }
    };
}
