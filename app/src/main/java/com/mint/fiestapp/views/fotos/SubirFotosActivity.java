package com.mint.fiestapp.views.fotos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.presenters.IPresenter;
import com.mint.fiestapp.presenters.fotos.IFotosPresenter;
import com.mint.fiestapp.presenters.fotos.ISubirFotosPresenter;
import com.mint.fiestapp.views.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SubirFotosActivity extends BaseActivity implements ISubirFotosActivity {
    ISubirFotosPresenter presenter;

    //region Controles
    @BindView(R.id.fabSubirFotos) FloatingActionButton fabSubirFotos;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_fotos);
        if(getIntent().getExtras() != null){
            iniciarPresenter((ISubirFotosPresenter)getIntent().getExtras().getSerializable(IntentKeys.PRESENTER));
        }
        binding();
        eventos();
    }

    @Override
    public void binding() {
        ButterKnife.bind(this);
    }

    @Override
    public void eventos() {

    }

    @Override
    public void iniciarPresenter(IPresenter presenter){
        this.presenter = (ISubirFotosPresenter) presenter;
        iniciarActivityPresenter();
    }

    @Override
    public void iniciarActivityPresenter(){
        presenter.setContext(this);
        presenter.setActivity(this);
    }


    @Override
    public void mostrarProgreso() {

    }

    @Override
    public void ocultarProgreso() {

    }
}
