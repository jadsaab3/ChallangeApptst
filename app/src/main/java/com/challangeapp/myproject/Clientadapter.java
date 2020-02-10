package com.challangeapp.myproject;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.challangeapp.R;
import com.challangeapp.connections.model.Items;
import com.challangeapp.connections.model.Post;

import com.challangeapp.connections.model.Owner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


public class Clientadapter extends RecyclerView.Adapter<Clientadapter.BannerViewHolder> {
    private Activity act;
    private MainFragment mainFragment;
    RecyclerView recyclerView;
    private View view;
    ArrayList<Items> arrayPost = new ArrayList<>();
    int currpos = 0;

    public Clientadapter(Activity act,MainFragment mainFragment, ArrayList<Items> arrayPost, RecyclerView recyclerView) {
        this.act = act;
        this.mainFragment=mainFragment;
        this.recyclerView=recyclerView;
        this.arrayPost=arrayPost;
    }

    @Override
    public int getItemCount() {
        return arrayPost.size();
    }

    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.client_item, viewGroup, false);
        BannerViewHolder viewHolder = new BannerViewHolder(view);

        return viewHolder;
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout relbackg;
        TextView tvId,tvName,tvFullName,tvOwner,tvOwnerId,tvOwnerLogin,tvOwnerAvatarUrl;

        private BannerViewHolder(View itemView) {
            super(itemView);

            relbackg        = (ConstraintLayout)itemView.findViewById(R.id.relbackg);
            tvId            = (TextView)itemView.findViewById(R.id.tvId);
            tvName          = (TextView)itemView.findViewById(R.id.tvName);
            tvFullName      = (TextView)itemView.findViewById(R.id.tvFullName);
            tvOwner         = (TextView)itemView.findViewById(R.id.tvOwner);
            tvOwnerId       = (TextView)itemView.findViewById(R.id.tvOwnerId);
            tvOwnerLogin    = (TextView)itemView.findViewById(R.id.tvOwnerLogin);
            tvOwnerAvatarUrl= (TextView)itemView.findViewById(R.id.tvOwnerAvatarUrl);
        }
    }

    public void updateList(ArrayList<Items> newArrayPost){
        arrayPost = new ArrayList<>();
        arrayPost = newArrayPost;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder groupHolder, int position) {
        if (position > currpos) {
            currpos = position;
        }
        groupHolder.tvId.setText(String.valueOf(arrayPost.get(position).getId()));
        groupHolder.tvName.setText(arrayPost.get(position).getName());
        groupHolder.tvFullName.setText(arrayPost.get(position).getFullName());
        groupHolder.tvOwnerId.setText(String.valueOf(arrayPost.get(position).getOwner().getId()));
        groupHolder.tvOwnerLogin.setText(arrayPost.get(position).getOwner().getLogin());
        groupHolder.tvOwnerAvatarUrl.setText(arrayPost.get(position).getOwner().getAvatarUrl());

        groupHolder.relbackg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment itemdescription = new ItemDescription(act);
                Items postitem = new Items();
                postitem =arrayPost.get(position);
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("postitem",postitem);
                Config.goToFragment(act,itemdescription,mBundle,false,false,
                        false,null,true,false);
            }
        });
        if (position == arrayPost.size()-3 && currpos <= position) {
            mainFragment.currentPage++;
            mainFragment.getAllItems(mainFragment.currentPage);
        }

    }
    
}