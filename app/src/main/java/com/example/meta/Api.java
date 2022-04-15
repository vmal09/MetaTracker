package com.example.meta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("upload_image.php")
    Call<ResponsePOJO> savePhoto(
            @Field("EN_IMAGE") String encodedImage
    );

}
