package com.example.myshelf;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookService {
    @GET("search.json")
    Call<BookResponse> searchBooks(@Query("q") String query);
}