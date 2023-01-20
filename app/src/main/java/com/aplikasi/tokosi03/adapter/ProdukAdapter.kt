package com.aplikasi.tokosi03.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.tokosi03.LoginActivity
import com.aplikasi.tokosi03.R
import com.aplikasi.tokosi03.response.produk.Produk
import com.aplikasi.tokosi03.api.BaseRetrofit
import com.aplikasi.tokosi03.response.produk.ProdukResponsePost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*


class ProdukAdapter(val listProduk: List<Produk>): RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    private val api by lazy { BaseRetrofit().endpoint }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listProduk.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produk = listProduk[position]
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.setMaximumFractionDigits(0);
        holder.txtNamaProduk.text = produk.nama
        holder.txtHargaProduk.text = numberFormat.format(produk.harga.toDouble()).toString()

        holder.btnDelete.setOnClickListener {
            Toast.makeText(holder.itemView.context, produk.nama,Toast.LENGTH_LONG).show()
            val token = LoginActivity.sessionManager.getString("TOKEN")
            val admin_id = LoginActivity.sessionManager.getString("ADMIN_ID")

            api.deleteProduk(token.toString(),produk.id.toInt()).enqueue(object :Callback<ProdukResponsePost>{
                override fun onResponse(
                    call: Call<ProdukResponsePost>,
                    response: Response<ProdukResponsePost>
                ) {
                    Toast.makeText(holder.itemView.context, "Delete " +produk.nama.toString()+ " Success", Toast.LENGTH_LONG).show()

                    holder.itemView.findNavController().navigate(R.id.produkFragment)
                }

                override fun onFailure(call: Call<ProdukResponsePost>, t: Throwable) {
                    Log.e("Error", t.toString())
                }

            })
        }

        holder.btnEdit.setOnClickListener {
            Toast.makeText(holder.itemView.context, produk.nama, Toast.LENGTH_LONG).show()

            val bundle = Bundle()
            bundle.putParcelable("produk", produk)


            holder.itemView.findNavController().navigate(R.id.produkFromEditFragment, bundle)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNamaProduk = itemView.findViewById<TextView>(R.id.txtNamaProduk)
        val txtHargaProduk = itemView.findViewById<TextView>(R.id.txtHargaProduk)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.btnDeleteProduk)
        val btnEdit = itemView.findViewById<ImageButton>(R.id.btnEditProduk)
    }
}