package com.challangeapp.myproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.challangeapp.R;
import com.challangeapp.connections.ClientApi;
import com.challangeapp.connections.JsonApi;
import com.challangeapp.connections.model.Items;
import com.challangeapp.connections.model.Post;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment implements TwoButtonsDialog.TwoButtonsDialogAlertInterface {
    private Activity act;
    private View rootView;
    private RecyclerView recyclerView;
    private Clientadapter adap;
    private JsonApi jsonApi;
    public ArrayList<Items> arrayNormalPost;
    public ArrayList<Items> arrayAscendingPost;
    public boolean isAscending=false;
    public int currentPage=0;
    @SuppressLint("ValidFragment")
    public MainFragment(Activity act){
        this.act=act;
    }

    public MainFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.mainfragment, container,false);

        recyclerView   = (RecyclerView)rootView.findViewById(R.id.recyclerView);

        initializePage();

        return rootView;
    }

    public void initializePage(){
        jsonApi = ClientApi.getClient(act).create(JsonApi.class);
        if(arrayNormalPost==null || arrayNormalPost.size()==0) {
            arrayNormalPost = new ArrayList<>();
            currentPage=1;
            isAscending=false;
            getAllItems(currentPage);
        }else{
            drawRecyle(isAscending,true);
        }
    }

    @SuppressLint("CheckResult")
    public void getAllItems(int currpage){
        Config.showdialog(act);
        jsonApi.getPosts(String.valueOf(currpage))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Post>() {
                    @Override
                    public void onSuccess(Post post) {
                        arrayNormalPost.addAll(post.getItems());
                        if (Config.pDialog != null && Config.pDialog.isShowing()) {
                            Config.pDialog.dismiss();
                        }
                       drawRecyle(isAscending,false);
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (Config.pDialog != null && Config.pDialog.isShowing()) {
                            Config.pDialog.dismiss();
                        }
                        TwoButtonsDialog twobuttonsdialog = new TwoButtonsDialog(act, MainFragment.this);
                        twobuttonsdialog.showCustomAlertDialog(1, act.getResources().getString(R.string.app_name),
                                act.getResources().getString(R.string.ErrorConnection),
                                act.getResources().getString(R.string.btnRetry),
                                act.getResources().getString(R.string.btnCancel),
                                2,false, 0);
                    }
                });
    }

    public void drawRecyle(boolean isAscending,boolean shouldResetList) {
        this.isAscending = isAscending;
        if (isAscending) {
            sortArrayAscending();
            if (shouldResetList) {
                adap = new Clientadapter(act, MainFragment.this, arrayAscendingPost, recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(act, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adap);
            } else {
                adap.updateList(arrayAscendingPost);
            }
        } else {
            if (adap == null || shouldResetList) {
                adap = new Clientadapter(act, MainFragment.this, arrayNormalPost, recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(act, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adap);
            } else {
                adap.updateList(arrayNormalPost);
            }
        }
    }

    public void sortArrayAscending(){
        arrayAscendingPost = new ArrayList<>();
        Items[] ItemsArray= new Items[arrayNormalPost.size()];
        for(int i=0; i < arrayNormalPost.size(); i++) {
            ItemsArray[i] = arrayNormalPost.get(i);
        }
        for(int i=0; i < ItemsArray.length-1; i++){
            for(int j=1; j < ItemsArray.length-i; j++){
                if(ItemsArray[j-1].getOwner().getId() > ItemsArray[j].getOwner().getId()){
                    Items item=ItemsArray[j-1];
                    ItemsArray[j-1] = ItemsArray[j];
                    ItemsArray[j] = item;
                }
            }
        }
        Collections.addAll(arrayAscendingPost, ItemsArray);
    }

    @Override
    public void showTwoButtonsDialog(int buttonselected, int flagid) {
        switch (buttonselected){
            case 0:
                //retry
                currentPage--;
                getAllItems(currentPage);
                break;

            case 1:
                //cancel
                break;
        }
    }
}