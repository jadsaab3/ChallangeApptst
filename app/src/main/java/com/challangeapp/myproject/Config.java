package com.challangeapp.myproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.challangeapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Config {

    public static String Main_URL = "https://api.github.com/";
    public static AlertDialog pDialog;



    public static void goToFragment(Activity act, Fragment fr, Bundle bundle, boolean popLastFragment,
                                    boolean popAllFragments, boolean goHome, Fragment homeFragment,
                                    boolean moveForward, boolean addtransaction) {
        FragmentManager fragmentManager = ((FragmentActivity) act).getSupportFragmentManager();
        if (moveForward) {
            if (bundle != null)
                fr.setArguments(bundle);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (popLastFragment) {
                int numberFragment = fragmentManager.getBackStackEntryCount();
                if (numberFragment > 1) {
                    fragmentManager.popBackStackImmediate();
                }
            }
            if(popAllFragments){
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            if(goHome) {
                int sd = fragmentManager.getBackStackEntryCount();
                int j = 0;
                for (int i = 0; i < sd; i++) {
                    FragmentManager.BackStackEntry backEntry = ((FragmentActivity) act).getSupportFragmentManager().getBackStackEntryAt(((FragmentActivity) act).getSupportFragmentManager().getBackStackEntryCount() - (i + 1));
                    String str = backEntry.getName();
                    if (str.equalsIgnoreCase(homeFragment.getClass().getName())) {
                        j = i;
                        i = sd;
                        FragmentManager fragmentManager2 = ((FragmentActivity) act).getSupportFragmentManager();
                        fragmentManager2.popBackStack(fragmentManager2.getBackStackEntryAt(fragmentManager2.getBackStackEntryCount() - j).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
            }
            fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout,R.anim.fadein, R.anim.fadeout);
            if(addtransaction){
                fragmentTransaction.add(R.id.content_frame, fr, fr.getClass().getName());
            }else {
                fragmentTransaction.replace(R.id.content_frame, fr, fr.getClass().getName());
            }
            fragmentTransaction.addToBackStack(fr.getClass().getName());
            fragmentTransaction.commit();
        } else {
            int sd = fragmentManager.getBackStackEntryCount();
            int j = 0;
            for (int i = 0; i < sd; i++) {
                FragmentManager.BackStackEntry backEntry = ((FragmentActivity) act).getSupportFragmentManager().getBackStackEntryAt(((FragmentActivity) act).getSupportFragmentManager().getBackStackEntryCount() - (i + 1));
                String str = backEntry.getName();

                if (str.equalsIgnoreCase(fr.getClass().getName())) {
                    j = i;
                    i = sd;
                    FragmentManager fragmentManager2 = ((FragmentActivity) act).getSupportFragmentManager();
                    fragmentManager2.popBackStack(fragmentManager2.getBackStackEntryAt(fragmentManager2.getBackStackEntryCount() - j).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        }
    }

    public static void showdialog(Activity act) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        pDialog = new AlertDialog.Builder(act).create();
        LayoutInflater inflater = act.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.customloading, null);
        pDialog.setView(dialogView);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        pDialog.show();
    }

}