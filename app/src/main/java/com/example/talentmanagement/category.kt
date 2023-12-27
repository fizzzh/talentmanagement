package com.example.talentmanagement

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Context

class category : Fragment() {
    // Meng-override metode onCreateView untuk membuat tampilan fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Mengembalikan tampilan yang diinflate dari layout activity_category
        return inflater.inflate(R.layout.activity_category, container, false)
    }

    // Meng-override metode onViewCreated yang dipanggil setelah tampilan dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil referensi RecyclerView dengan id rv_category dari tampilan
        val rv_category: RecyclerView = view.findViewById(R.id.rv_brief)

        // Membuat MutableList untuk menyimpan data id, nama, dan idCategory
        val id: MutableList<String> = mutableListOf()
        val nama: MutableList<String> = mutableListOf()
        val idCategory: MutableList<String> = mutableListOf()

        // Membuat atau membuka database "tm" dengan mode private
        val dbtm: SQLiteDatabase = requireActivity().openOrCreateDatabase("tm", Context.MODE_PRIVATE, null)

        // Melakukan query untuk mengambil semua data dari tabel tb_category
        val gali_category = dbtm.rawQuery("SELECT * FROM tb_category", null)

        // Memproses hasil query dan menambahkan data ke dalam MutableList
        while (gali_category.moveToNext()) {
            id.add(gali_category.getString(0))
            nama.add(gali_category.getString(1))
            idCategory.add(gali_category.getString(0))
        }

        // Membuat instance dari category_item dengan menggunakan data yang telah diambil
        val mi = category_item(requireContext(), id, nama, idCategory)

        // Mengatur adapter RecyclerView dengan category_item
        rv_category.adapter = mi

        // Mengatur layout manager RecyclerView menjadi GridLayoutManager dengan 2 kolom
        rv_category.layoutManager = GridLayoutManager(requireContext(), 2)

        // Mengambil referensi tombol btn_tambah dari tampilan
        val btn_tambah: Button = view.findViewById(R.id.btn_tambah)

        // Menambahkan listener klik pada tombol btn_tambah
        btn_tambah.setOnClickListener {
            // Membuat intent untuk memulai activity category_tambah
            val pindah = Intent(requireContext(), category_tambah::class.java)
            startActivity(pindah) // Memulai activity category_tambah
        }
    }
}