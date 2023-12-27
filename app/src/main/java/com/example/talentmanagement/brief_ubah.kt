package com.example.talentmanagement

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class brief_ubah : AppCompatActivity() {
    // Metode onCreate dipanggil ketika aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengatur tata letak aktivitas dengan menggunakan layout dari file XML activity_brief_ubah
        setContentView(R.layout.activity_brief_ubah)

        // Mendapatkan ID bakat yang dipilih dari intent
        val id_brief_terpilih:String = intent.getStringExtra("id_brief_terpilih").toString()

        // Membuka atau membuat database SQLite dengan nama "tm"
        val dbtm: SQLiteDatabase = openOrCreateDatabase("tm", MODE_PRIVATE, null)
        // Mengambil data bakat dari database berdasarkan ID bakat yang dipilih
        val ambil = dbtm.rawQuery("SELECT * FROM tb_brief WHERE IdBrief = '$id_brief_terpilih'", null)
        ambil.moveToNext()

        // Mendapatkan data dari hasil query
        val isi_brief:String = ambil.getString(1)
        val isi_deskripsi:String = ambil.getString(2)
        val isi_talent:String = ambil.getString(3)

        // Mendapatkan referensi ke elemen-elemen UI
        val edt_judul : EditText = findViewById(R.id.edt_judul)
        val edt_untuk: EditText = findViewById(R.id.edt_untuk)
        val edt_deskripsi: EditText = findViewById(R.id.edt_deskripsi)
        val btn_simpan: Button = findViewById(R.id.btn_simpan)

        // Menetapkan nilai dari database ke EditText
        edt_judul.setText(isi_brief)
        edt_untuk.setText(isi_talent)
        edt_deskripsi.setText(isi_deskripsi)


        // Menangani klik pada tombol simpan
        btn_simpan.setOnClickListener {
            // Mendapatkan nilai dari EditText
            val judul_baru: String = edt_judul.text.toString()
            val untuk_baru: String = edt_untuk.text.toString()
            val deskripsi_baru: String = edt_deskripsi.text.toString()

            // Query SQL untuk memperbarui data bakat di database
            val sql = "UPDATE tb_brief SET Brief=?, Description=?, IdTalent=? WHERE IdBrief ='$id_brief_terpilih'"
            val statement = dbtm.compileStatement(sql)
            statement.clearBindings()
            statement.bindString(1, judul_baru)
            statement.bindString(2, deskripsi_baru)
            statement.bindString(3, untuk_baru)

            // Eksekusi query update
            statement.executeUpdateDelete()

            // Pindah ke aktivitas dashboard setelah menyimpan perubahan
            val pindah: Intent = Intent(this, dashboard::class.java)
            startActivity(pindah)
        }
    }
}