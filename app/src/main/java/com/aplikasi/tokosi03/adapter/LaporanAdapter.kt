package com.aplikasi.tokosi03.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.tokosi03.R
import com.aplikasi.tokosi03.response.produk.Produk
import com.aplikasi.tokosi03.response.transaksi.Transaksi
import java.util.*
import java.text.NumberFormat

class LaporanAdapter(val listTransaksi: List<Transaksi>): RecyclerView.Adapter<LaporanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_laporan, parent,false)
        return LaporanAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTransaksi.size
    }

    override fun onBindViewHolder(holder: LaporanAdapter.ViewHolder, position: Int) {
        val transaksi = listTransaksi[position]
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.setMaximumFractionDigits(0);
        holder.txtTanggalTransaksi.text = transaksi.tanggal
        holder.txtNoNota.text = "#0000" + transaksi.id
        holder.txtTransaksiTotal.text = numberFormat.format(transaksi.total.toDouble()).toString()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTanggalTransaksi = itemView.findViewById<TextView>(R.id.txtTanggalTransaksi)
        val txtNoNota = itemView.findViewById<TextView>(R.id.txtNoNota)
        val txtTransaksiTotal = itemView.findViewById<TextView>(R.id.txtTransaksiTotal)
    }

}