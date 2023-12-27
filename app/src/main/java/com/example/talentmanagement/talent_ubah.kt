package com.example.talentmanagement

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class talent_ubah : AppCompatActivity() {

    // Variabel-variabel yang digunakan dalam kelas
    var urlgambar: Uri?=null  // Menyimpan URI dari gambar yang dipilih
    var bitmapgmabar: Bitmap?=null  // Menyimpan gambar dalam bentuk bitmap
    var iv_upload: ImageView?=null  // ImageView untuk menampilkan gambar

    // Metode onCreate dipanggil ketika aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengatur tata letak aktivitas dengan menggunakan layout dari file XML activity_talent_ubah
        setContentView(R.layout.activity_talent_ubah)

        // Mendapatkan ID bakat yang dipilih dari intent
        val id_talent_terpilih:String = intent.getStringExtra("id_talent_terpilih").toString()

        // Membuka atau membuat database SQLite dengan nama "tm"
        val dbtm: SQLiteDatabase = openOrCreateDatabase("tm", MODE_PRIVATE, null)
        // Mengambil data bakat dari database berdasarkan ID bakat yang dipilih
        val ambil = dbtm.rawQuery("SELECT * FROM tb_talent WHERE IdTalent = '$id_talent_terpilih'", null)
        ambil.moveToNext()

        // Mendapatkan data dari hasil query
        val isi_nama:String = ambil.getString(1)
        val isi_foto:ByteArray = ambil.getBlob(2)
        val isi_talent:String = ambil.getString(3)

        // Mendapatkan referensi ke elemen-elemen UI
        val edt_category: EditText = findViewById(R.id.edt_judul)
        val edt_nama: EditText = findViewById(R.id.edt_untuk)
        val btn_simpan: Button = findViewById(R.id.btn_simpan)
        iv_upload = findViewById(R.id.iv_upload)

        // Menetapkan nilai awal untuk EditText dan menangani klik pada ImageView untuk memilih gambar
        iv_upload?.setOnClickListener {
            val bukagaleri: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            pilih_gambar.launch(bukagaleri)
        }

        // Menetapkan nilai dari database ke EditText dan menampilkan gambar
        edt_category.setText(isi_talent)
        edt_nama.setText(isi_nama)
        try {
            val bis = ByteArrayInputStream(isi_foto)
            val gambarbitmap: Bitmap = BitmapFactory.decodeStream(bis)
            iv_upload?.setImageBitmap(gambarbitmap)
        } catch (e: Exception) {
            val gambarbitmap: Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.noimage)
            iv_upload?.setImageBitmap(gambarbitmap)
        }

        // Menangani klik pada tombol simpan
        btn_simpan.setOnClickListener {
            // Mendapatkan nilai dari EditText
            val nama_baru: String = edt_nama.text.toString()
            val category_baru: String = edt_category.text.toString()

            // Mengompresi gambar ke dalam byte array
            val bos = ByteArrayOutputStream()
            bitmapgmabar?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val bytesarraygambar = bos.toByteArray()

            // Query SQL untuk memperbarui data bakat di database
            val sql = "UPDATE tb_talent SET NameTalent=?, IdCategory=?, PhotoTalent=? WHERE IdTalent ='$id_talent_terpilih'"
            val statement = dbtm.compileStatement(sql)
            statement.clearBindings()
            statement.bindString(1, nama_baru)
            statement.bindString(2, category_baru)

            // Cek apakah gambar baru diubah
            if (bitmapgmabar != null) {
                statement.bindBlob(3, bytesarraygambar)
            } else {
                // Jika tidak ada perubahan gambar, ikuti penomoran bind yang sesuai
                val isi_foto: ByteArray = ambil.getBlob(2)
                statement.bindBlob(3, isi_foto)
            }

            // Eksekusi query update
            statement.executeUpdateDelete()

            // Pindah ke aktivitas dashboard setelah menyimpan perubahan
            val pindah: Intent = Intent(this, dashboard::class.java)
            startActivity(pindah)
        }
    }

    // Mendeklarasikan variabel pilih_gambar untuk mengatur aksi pemilihan gambar
    val pilih_gambar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode== Activity.RESULT_OK) {
            val gambardiperoleh = it.data

            if (gambardiperoleh!=null) {
                // Mendapatkan URI gambar yang dipilih
                urlgambar = gambardiperoleh.data

                // Mengonversi URI gambar menjadi bitmap
                bitmapgmabar = MediaStore.Images.Media.getBitmap(contentResolver, urlgambar)
                // Menampilkan gambar di ImageView
                iv_upload?.setImageBitmap(bitmapgmabar)
            }
        }
    }
}