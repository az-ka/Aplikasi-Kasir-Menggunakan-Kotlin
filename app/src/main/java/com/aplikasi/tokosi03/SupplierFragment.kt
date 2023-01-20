package com.aplikasi.tokosi03

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.tokosi03.adapter.SupplierAdapter
import com.aplikasi.tokosi03.adapter.TransaksiAdapter
import com.aplikasi.tokosi03.response.supplier.Supplier

class SupplierFragment : Fragment() {

    private lateinit var supplierList: ArrayList<Supplier>
    private lateinit var supplierAdapter: SupplierAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_supplier, container, false)

        val rvSupplier = view.findViewById<RecyclerView>(R.id.rv_supplier)
        rvSupplier.setHasFixedSize(true)
        rvSupplier.layoutManager = LinearLayoutManager(activity)

        supplierList = ArrayList()

        supplierList.add(Supplier("Bu Sumi"))
        supplierList.add(Supplier("Pak Suhar"))
        supplierList.add(Supplier("Pak Salis"))
        supplierList.add(Supplier("Pak Muklis"))
        supplierList.add(Supplier("Bu Putri"))
        supplierList.add(Supplier("Bu Salis"))
        supplierList.add(Supplier("Mbak Nurul"))
        supplierList.add(Supplier("Pak Rt"))
        supplierList.add(Supplier("Jajanan Pasar"))
        supplierList.add(Supplier("Bu Yanti"))
        supplierList.add(Supplier("Mas Tomo"))
        supplierList.add(Supplier("Dark Web"))

        supplierAdapter = SupplierAdapter(supplierList)
        rvSupplier.adapter = supplierAdapter


        supplierAdapter.onItemClick = {
            val supplierNama = arguments?.getString("supplierNama")
        }

        return view
    }
}