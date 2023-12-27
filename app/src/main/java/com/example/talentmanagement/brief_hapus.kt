package com.example.talentmanagement

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class brief_hapus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brief_hapus)

        // Mendapatkan id brief yang akan dihapus dari data yang dikirimkan sebelumnya
        val id_brief_terpilih: String = intent.getStringExtra("id_brief_terpilih").toString()

        // Membuat koneksi ke database
        val dbtm: SQLiteDatabase = openOrCreateDatabase("tm", MODE_PRIVATE, null)

        // Mengeksekusi query untuk menghapus data dengan id tertentu dari tabel tb_brief
        val query = dbtm.rawQuery("DELETE FROM tb_brief WHERE IdBrief='$id_brief_terpilih'", null)
        query.moveToNext() // Memindahkan kursor ke hasil query (walaupun tidak perlu di sini)

        // Setelah menghapus, akan beralih ke halaman dashboard
        val pindah: Intent = Intent(this, dashboard::class.java )
        startActivity(pindah) // Memulai aktivitas dashboard
    }
}