package com.mint.fiestapp.views.fotos;

import android.os.Bundle;
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

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class FotosActivity extends BaseActivity implements IFotosActivity {

    IFotosPresenter presenter;
    private RecyclerView lisFotos;
    private LinearLayoutManager layoutManager;
    private FloatingActionButton fabNuevaFoto;
    private FloatingActionButton fabCamara;
    private FloatingActionButton fabGaleria;
    private Animation animFabOpen;
    private Animation animFabClose;
    private Animation animRotateForward;
    private Animation animRotateBackward;
    private boolean estaAbiertoFabNuevaFoto = false;
    private boolean estaCargando = false;
    private boolean esUltimaPagina = false;
    private String keyUltimaFoto = "";

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
        lisFotos = (RecyclerView)findViewById(R.id.lisFotos);
        fabNuevaFoto = (FloatingActionButton) findViewById(R.id.fabNuevaFoto);
        fabCamara = (FloatingActionButton)findViewById(R.id.fabCamara);
        fabGaleria = (FloatingActionButton)findViewById(R.id.fabGaleria);
        animFabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        animFabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        animRotateForward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        animRotateBackward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
    }

    @Override
    public void eventos() {
        iniciarListaFotos();
        eventosNuevaFoto();
    }

    private void iniciarListaFotos(){
        layoutManager = new GridLayoutManager(getApplicationContext(),1);
        lisFotos.setLayoutManager(layoutManager);
    }

    private void eventosNuevaFoto(){
        fabNuevaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animacionNuevaFoto();
            }
        });
    }

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
