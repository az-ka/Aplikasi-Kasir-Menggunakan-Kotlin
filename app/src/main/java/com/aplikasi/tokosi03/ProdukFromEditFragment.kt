package com.aplikasi.tokosi03

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aplikasi.tokosi03.api.BaseRetrofit
import com.aplikasi.tokosi03.response.produk.Produk
import com.aplikasi.tokosi03.response.produk.ProdukResponsePost
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukFromEditFragment : Fragment() {
    private val api by lazy { BaseRetrofit().endpoint }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_produk_from_edit, container, false)

        editProduk(view)

        return view
    }

    fun editProduk(view: View) {
        val btnEdit = view.findViewById<Button>(R.id.btnUpdateProduk)
        val txtEditNama = view.findViewById<TextInputEditText>(R.id.txtEditNama)
        val txtEditHarga = view.findViewById<TextInputEditText>(R.id.txtEditHarga)
        val txtEditStok = view.findViewById<TextInputEditText>(R.id.txtEditStok)
        val token = LoginActivity.sessionManager.getString("TOKEN")
        val admin_id = LoginActivity.sessionManager.getString("ADMIN_ID")

        val produk = arguments?.getParcelable<Produk>("produk")

        Log.d("Produk data", produk.toString())
        if (produk != null) {
            txtEditNama.setText(produk.nama.toString())
            txtEditHarga.setText(produk.harga.toString())
            txtEditStok.setText(produk.stok.toString())
        }


        btnEdit.setOnClickListener {
            if (produk != null) {
                api.updateProduk(
                    token.toString(),
                    produk.id.toInt(),
                    admin_id.toString().toInt(),
                    txtEditNama.text.toString(),
                    txtEditHarga.text.toString().toInt(),
                    txtEditStok.text.toString().toInt()
                ).enqueue(object : Callback<ProdukResponsePost> {
                    override fun onResponse(
                        call: Call<ProdukResponsePost>,
                        response: Response<ProdukResponsePost>
                    ) {
                        Toast.makeText(context, "Produk Berhasil DiUpdate", Toast.LENGTH_SHORT).show()

                        findNavController().navigate(R.id.produkFragment)
                    }

                    override fun onFailure(call: Call<ProdukResponsePost>, t: Throwable) {
                        Log.e("ERROR", t.toString())
                    }

                })
            }
        }
    }

}