package com.example.talentmanagement

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayOutputStream

class talent_tambah : AppCompatActivity() {
    // Deklarasi untuk konversi gambar
    var urlgambar: Uri?=null            // Variabel untuk menyimpan URI gambar yang dipilih dari galeri
    var bitmapgmabar: Bitmap?=null      // Variabel untuk menyimpan gambar dalam bentuk bitmap
    var iv_upload: ImageView?=null      // Variabel untuk mereferensikan ImageView di layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talent_tambah)  // Mengatur tata letak aktivitas dengan layout yang didefinisikan di XML

        // Mendapatkan referensi ke elemen UI menggunakan ID
        val edt_category: EditText = findViewById(R.id.edt_judul)
        val edt_nama: EditText = findViewById(R.id.edt_untuk)
        val btn_simpan: Button = findViewById(R.id.btn_simpan)
        iv_upload = findViewById(R.id.iv_upload)

        // Menetapkan aksi klik pada ImageView untuk memilih gambar dari galeri
        iv_upload?.setOnClickListener {
            val bukagaleri: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            pilih_gambar.launch(bukagaleri)
        }

        // Menetapkan aksi klik pada tombol simpan
        btn_simpan.setOnClickListener {
            val isi_nama:String = edt_nama.text.toString()    // Mendapatkan teks dari EditText nama
            val isi_id:String = edt_category.text.toString()  // Mendapatkan teks dari EditText category

            // Mengompres gambar ke dalam bentuk byte array
            val bos = ByteArrayOutputStream()
            bitmapgmabar?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val bytesarraygambar = bos.toByteArray()

            // Membuka atau membuat database SQLite dengan nama "tm"
            val dbtm: SQLiteDatabase = openOrCreateDatabase("tm", MODE_PRIVATE, null)

            // Query SQL untuk menyimpan data ke dalam tabel tb_talent
            val sql = "INSERT INTO tb_talent (NameTalent,PhotoTalent,IdCategory) VALUES (?,?,?)"
            val statement = dbtm.compileStatement(sql)

            // Mengikat nilai ke parameter SQL
            statement.clearBindings()
            statement.bindString(1, isi_nama)
            statement.bindBlob(2, bytesarraygambar)
            statement.bindString(3, isi_id)

            // Mengeksekusi query SQL untuk memasukkan data
            statement.executeInsert()

            // Pindah ke aktivitas dashboard setelah menyimpan data
            val pindah: Intent = Intent(this, dashboard::class.java)
            startActivity(pindah)
        }
    }

    // Registering untuk mendapatkan hasil dari pemilihan gambar dari galeri
    val pilih_gambar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode== Activity.RESULT_OK) {
            val gambardiperoleh = it.data

            if (gambardiperoleh!=null) {
                // Mendapatkan URI gambar yang dipilih
                urlgambar = gambardiperoleh.data

                // Mengonversi URI gambar menjadi objek Bitmap
                bitmapgmabar = MediaStore.Images.Media.getBitmap(contentResolver, urlgambar)

                // Menetapkan gambar ke ImageView
                iv_upload?.setImageBitmap(bitmapgmabar)
            }
        }
    }
}