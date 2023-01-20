package com.aplikasi.tokosi03.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aplikasi.tokosi03.R
import com.aplikasi.tokosi03.response.supplier.Supplier

class SupplierAdapter(private val listSupplier:ArrayList<Supplier>) : RecyclerView.Adapter<SupplierAdapter.ViewHolder>() {

    var onItemClick : ((Supplier) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaSupplier = itemView.findViewById<TextView>(R.id.txtNamaSupplier)
        val btnLangganan = itemView.findViewById<Button>(R.id.btnLangganan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_supplier,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listSupplier.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val supplier = listSupplier[position]
        holder.namaSupplier.text = supplier.namaSupplier


        holder.btnLangganan.setOnClickListener {
            onItemClick?.invoke(supplier)
            val bundle = Bundle()
            bundle.putString("supplierNama", supplier.namaSupplier)


            holder.itemView.findNavController().navigate(R.id.supplierFromFragment, bundle)
        }
    }
}