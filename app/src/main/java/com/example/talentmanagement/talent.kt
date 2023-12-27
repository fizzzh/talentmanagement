package com.example.talentmanagement

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayInputStream
import java.lang.Exception
import android.content.Context

class talent : Fragment() {

    // Fungsi ini dipanggil ketika tampilan fragment dibuat
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk tampilan talent menggunakan XML layout activity_talent
        return inflater.inflate(R.layout.activity_talent, container, false)
    }

    // Fungsi ini dipanggil setelah tampilan fragment dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil referensi RecyclerView dari tampilan
        val rv_talent: RecyclerView = view.findViewById(R.id.rv_talent)

        // Membuat beberapa list untuk menampung data dari database
        val id: MutableList<String> = mutableListOf()
        val nama : MutableList<String> = mutableListOf();
        val nomor : MutableList<String> = mutableListOf();
        val idCategory : MutableList<String> = mutableListOf();
        val foto: MutableList<Bitmap> = mutableListOf();

        // Membuat atau membuka database lokal "tm"
        val dbtm: SQLiteDatabase = requireActivity().openOrCreateDatabase("tm", Context.MODE_PRIVATE, null)

        // Melakukan query pada database untuk mengambil data dari tabel talent dan category
        val gali_talent = dbtm.rawQuery("SELECT tb_talent.IdTalent, tb_talent.NameTalent, tb_talent.PhotoTalent, tb_category.Category\n" +
                "FROM tb_talent\n" +
                "JOIN tb_category ON tb_talent.IdCategory = tb_category.IdCategory\n", null)

        // Mengambil data dari hasil query dan menyimpannya ke dalam list yang telah dibuat
        while (gali_talent.moveToNext()) {
            try {
                val bis = ByteArrayInputStream(gali_talent.getBlob(2))
                val gambarbitmap: Bitmap = BitmapFactory.decodeStream(bis)
                foto.add(gambarbitmap)
            } catch (e: Exception) {
                // Jika ada kesalahan dalam mengambil gambar dari database, akan ditampilkan gambar default
                val gambarbitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.noimage)
                foto.add(gambarbitmap)
            }
            id.add(gali_talent.getString(0))
            nama.add(gali_talent.getString(1))
            nomor.add(gali_talent.getString(0))
            idCategory.add(gali_talent.getString(3))
        }

        // Membuat instance dari talent_item adapter dengan menggunakan data yang sudah diambil
        val mi = talent_item(requireContext(), id, nama, nomor, idCategory, foto)

        // Menetapkan adapter dan layout manager untuk RecyclerView
        rv_talent.adapter = mi
        rv_talent.layoutManager = GridLayoutManager(requireContext(), 2)

        // Mengambil referensi tombol tambah dari tampilan dan menambahkan onClickListener
        val btn_tambah: Button = view.findViewById(R.id.btn_tambah)
        btn_tambah.setOnClickListener {
            // Ketika tombol tambah ditekan, akan berpindah ke halaman talent_tambah
            val pindah = Intent(requireContext(), talent_tambah::class.java)
            startActivity(pindah)
        }
    }
}