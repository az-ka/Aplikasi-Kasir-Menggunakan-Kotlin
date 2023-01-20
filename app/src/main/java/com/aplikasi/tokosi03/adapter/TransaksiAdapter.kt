package com.aplikasi.tokosi03.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.tokosi03.CallBackInterface
import com.aplikasi.tokosi03.R
import com.aplikasi.tokosi03.response.cart.Cart
import com.aplikasi.tokosi03.response.produk.Produk
import java.text.NumberFormat
import java.util.*

class TransaksiAdapter(val listProduk: List<Produk>) :
    RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

    var callBackInterface: CallBackInterface? = null
    var total: Int=0
    var cart: ArrayList<Cart> = arrayListOf<Cart>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaksi, parent, false)
        return TransaksiAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listProduk.size
    }

    override fun onBindViewHolder(holder: TransaksiAdapter.ViewHolder, position: Int) {
        val produk = listProduk[position]
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.setMaximumFractionDigits(0);
        holder.txtNamaProduk.text = produk.nama
        holder.txtHargaProduk.text = numberFormat.format(produk.harga.toDouble()).toString()

        holder.btnPlus.setOnClickListener {
            val oldValue = holder.txtQty.text.toString().toInt()
            val newValue = oldValue + 1
            holder.txtQty.setText(newValue.toString())

            total = total+produk.harga.toString().toInt()

            val index = cart.indexOfFirst { it.id == produk.id.toInt() }.toInt()

            if (index!=-1){
                cart.removeAt(index)
            }

            val itemCart = Cart(produk.id.toInt(), produk.harga.toInt(),newValue)
            cart.add(itemCart)

            callBackInterface?.passResultCallback(total.toString(),cart)
        }
        holder.btnMinus.setOnClickListener {
            val oldValue = holder.txtQty.text.toString().toInt()
            val newValue = oldValue - 1

            val index = cart.indexOfFirst { it.id == produk.id.toInt() }.toInt()

            if (index!=-1){
                cart.removeAt(index)
            }

            if (newValue >=0){
                holder.txtQty.setText(newValue.toString())
                total = total-produk.harga.toString().toInt()
            }

            if (newValue>=1){
                val itemCart = Cart(produk.id.toInt(), produk.harga.toInt(),newValue)
                cart.add(itemCart)
            }


            callBackInterface?.passResultCallback(total.toString(),cart)


        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNamaProduk = itemView.findViewById<TextView>(R.id.txtNamaTransaksi)
        val txtHargaProduk = itemView.findViewById<TextView>(R.id.txtHargaTransaksi)
        val txtQty = itemView.findViewById<TextView>(R.id.txtQty)
        val btnPlus = itemView.findViewById<ImageButton>(R.id.btnPlus)
        val btnMinus = itemView.findViewById<ImageButton>(R.id.btnMinus)
    }

}