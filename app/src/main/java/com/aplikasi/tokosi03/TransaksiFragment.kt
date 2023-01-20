package com.aplikasi.tokosi03

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.tokosi03.adapter.ProdukAdapter
import com.aplikasi.tokosi03.adapter.TransaksiAdapter
import com.aplikasi.tokosi03.api.BaseRetrofit
import com.aplikasi.tokosi03.response.cart.Cart
import com.aplikasi.tokosi03.response.produk.ProdukResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import java.text.NumberFormat

class TransaksiFragment : Fragment(), CallBackInterface {
    private val api by lazy { BaseRetrofit().endpoint }
    private lateinit var myCart: ArrayList<Cart>
    private lateinit var totalBayar: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaksi, container, false)

        getTransaksi(view)
        simpanTransaksi(view)

        return view
    }

    fun getTransaksi(view: View) {
        val token = LoginActivity.sessionManager.getString("TOKEN")

        api.getProduk(token.toString()).enqueue(object : Callback<ProdukResponse> {
            override fun onResponse(
                call: Call<ProdukResponse>,
                response: Response<ProdukResponse>
            ) {
                Log.d("TransaksiData", response.body().toString())

//                val txtTotalProduk = view.findViewById<TextView>(R.id.txtTotalProduk)
                val rvTransaksi = view.findViewById<RecyclerView>(R.id.rv_transaksi)

//                txtTotalProduk.text = response.body()!!.data.produk.size.toString()+" Item"

                rvTransaksi.setHasFixedSize(true)
                rvTransaksi.layoutManager = LinearLayoutManager(activity)
                val rvAdapter = TransaksiAdapter(response.body()!!.data.produk)
                rvTransaksi.adapter = rvAdapter

                rvAdapter.callBackInterface = object : CallBackInterface{
                    override fun passResultCallback(total: String, cart: ArrayList<Cart>) {
                        val txtTotalBayar = activity?.findViewById<TextView>(R.id.txtTotalPembayaran)
                        val localeID = Locale("in", "ID")
                        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
                        numberFormat.setMaximumFractionDigits(0);

                        txtTotalBayar?.setText(numberFormat.format(total.toDouble()).toString())
                        totalBayar = total
                        myCart = cart

                        Log.d("myCart", cart.toString())
                    }

                }
            }

            override fun onFailure(call: Call<ProdukResponse>, t: Throwable) {
                Log.e("Error", t.toString())
            }

        })
    }

    fun simpanTransaksi(view: View) {
        val btnBayar = view.findViewById<Button>(R.id.btnBayar)
        btnBayar.setOnClickListener{
            val bundle = Bundle()
            bundle.putParcelableArrayList("myCart", myCart)
            bundle.putString("TOTAL", totalBayar )

            findNavController().navigate(R.id.bayarFragment, bundle)
        }
    }

    override fun passResultCallback(total: String, cart: ArrayList<Cart>) {
        TODO("Not yet implemented")
    }
}