package com.example.talentmanagement

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class category_ubah : AppCompatActivity() {
    // Metode onCreate dipanggil ketika aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengatur tata letak aktivitas dengan menggunakan layout dari file XML activity_category_ubah
        setContentView(R.layout.activity_category_ubah)

        // Mendapatkan ID bakat yang dipilih dari intent
        val id_category_terpilih:String = intent.getStringExtra("id_category_terpilih").toString()

        // Membuka atau membuat database SQLite dengan nama "tm"
        val dbtm: SQLiteDatabase = openOrCreateDatabase("tm", MODE_PRIVATE, null)
        // Mengambil data bakat dari database berdasarkan ID bakat yang dipilih
        val ambil = dbtm.rawQuery("SELECT * FROM tb_category WHERE IdCategory = '$id_category_terpilih'", null)
        ambil.moveToNext()

        // Mendapatkan data dari hasil query
        val isi_id:String = ambil.getString(0)
        val isi_category:String = ambil.getString(1)

        // Mendapatkan referensi ke elemen-elemen UI
        val edt_id: EditText = findViewById(R.id.edt_judul)
        val edt_category: EditText = findViewById(R.id.edt_untuk)
        val btn_simpan: Button = findViewById(R.id.btn_simpan)

        // Menetapkan nilai dari database ke EditText
        edt_id.setText(isi_id)
        edt_category.setText(isi_category)

        // Menangani klik pada tombol simpan
        btn_simpan.setOnClickListener {
            // Mendapatkan nilai dari EditText
            val id_baru: String = edt_id.text.toString()
            val category_baru: String = edt_category.text.toString()

            // Query SQL untuk memperbarui data bakat di database
            val sql = "UPDATE tb_category SET IdCategory=?, Category=? WHERE IdCategory ='$id_category_terpilih'"
            val statement = dbtm.compileStatement(sql)
            statement.clearBindings()
            statement.bindString(1, id_baru)
            statement.bindString(2, category_baru)

            // Eksekusi query update
            statement.executeUpdateDelete()

            // Pindah ke aktivitas dashboard setelah menyimpan perubahan
            val pindah: Intent = Intent(this, dashboard::class.java)
            startActivity(pindah)
        }
    }
}