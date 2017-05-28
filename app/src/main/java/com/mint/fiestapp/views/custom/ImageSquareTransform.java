package com.mint.fiestapp.views.custom;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class ImageSquareTransform implements Transformation {

    int size = 0;
    public ImageSquareTransform(int size){
        this.size = size;
    }
    @Override public Bitmap transform(Bitmap source) {
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
        if (result != source) {
            source.recycle();
        }
        return result;
    }

    @Override public String key() { return "square()"; }
}