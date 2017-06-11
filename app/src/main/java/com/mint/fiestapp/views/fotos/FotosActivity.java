package com.mint.fiestapp.views.fotos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.presenters.IPresenter;
import com.mint.fiestapp.presenters.fotos.FotosAdapter;
import com.mint.fiestapp.presenters.fotos.IFotosPresenter;
import com.mint.fiestapp.views.BaseActivity;

import java.io.ByteArrayOutputStream;

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
    }

    @Override
    public void eventos() {
        iniciarListaFotos();
    }

    private void iniciarListaFotos(){
        layoutManager = new GridLayoutManager(getApplicationContext(),1);
        lisFotos.setLayoutManager(layoutManager);
    }

    @OnClick(R.id.fabCamara)
    public void nuevaFotoCamara(){
        Intent intentNuevaFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentNuevaFoto.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentNuevaFoto, REQUEST_FOTO_CAMARA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FOTO_CAMARA && resultCode == RESULT_OK) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 70, stream);
            byte[] byteArray = stream.toByteArray();
            presenter.subirFotos(byteArray);
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