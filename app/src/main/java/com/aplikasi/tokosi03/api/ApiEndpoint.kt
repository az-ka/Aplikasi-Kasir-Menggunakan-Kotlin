package com.aplikasi.tokosi03.api

import com.aplikasi.tokosi03.response.ItemTransaksi.itemTransaksiResponsePost
import com.aplikasi.tokosi03.response.login.LoginResponse
import com.aplikasi.tokosi03.response.produk.ProdukResponse
import com.aplikasi.tokosi03.response.produk.ProdukResponsePost
import com.aplikasi.tokosi03.response.transaksi.TransaksiResponse
import com.aplikasi.tokosi03.response.transaksi.TransaksiResponsePost
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("produk")
    fun getProduk(
        @Header("Authorization") token: String
    ): Call<ProdukResponse>

    @FormUrlEncoded
    @POST("produk")
    fun postProduk(
        @Header("Authorization") token: String,
        @Field("admin_id") admin_id: Int,
        @Field("nama") nama: String,
        @Field("harga") harga: Int,
        @Field("stok") stok: Int,
    ): Call<ProdukResponsePost>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "produk", hasBody = true)
    fun deleteProduk(
        @Header("Authorization") token: String,
        @Field("id") admin_id: Int,
    ): Call<ProdukResponsePost>

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "produk", hasBody = true)
    fun updateProduk(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
        @Field("admin_id") admin_id: Int,
        @Field("nama") nama : String,
        @Field("harga") harga : Int,
        @Field("stok") stok: Int
        ): Call<ProdukResponsePost>

    @FormUrlEncoded
    @POST("transaksi")
    fun postTransaksi(
        @Header("Authorization") token: String,
        @Field("admin_id") admin_id: Int,
        @Field("total") total: Int,
    ): Call<TransaksiResponsePost>

    @FormUrlEncoded
    @POST("item_transaksi")
    fun postItemTransaksi(
        @Header("Authorization") token: String,
        @Field("transaksi_id") admin_id: Int,
        @Field("produk_id") produk_id: Int,
        @Field("qty") qty: Int,
        @Field("harga_saat_transaksi") harga_saat_transaksi: Int,
    ): Call<itemTransaksiResponsePost>

    @GET("transaksi_bulan_ini")
    fun getTransaksi(
        @Header("Authorization") token: String
    ): Call<TransaksiResponse>
}