package com.challangeapp.connections;

import com.challangeapp.connections.model.Post;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonApi {

//    @GET("posts")
//    Call<List<Post>> getPosts();

    @GET("search/repositories?q=created:>2019-11-01&sort=stars&order=desc")
    Single<Post> getPosts(@Query("page") String page);
//    Observable<Post> getPosts(@Path("page") String page);
}