package com.example.talentmanagement

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class brief_tambah : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brief_tambah)  // Mengatur tata letak aktivitas dengan layout yang didefinisikan di XML

        // Mendapatkan referensi ke elemen UI menggunakan ID
        val edt_judul: EditText = findViewById(R.id.edt_judul)
        val edt_untuk: EditText = findViewById(R.id.edt_untuk)
        val edt_deskripsi: EditText = findViewById(R.id.edt_deskripsi)
        val btn_simpan: Button = findViewById(R.id.btn_simpan)

        // Menetapkan aksi klik pada tombol simpan
        btn_simpan.setOnClickListener {
            val isi_judul:String = edt_judul.text.toString()  // Mendapatkan teks dari EditText judul
            val isi_untuk:String = edt_untuk.text.toString()  // Mendapatkan teks dari EditText untuk
            val isi_deskripsi:String = edt_deskripsi.text.toString()    // Mendapatkan teks dari EditText deskripsi

            // Membuka atau membuat database SQLite dengan nama "tm"
            val dbtm: SQLiteDatabase = openOrCreateDatabase("tm", MODE_PRIVATE, null)

            // Query SQL untuk menyimpan data ke dalam tabel tb_brief
            val sql = "INSERT INTO tb_brief (Brief,Description,IdTalent) VALUES (?,?,?)"
            val statement = dbtm.compileStatement(sql)

            // Mengikat nilai ke parameter SQL
            statement.clearBindings()
            statement.bindString(1, isi_judul)
            statement.bindString(2, isi_deskripsi)
            statement.bindString(3, isi_untuk)

            // Mengeksekusi query SQL untuk memasukkan data
            statement.executeInsert()

            // Pindah ke aktivitas dashboard setelah menyimpan data
            val pindah: Intent = Intent(this, dashboard::class.java)
            startActivity(pindah)
        }
    }
}