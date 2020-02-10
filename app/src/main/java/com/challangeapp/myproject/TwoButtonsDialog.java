package com.challangeapp.myproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.challangeapp.R;


@SuppressLint("ValidFragment")
public class TwoButtonsDialog {

    private Activity act;
    private TextView tvTitle,tvMessage;
    private ImageView imgPic;
    private Button btnOk,btnCancel;
    private TwoButtonsDialogAlertInterface twobuttoninterface;

    public interface TwoButtonsDialogAlertInterface{
        public void showTwoButtonsDialog(int buttonselected, int flagid);
    }

    public TwoButtonsDialog(Activity act, TwoButtonsDialogAlertInterface twobuttoninterface){
        this.act=act;
        this.twobuttoninterface=twobuttoninterface;
    }


    public void showCustomAlertDialog(int flagid, String title, String message, String strSuccess, String strCancel, float numberofbuttons, boolean shouldShowImage, int imgresource){
        AlertDialog alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(act, R.style.AlertDialogCustom)).create();
        LayoutInflater inflater = act.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.twobuttondialog, null);
        alertDialog.setView(dialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvTitle     = (TextView)dialogView.findViewById(R.id.tvTitle);
        tvMessage   = (TextView)dialogView.findViewById(R.id.tvMessage);
        btnOk       = (Button)dialogView.findViewById(R.id.btnOk);
        btnCancel   = (Button)dialogView.findViewById(R.id.btnCancel);
        imgPic      = (ImageView) dialogView.findViewById(R.id.imgPic);

        if(shouldShowImage){
            imgPic.setVisibility(View.VISIBLE);
            imgPic.setImageDrawable(act.getDrawable(imgresource));
        }else{
            imgPic.setVisibility(View.GONE);
        }

        if(numberofbuttons==1){
            btnOk.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.GONE);
        }else if(numberofbuttons==2){
            btnOk.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        }

        tvTitle.setText(title);
        tvMessage.setText(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog,
                                 int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        btnOk.setText(strSuccess);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twobuttoninterface.showTwoButtonsDialog(0,flagid);
                alertDialog.dismiss();
            }
        });
        btnCancel.setText(strCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twobuttoninterface.showTwoButtonsDialog(1,flagid);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}