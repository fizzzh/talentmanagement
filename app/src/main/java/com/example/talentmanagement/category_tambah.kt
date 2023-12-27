package com.example.talentmanagement

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class category_tambah : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_tambah)  // Mengatur tata letak aktivitas dengan layout yang didefinisikan di XML

        // Mendapatkan referensi ke elemen UI menggunakan ID
        val edt_id: EditText = findViewById(R.id.edt_judul)
        val edt_category: EditText = findViewById(R.id.edt_untuk)
        val btn_simpan: Button = findViewById(R.id.btn_simpan)

        // Menetapkan aksi klik pada tombol simpan
        btn_simpan.setOnClickListener {
            val isi_id:String = edt_id.text.toString()  // Mendapatkan teks dari EditText id
            val isi_category:String = edt_category.text.toString()    // Mendapatkan teks dari EditText category

            // Membuka atau membuat database SQLite dengan nama "tm"
            val dbtm: SQLiteDatabase = openOrCreateDatabase("tm", MODE_PRIVATE, null)

            // Query SQL untuk menyimpan data ke dalam tabel tb_category
            val sql = "INSERT INTO tb_category (IdCategory,Category) VALUES (?,?)"
            val statement = dbtm.compileStatement(sql)

            // Mengikat nilai ke parameter SQL
            statement.clearBindings()
            statement.bindString(1, isi_id)
            statement.bindString(2, isi_category)

            // Mengeksekusi query SQL untuk memasukkan data
            statement.executeInsert()

            // Pindah ke aktivitas dashboard setelah menyimpan data
            val pindah: Intent = Intent(this, dashboard::class.java)
            startActivity(pindah)
        }
    }
}