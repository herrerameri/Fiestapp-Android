package com.mint.fiestapp.views.fotos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.presenters.IPresenter;
import com.mint.fiestapp.presenters.fotos.FotosAdapter;
import com.mint.fiestapp.presenters.fotos.IFotosPresenter;
import com.mint.fiestapp.views.BaseActivity;


public class FotosActivity extends BaseActivity implements IFotosActivity {

    IFotosPresenter presenter;
    private RecyclerView lisFotos;
    private RecyclerView.LayoutManager layoutManager;

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
    }
    @Override
    public void eventos() {
        iniciarListaFotos();
    }

    private void iniciarListaFotos(){
        layoutManager = new GridLayoutManager(getApplicationContext(),1);
        lisFotos.setLayoutManager(layoutManager);
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
    }

    @Override
    public void mostrarProgreso(){}
}
