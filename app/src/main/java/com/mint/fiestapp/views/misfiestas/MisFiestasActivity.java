package com.mint.fiestapp.views.misfiestas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.presenters.IPresenter;
import com.mint.fiestapp.presenters.misfiestas.IMisFiestasPresenter;
import com.mint.fiestapp.presenters.misfiestas.MisFiestasPresenter;
import com.mint.fiestapp.views.BaseActivity;

import static android.os.Build.*;


public class MisFiestasActivity extends BaseActivity implements IMisFiestasActivity{

    IMisFiestasPresenter presenter;
    private RecyclerView lisFiestas;
    private RecyclerView.LayoutManager layoutManager;
    private BottomNavigationView navMisFiestas;
    private BottomNavigationMenuView navMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_misfiestas);
        if(getIntent().getExtras() != null){
            iniciarPresenter((IMisFiestasPresenter)getIntent().getExtras().getSerializable(IntentKeys.PRESENTER));
        }
        binding();
        eventos();
        presenter.obtenerFiestas();
    }

    @Override
    public void iniciarPresenter(IPresenter presenter){
        this.presenter = (IMisFiestasPresenter)presenter;
        iniciarActivityPresenter();
    }

    @Override
    public void iniciarActivityPresenter(){
        presenter.setContext(this);
        presenter.setActivity(this);
    }

    @Override
    public void binding(){
        lisFiestas = (RecyclerView) findViewById(R.id.lisFiestas);
    }

    @Override
    public void eventos(){
        iniciarListaFiestas();
        iniciarBottomNav();
    }

    private void iniciarListaFiestas(){
        layoutManager = new LinearLayoutManager(this);
        lisFiestas.setLayoutManager(layoutManager);
    }

    private void iniciarBottomNav(){
        navMisFiestas = (BottomNavigationView) findViewById(R.id.navMisFiestas);

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

        navMisFiestas.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_misfiestas:

                            break;
                        case R.id.action_nuevafiesta:

                            break;
                        case R.id.action_notificaciones:

                            break;
                    }
                    return false;
                }
            });
    }

    @Override
    public void mostrarFiestas(RecyclerView.Adapter adapter){
        lisFiestas.setAdapter(adapter);
    }

    @Override
    public void mostrarError(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarProgreso(){}

    @Override
    public void ocultarProgreso(){}

    public void logOut() {
        LoginManager.getInstance().logOut();
        // GO TO LOGIN
    }
}
