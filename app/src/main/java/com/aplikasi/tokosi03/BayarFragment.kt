package com.aplikasi.tokosi03

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aplikasi.tokosi03.api.BaseRetrofit
import com.aplikasi.tokosi03.response.ItemTransaksi.itemTransaksiResponsePost
import com.aplikasi.tokosi03.response.cart.Cart
import com.aplikasi.tokosi03.response.transaksi.TransaksiResponsePost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.text.NumberFormat

class BayarFragment : Fragment() {

    private val api by lazy { BaseRetrofit().endpoint }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_bayar, container, false)

        val total = arguments?.getString("TOTAL")
        val myCart = arguments?.getParcelableArrayList<Cart>("myCart")

        val txtKembalian = view.findViewById<TextView>(R.id.txtKembalian)
        val txtTotalBayar = activity?.findViewById<TextView>(R.id.txtTotalPembayaran)
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.setMaximumFractionDigits(0);

        val txtTotalTransaksi = view.findViewById<TextView>(R.id.txtTotalTransaksi)
        txtTotalTransaksi.setText(numberFormat.format(total?.toDouble()).toString())

        val txtPenerimaan = view.findViewById<TextView>(R.id.txtPenerimaan)
        txtPenerimaan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                var kembalian : Int = 0
                if (txtPenerimaan.text.toString()!=""){
                    val penerimaan = txtPenerimaan.text.toString().toInt()
                    kembalian = penerimaan - total.toString().toInt()

                    if (kembalian>0){
//                        txtKembalian.setText(numberFormat.format(kembalian.toDouble().toString()))
                        txtKembalian?.setText(numberFormat.format(kembalian.toString().toDouble()).toString())
                    }else{
                        txtKembalian.setText("Rp. "+ 0)
                    }
                }
            }

        })

        simpanPembayaran(view)

        return view
    }

    fun simpanPembayaran(view: View) {
        val btnSimpan = view.findViewById<Button>(R.id.btnSimpan)
        val token = LoginActivity.sessionManager.getString("TOKEN")
        val admin_id = LoginActivity.sessionManager.getString("ADMIN_ID")
        val total = arguments?.getString("TOTAL")
        val myCart = arguments?.getParcelableArrayList<Cart>("myCart")

        btnSimpan.setOnClickListener {
            api.postTransaksi(token.toString(),admin_id.toString().toInt(),total.toString().toInt()).enqueue(object :Callback<TransaksiResponsePost>{
                override fun onResponse(
                    call: Call<TransaksiResponsePost>,
                    response: Response<TransaksiResponsePost>
                ) {
                    val id_transaksi = response.body()!!.data.transaksi.id
                    Log.e("id-transaksi", id_transaksi.toString())

                    for (items in myCart!!){
                        api.postItemTransaksi(token.toString(),id_transaksi.toString().toInt(),items.id, items.qty, items.harga).enqueue(object : Callback<itemTransaksiResponsePost>{
                            override fun onResponse(
                                call: Call<itemTransaksiResponsePost>,
                                response: Response<itemTransaksiResponsePost>
                            ) {

                            }

                            override fun onFailure(
                                call: Call<itemTransaksiResponsePost>,
                                t: Throwable
                            ) {
                                Log.e("ERROR", t.toString())
                            }

                        })
                    }

                    Toast.makeText(view.context, "Transaksis Berhasil Disimpan", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.transaksiFragment)

                }

                override fun onFailure(call: Call<TransaksiResponsePost>, t: Throwable) {
                    Log.e("ERROR", t.toString())
                }

            })
        }
    }
}