package com.aplikasi.tokosi03.response.produk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Produk(
    val admin_id: Int,
    val harga: Int,
    val id: Int,
    val nama: String,
    val nama_admin: String,
    val stok: Int
): Parcelable