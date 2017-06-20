package com.sample.tumeda.retrosample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("services/api/BooksBook/Search/20170404?size=0&sort=sales")
//    引数にデータクラスを指定
    Call<BookItems> getItems(@Query("applicationId") String applicationId,
                             @Query("operation") String operation,
                             @Query("hits") String hits);
}