package com.nerbly.bemoji.UI;

import static com.nerbly.bemoji.UI.MainUIMethods.setViewRadius;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nerbly.bemoji.R;

public class UserInteractions {

    public static void showCustomSnackBar(String message, Activity context) {
        com.google.android.material.snackbar.Snackbar snackBarView;
        com.google.android.material.snackbar.Snackbar.SnackbarLayout sblayout;
        ViewGroup parentLayout = (ViewGroup) ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);

        snackBarView = com.google.android.material.snackbar.Snackbar.make(parentLayout, "", com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
        sblayout = (com.google.android.material.snackbar.Snackbar.SnackbarLayout) snackBarView.getView();

        View inflate = context.getLayoutInflater().inflate(R.layout.snackbar, parentLayout, false);
        sblayout.setPadding(0, 0, 0, 0);
        sblayout.setBackgroundColor(Color.argb(0, 0, 0, 0));
        LinearLayout back = inflate.findViewById(R.id.tutorialBg);

        TextView snackbar_tv = inflate.findViewById(R.id.tutorialTitle);
        setViewRadius(back, 20, "#202125");
        snackbar_tv.setText(message);
        snackbar_tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/whitney.ttf"), Typeface.NORMAL);
        sblayout.addView(inflate, 0);
        snackBarView.show();
    }
}
