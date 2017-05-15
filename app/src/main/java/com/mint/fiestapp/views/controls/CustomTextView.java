package com.mint.fiestapp.views.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import java.util.Locale;

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    private static final String sScheme = "http://schemas.android.com/apk/res-auto";
    private static final String sAttribute = "customFont";

    static enum CustomFont {
        UBUNTU_NORMAL("fonts/Ubuntu-M.ttf"),
        UBUNTU_REGULAR("fonts/Ubuntu-R.ttf");

        private final String fileName;

        CustomFont(String fileName) {
            this.fileName = fileName;
        }

        static CustomFont fromString(String fontName) {
            return CustomFont.valueOf(fontName.toUpperCase(Locale.US));
        }

        public Typeface asTypeface(Context context) {
            return Typeface.createFromAsset(context.getAssets(), fileName);
        }
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        } else {
            final String fontName = attrs.getAttributeValue(sScheme, sAttribute);

            if (fontName == null) {
                throw new IllegalArgumentException("You must provide \"" + sAttribute + "\" for your text view");
            } else {
                final Typeface customTypeface = CustomFont.fromString(fontName).asTypeface(context);
                setTypeface(customTypeface);
            }
        }
    }
}
