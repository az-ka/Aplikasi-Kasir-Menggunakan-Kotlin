package com.aplikasi.tokosi03

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.tokosi03.adapter.LaporanAdapter
import com.aplikasi.tokosi03.adapter.ProdukAdapter
import com.aplikasi.tokosi03.adapter.TransaksiAdapter
import com.aplikasi.tokosi03.api.BaseRetrofit
import com.aplikasi.tokosi03.response.produk.ProdukResponse
import com.aplikasi.tokosi03.response.transaksi.TransaksiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class LaporanFragment : Fragment() {

    private val api by lazy { BaseRetrofit().endpoint }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_laporan, container, false)

        getLaporan(view)

        return view
    }

    fun getLaporan(view: View) {
        val token = LoginActivity.sessionManager.getString("TOKEN")

        api.getTransaksi(token.toString()).enqueue(object : Callback<TransaksiResponse> {
            override fun onResponse(
                call: Call<TransaksiResponse>,
                response: Response<TransaksiResponse>
            ) {
                val rvLaporan = view.findViewById<RecyclerView>(R.id.rv_pendapatan)
                val localeID = Locale("in", "ID")
                val numberFormat = NumberFormat.getCurrencyInstance(localeID)
                numberFormat.setMaximumFractionDigits(0);
                val totalPendapatan = view.findViewById<TextView>(R.id.totalPendapatan)
                val totalKeseluruhan = response.body()!!.data.total

                totalPendapatan.text = numberFormat.format(totalKeseluruhan.toDouble()).toString()



                rvLaporan.setHasFixedSize(true)
                rvLaporan.layoutManager = LinearLayoutManager(activity)
                val rvAdapter = LaporanAdapter(response.body()!!.data.transaksi)
                rvLaporan.adapter = rvAdapter
            }

            override fun onFailure(call: Call<TransaksiResponse>, t: Throwable) {
                Log.e("Error", t.toString())
            }

        })
    }
}