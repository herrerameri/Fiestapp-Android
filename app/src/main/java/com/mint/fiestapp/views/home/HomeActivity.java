package com.mint.fiestapp.views.home;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.presenters.IPresenter;
import com.mint.fiestapp.presenters.home.IHomePresenter;
import com.mint.fiestapp.views.BaseActivity;
import com.mint.fiestapp.views.IActivity;
import com.mint.fiestapp.views.custom.CustomToast;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeActivity extends BaseActivity implements IHomeActivity {

    IHomePresenter presenter;

    //region Controles
    @BindView(R.id.navMisFiestas) BottomNavigationView navMisFiestas;
    @BindView(R.id.texTitulo) TextView texTitulo;
    @BindView(R.id.prgCargando) ProgressBar prgCargando;
    BottomNavigationMenuView navMenu;
    //endregion

    private MisFiestasFragment frgMisFiestas;
    private AgregarFiestaFragment fraAgregarFiesta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(getIntent().getExtras() != null){
            iniciarPresenter((IHomePresenter)getIntent().getExtras().getSerializable(IntentKeys.PRESENTER));
        }
        binding();
        eventos();
    }

    @Override
    public void iniciarPresenter(IPresenter presenter){
        this.presenter = (IHomePresenter)presenter;
        iniciarActivityPresenter();
    }

    @Override
    public void iniciarActivityPresenter(){
        presenter.setContext(this);
        presenter.setActivity(this);
    }

    @Override
    public void binding(){
        ButterKnife.bind(this);
        texTitulo.setText(getResources().getString(R.string.mis_fiestas));
        frgMisFiestas = new MisFiestasFragment();
        fraAgregarFiesta = new AgregarFiestaFragment();

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frgLayout, frgMisFiestas).commit();
        presenter.obtenerFiestas();
    }

    @Override
    public void eventos(){
        iniciarBottomNav();
    }

    private void iniciarBottomNav(){
        // BottomNav tiene hardcode 24dp para el alto de los íconos
        navMenu = (BottomNavigationMenuView) navMisFiestas.getChildAt(0);
        for (int i = 0; i < navMenu.getChildCount(); i++) {
            final View iconView = navMenu.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 43, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 43, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        final FragmentManager manager = getSupportFragmentManager();
        navMisFiestas.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_misfiestas:
                            texTitulo.setText(getResources().getString(R.string.mis_fiestas));
                            manager.beginTransaction().replace(R.id.frgLayout, frgMisFiestas).commit();
                            break;
                        case R.id.action_nuevafiesta:
                            texTitulo.setText(getResources().getString(R.string.nueva_fiesta));
                            manager.beginTransaction().replace(R.id.frgLayout, fraAgregarFiesta).commit();
                            break;
                        case R.id.action_cerrar_sesion:
                            showDialogoCerrarSesion();
                            break;
                    }
                    return false;
                }
            });
    }

    private void showDialogoCerrarSesion(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.cerrar_sesion));
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.seguro_cerrar_sesion))
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        FirebaseAuth.getInstance().signOut();
                        LoginManager.getInstance().logOut();
                        presenter.volverALogin();
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void mostrarFiestas(RecyclerView.Adapter adapter){
        frgMisFiestas.setAdapter(adapter);
    }

    @Override
    public void agregarFiesta(String codigo){
        presenter.agregarFiesta(codigo);
    }

    @Override
    public void mostrarMensaje(String mensaje){
        CustomToast.showToast(this, Toast.LENGTH_SHORT, mensaje);
    }

    @Override
    public void mostrarProgreso(){
        prgCargando.setVisibility(View.VISIBLE);
    }

    @Override
    public void ocultarProgreso(){
        prgCargando.setVisibility(View.GONE);
    }

}
