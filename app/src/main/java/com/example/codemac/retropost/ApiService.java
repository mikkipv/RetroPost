package com.example.codemac.retropost;

/**
 * Created by codemac on 16/6/17.
 */
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/nizam/addmall")
    Call<List<model>>validate(@Body login login);
@Multipart
    @POST("api/nizam/addmall")
    Call<ResponseBody>AddMall1(
            @Part("name") RequestBody name,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("address") RequestBody address,
            @Part("location") RequestBody location,
            @Part MultipartBody.Part mall_image);

    @GET("listitems/{category}")
    Call<List<model>> getProducts(@Path("category") String category);

    @DELETE("deleteitem/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") String id);


}
