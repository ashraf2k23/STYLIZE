package com.progress.photos.delivery.apiPack

import com.progress.photos.delivery.dataclass.CartData
import com.progress.photos.delivery.dataclass.GlobalResponse
import com.progress.photos.delivery.dataclass.ProductData
import com.progress.photos.delivery.dataclass.User
import com.progress.photos.delivery.dataclass.Result
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    suspend fun loginUser(@Field("email")email:String, @Field("password")password: String) : Response<GlobalResponse>

    @FormUrlEncoded
    @POST("user-details.php")
    suspend fun getUserDetails(@Field("email")email:String) : Response<GlobalResponse>

    @POST("register.php")
    suspend fun registerUser(@Body user:User) : Response<Result>

    @FormUrlEncoded
    @POST("fetchProduct.php")
    suspend fun getProduct(@Field("query") query:String) : Response<ProductData>

    @FormUrlEncoded
    @POST("addCart.php")
    suspend fun addToCart(@Field("productId") productId:Int, @Field("userId") userId: Int, @Field("quantity") quantity:Int, @Field("size") size:String) : Response<Result>

    @FormUrlEncoded
    @POST("fetchCart.php")
    suspend fun fetchCartItems(@Field("userId") user:Int) : Response<CartData>

}