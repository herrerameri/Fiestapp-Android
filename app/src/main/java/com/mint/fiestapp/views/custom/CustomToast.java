package com.mint.fiestapp.views.custom;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.mint.fiestapp.R;

public class CustomToast {

    public static void showToast(Context context, int duracion, String texto){
        Toast toast = Toast.makeText(context, texto, duracion);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_back);

        toast.show();
    }
}
