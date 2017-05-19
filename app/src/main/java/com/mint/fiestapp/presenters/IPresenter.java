package com.mint.fiestapp.presenters;

import android.content.Context;

import com.mint.fiestapp.views.IActivity;

import java.io.Serializable;

public interface IPresenter extends Serializable {
    void iniciarActivity(Context context);
    void setContext(Context context);
    void setActivity(IActivity activity);
}
