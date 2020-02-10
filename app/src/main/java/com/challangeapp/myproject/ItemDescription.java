package com.challangeapp.myproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.challangeapp.R;
import com.challangeapp.connections.ClientApi;
import com.challangeapp.connections.JsonApi;
import com.challangeapp.connections.model.Items;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDescription extends Fragment {
    Activity act;
    View rootView;
    Items postitem;
    TextView tvId,tvName,tvFullName,tvOwner,tvOwnerId,tvOwnerLogin,tvOwnerAvatarUrl;

    @SuppressLint("ValidFragment")
    public ItemDescription(Activity act) {
        this.act = act;
    }

    public ItemDescription() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.itemdescription, container, false);
        tvId                = (TextView)rootView.findViewById(R.id.tvId);
        tvName              = (TextView)rootView.findViewById(R.id.tvName);
        tvFullName          = (TextView)rootView.findViewById(R.id.tvFullName);
        tvOwner             = (TextView)rootView.findViewById(R.id.tvOwner);
        tvOwnerId           = (TextView)rootView.findViewById(R.id.tvOwnerId);
        tvOwnerLogin        = (TextView)rootView.findViewById(R.id.tvOwnerLogin);
        tvOwnerAvatarUrl    = (TextView)rootView.findViewById(R.id.tvOwnerAvatarUrl);

        initializePage();

        return rootView;
    }

    public void initializePage(){

        ((MainActivity)act).toggleButton.setVisibility(View.INVISIBLE);

        if (getArguments() != null) {
            try {
                Bundle mBundle = getArguments();
                postitem = mBundle.getParcelable("postitem");

                if(postitem!=null){

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tvId.setText(String.valueOf(postitem.getId()));
        tvName.setText(postitem.getName());
        tvFullName.setText(postitem.getFullName());
        tvOwnerId.setText(String.valueOf(postitem.getOwner().getId()));
        tvOwnerLogin.setText(postitem.getOwner().getLogin());
        tvOwnerAvatarUrl.setText(postitem.getOwner().getAvatarUrl());
    }

}