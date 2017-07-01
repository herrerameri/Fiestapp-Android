package com.mint.fiestapp.views.fotos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.models.entidades.FotoModel;
import com.mint.fiestapp.presenters.IPresenter;
import com.mint.fiestapp.presenters.fotos.IFotosPresenter;
import com.mint.fiestapp.presenters.fotos.ISubirFotosPresenter;
import com.mint.fiestapp.views.BaseActivity;
import com.mint.fiestapp.views.custom.CustomToast;
import com.mint.fiestapp.views.custom.ImageSquareTransform;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SubirFotosActivity extends BaseActivity implements ISubirFotosActivity {
    ISubirFotosPresenter presenter;

    //region Controles
    @BindView(R.id.fabSubirFotos) FloatingActionButton fabSubirFotos;
    @BindView(R.id.linNuevasFotos) LinearLayout linNuevasFotos;
    @BindView(R.id.ediDescripcion) EditText ediDescripcion;
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
        presenter.getPrimeraFoto();
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
    public void addFoto(FotoModel foto){
        ImageView imgFiesta = new ImageView(this);
        LinearLayout.LayoutParams layoutParamsImagen = obtenerParametrosImagen();
        imgFiesta.setLayoutParams(layoutParamsImagen);

        Bitmap bitmap = BitmapFactory.decodeByteArray(foto.Bytes, 0, foto.Bytes.length);
        imgFiesta.setImageBitmap(bitmap);
        imgFiesta.setScaleType(ImageView.ScaleType.CENTER_CROP);

        linNuevasFotos.addView(imgFiesta);
    }

    @OnClick(R.id.fabSubirFotos)
    public void subirFotos(){
        presenter.subirFotos(ediDescripcion.getText().toString());
    }

    private LinearLayout.LayoutParams obtenerParametrosImagen(){
        int margin = getResources().getDimensionPixelSize(R.dimen.margin_foto_fiesta);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.width_subir_foto),
                getResources().getDimensionPixelSize(R.dimen.height_subir_foto));

        layoutParams.setMargins(margin,margin,margin,margin);
        return layoutParams;
    }

    @Override
    public void finishActivity(){
        finish();
    }

    @Override
    public void mostrarProgreso() {
        CustomToast.showToast(this, Toast.LENGTH_SHORT, getResources().getString(R.string.progreso_foto));
    }

    @Override
    public void ocultarProgreso() {

    }
}
