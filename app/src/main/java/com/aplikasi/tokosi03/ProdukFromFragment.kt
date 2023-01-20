package com.aplikasi.tokosi03

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aplikasi.tokosi03.api.BaseRetrofit
import com.aplikasi.tokosi03.response.produk.ProdukResponsePost
import com.google.android.material.textfield.TextInputEditText
import com.aplikasi.tokosi03.LoginActivity.Companion.sessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukFromFragment : Fragment() {
    private val api by lazy { BaseRetrofit().endpoint }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_produk_from, container, false)


        createProduk(view)

        return view
    }

    fun createProduk(view: View) {
        val btnCreate = view.findViewById<Button>(R.id.btnCreate)

        btnCreate.setOnClickListener {
            val txtFromNama = view.findViewById<TextInputEditText>(R.id.txtFromNamaProduk)
            val txtFromHarga = view.findViewById<TextInputEditText>(R.id.txtFromHargaProduk)
            val txtFormStok = view.findViewById<TextInputEditText>(R.id.txtFromStokProduk)

            val token = LoginActivity.sessionManager.getString("TOKEN")
            val admin_id = LoginActivity.sessionManager.getString("ADMIN_ID")

            api.postProduk(token.toString(),admin_id.toString().toInt(),txtFromNama.text.toString(),txtFromHarga.text.toString().toInt(),txtFormStok.text.toString().toInt()).enqueue(object :Callback<ProdukResponsePost>{
                override fun onResponse(
                    call: Call<ProdukResponsePost>,
                    response: Response<ProdukResponsePost>
                ) {
                    Log.d("Crete Produk Berhasil", response.toString())
                    Toast.makeText(activity?.applicationContext,"Berhasil", Toast.LENGTH_LONG).show()

                    findNavController().navigate(R.id.produkFragment)
                }

                override fun onFailure(call: Call<ProdukResponsePost>, t: Throwable) {
                    Log.e("ERROR", t.toString())
                }

            })
        }
    }
}