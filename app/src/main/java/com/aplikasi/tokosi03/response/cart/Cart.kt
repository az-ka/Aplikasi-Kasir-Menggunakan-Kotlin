package com.aplikasi.tokosi03.response.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart(
    val id: Int,
    var harga: Int,
    var qty: Int
):Parcelable
