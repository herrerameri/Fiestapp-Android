package com.mint.fiestapp.views.fiesta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mint.fiestapp.R;
import com.mint.fiestapp.comun.IntentKeys;
import com.mint.fiestapp.presenters.IPresenter;
import com.mint.fiestapp.presenters.fiesta.IFiestaPresenter;
import com.mint.fiestapp.views.custom.ImageCircleTransform;
import com.squareup.picasso.Picasso;


public class FiestaActivity extends AppCompatActivity implements IFiestaActivity {

    IFiestaPresenter presenter;
    GridLayout griOpcionesFiesta;
    ImageView imgFotoFiesta;
    TextView texTitulo;
    TextView texDescripcion;
    TextView texCantidadInvitados;
    TextView texCantidadFotos;
    TextView texCantidadDias;

    int COLUMNAS = 3;

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
        griOpcionesFiesta = (GridLayout)findViewById(R.id.griOpcionesFiesta);
        texTitulo = (TextView)findViewById(R.id.texTitulo);
        texDescripcion = (TextView)findViewById(R.id.texDescripcion);
        texCantidadDias = (TextView)findViewById(R.id.texCantidadDias);
        texCantidadFotos = (TextView)findViewById(R.id.texCantidadFotos);
        texCantidadInvitados = (TextView)findViewById(R.id.texCantidadInvitados);
        imgFotoFiesta = (ImageView)findViewById(R.id.imgFotoFiesta);
    }

    @Override
    public void eventos() {
        iniciarFoto();
        iniciarTextos();
        iniciarGrid();
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
    }

    private void iniciarGrid(){
        griOpcionesFiesta.removeAllViews();

        int total = presenter.totalFuncionalidades();
        int column = COLUMNAS;
        int row = total / column;
        griOpcionesFiesta.setColumnCount(column);
        griOpcionesFiesta.setRowCount(row + 1);
        for (int i = 0, c = 0, r = 0; i < total; i++, c++) {
            if (c == column) {
                c = 0;
                r++;
            }
            ImageView oImageView = new ImageView(this);
            int icono = presenter.idIconoFuncionalidad(i);
            oImageView.setImageResource(icono);
            oImageView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));

            GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
            GridLayout.Spec colspan = GridLayout.spec(GridLayout.UNDEFINED, 1);
            if (r == 0 && c == 0) {
                colspan = GridLayout.spec(GridLayout.UNDEFINED, 2);
                rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 2);
            }
            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colspan);
            griOpcionesFiesta.addView(oImageView, gridParam);
        }
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
}
