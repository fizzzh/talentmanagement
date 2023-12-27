package com.example.talentmanagement

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class brief : Fragment() {
    // Meng-override metode onCreateView untuk membuat tampilan fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Mengembalikan tampilan yang diinflate dari layout activity_brief
        return inflater.inflate(R.layout.activity_brief, container, false)
    }

    // Meng-override metode onViewCreated yang dipanggil setelah tampilan dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil referensi RecyclerView dengan id rv_brief dari tampilan
        val rv_brief: RecyclerView = view.findViewById(R.id.rv_brief)

        // Membuat MutableList untuk menyimpan data id, nama, dan idBrief
        val id: MutableList<String> = mutableListOf()
        val title: MutableList<String> = mutableListOf()
        val nama: MutableList<String> = mutableListOf()
        val deskripsi: MutableList<String> = mutableListOf()

        // Membuat atau membuka database "tm" dengan mode private
        val dbtm: SQLiteDatabase = requireActivity().openOrCreateDatabase("tm", Context.MODE_PRIVATE, null)

        // Melakukan query untuk mengambil semua data dari tabel tb_brief
        val gali_brief = dbtm.rawQuery("SELECT tb_brief.IdBrief, tb_brief.Brief, tb_brief.Description, tb_talent.NameTalent AS IdTalent\n" +
                "FROM tb_brief\n" +
                "INNER JOIN tb_talent ON tb_brief.IdTalent = tb_talent.IdTalent;\n", null)

        // Memproses hasil query dan menambahkan data ke dalam MutableList
        while (gali_brief.moveToNext()) {
            id.add(gali_brief.getString(0))
            title.add(gali_brief.getString(1))
            nama.add(gali_brief.getString(2))
            deskripsi.add(gali_brief.getString(3))
        }

        // Membuat instance dari brief_item dengan menggunakan data yang telah diambil
        val mi = brief_item(requireContext(), id, deskripsi, title, nama)

        // Mengatur adapter RecyclerView dengan brief_item
        rv_brief.adapter = mi

        // Mengatur layout manager RecyclerView menjadi GridLayoutManager dengan 1 kolom
        rv_brief.layoutManager = GridLayoutManager(requireContext(), 1)

        // Mengambil referensi tombol btn_tambah dari tampilan
        val btn_tambah: Button = view.findViewById(R.id.btn_tambah)

        // Menambahkan listener klik pada tombol btn_tambah
        btn_tambah.setOnClickListener {
            // Membuat intent untuk memulai activity brief_tambah
            val pindah = Intent(requireContext(), brief_tambah::class.java)
            startActivity(pindah) // Memulai activity brief_tambah
        }
    }
}