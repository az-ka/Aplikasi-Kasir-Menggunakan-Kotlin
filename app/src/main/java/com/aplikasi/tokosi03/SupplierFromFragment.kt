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
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.aplikasi.tokosi03.api.BaseRetrofit
import com.aplikasi.tokosi03.response.produk.ProdukResponsePost
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class SupplierFromFragment : Fragment() {
    private val api by lazy { BaseRetrofit().endpoint }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_supplier_from, container, false)

        getNamaSupp(view)
        totalSupplier(view)


        return view
    }

    fun getNamaSupp(view: View) {
        val supplierNama = arguments?.getString("supplierNama")
        val txtSupplierNama = view.findViewById<TextView>(R.id.txtSupplierNama)
        val btnLangganan = view.findViewById<Button>(R.id.btnPostLangganan)

        txtSupplierNama.setText(supplierNama.toString())

        btnLangganan.setOnClickListener {
            val txtFromNama = view.findViewById<TextInputEditText>(R.id.txtFromNamaSupplier)
            val txtFromHarga = view.findViewById<TextInputEditText>(R.id.txtFromHargaSupplier)
            val txtFormStok = view.findViewById<TextInputEditText>(R.id.txtFromStokSupplier)

            val token = LoginActivity.sessionManager.getString("TOKEN")
            val admin_id = LoginActivity.sessionManager.getString("ADMIN_ID")

            api.postProduk(token.toString(),admin_id.toString().toInt(),txtFromNama.text.toString(),txtFromHarga.text.toString().toInt(),txtFormStok.text.toString().toInt()).enqueue(object :
                Callback<ProdukResponsePost> {
                override fun onResponse(
                    call: Call<ProdukResponsePost>,
                    response: Response<ProdukResponsePost>
                ) {
                    Log.d("Success", response.toString())
                    Toast.makeText(activity?.applicationContext,"Produk Supplier Berhasil Ditambahkan", Toast.LENGTH_LONG).show()

                    findNavController().navigate(R.id.supplierFragment)
                }

                override fun onFailure(call: Call<ProdukResponsePost>, t: Throwable) {
                    Log.e("ERROR", t.toString())
                }

            })
        }
    }

    fun totalSupplier(view: View) {
        val txtHarga = view.findViewById<TextInputEditText>(R.id.txtFromHargaSupplier)


        txtHarga.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                val txtHarga = view.findViewById<TextInputEditText>(R.id.txtFromHargaSupplier)
                val txtJumlah = view.findViewById<TextInputEditText>(R.id.txtFromStokSupplier)
                val localeID = Locale("in", "ID")
                val numberFormat = NumberFormat.getCurrencyInstance(localeID)
                numberFormat.setMaximumFractionDigits(0);

                val txtTotalSupplier = view.findViewById<TextView>(R.id.txtTotalSupplier)

                txtJumlah.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }

                    override fun afterTextChanged(s: Editable?) {
                        var kembalian : Int = 0
                        try {
                            if (txtHarga.text.toString()!=""){
                                val penerimaan = txtHarga.text.toString().toInt()
                                kembalian = penerimaan * txtJumlah.text.toString().toInt()


                                if (kembalian>0){
//                        txtKembalian.setText(numberFormat.format(kembalian.toDouble().toString()))
                                    txtTotalSupplier?.setText(numberFormat.format(kembalian.toString().toDouble()).toString())
                                }else{
                                    txtTotalSupplier.setText("Rp. "+ 0)
                                }
                            }
                        }catch (ex:Exception){
                            Toast.makeText(context,"Jumlah Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
                        }
                    }
            })

        }
    })
}
}