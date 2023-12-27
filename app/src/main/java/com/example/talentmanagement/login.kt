package com.example.talentmanagement

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.jar.Attributes.Name

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //deklarasi variable
        val edt_email:EditText = findViewById(R.id.edt_email)
        val edt_password:EditText = findViewById(R.id.edt_password)
        val btn_login:Button = findViewById(R.id.btn_login)

        //aksi tombol login di tekan
        btn_login.setOnClickListener {
            val isi_email:String=edt_email.text.toString()
            val isi_password:String=edt_password.text.toString()

            //koneksi database tm
            val dbtm: SQLiteDatabase = openOrCreateDatabase("tm", MODE_PRIVATE, null)
            val query = dbtm.rawQuery("SELECT * FROM tb_admin WHERE Email='$isi_email' AND Password='$isi_password'", null)
            val cek = query.moveToNext()

            //buat tiket user
            if (cek) {
                val idAdmin = query.getString(0)
                val name = query.getString(1)
                val email = query.getString(2)
                val password = query.getString(3)

                val session:SharedPreferences = getSharedPreferences("tb_admin", MODE_PRIVATE)
                val buattiket = session.edit()
                buattiket.putString("IdAdmin", idAdmin)
                buattiket.putString("Name", name)
                buattiket.putString("Email", email)
                buattiket.putString("Password", password)
                buattiket.commit()

                val pindah: Intent = Intent (this, dashboard::class.java)
                startActivity(pindah)
            } else {
                Toast.makeText(this, "Incorrect Email or Password!", Toast.LENGTH_LONG).show()
            }
        }
    }
}