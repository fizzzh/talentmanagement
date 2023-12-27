package com.example.talentmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView

class dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        //deklarasi image button fragment
        val btn_category: ImageButton = findViewById(R.id.btn_category)
        val btn_talent: ImageButton = findViewById(R.id.btn_talent)
        val btn_brief: ImageButton = findViewById(R.id.btn_brief)
        val btn_account: ImageButton = findViewById(R.id.btn_account)
        val fc_konten: FragmentContainerView = findViewById(R.id.fc_konten)

        //otomatis membuka halaman category
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(fc_konten.id, manager())
        ft.commit()

        //tombol image view fragment agar bisa di klik
        btn_category.setOnClickListener() {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            ft.replace(fc_konten.id, category())
            ft.commit()
        }
        btn_talent.setOnClickListener() {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            ft.replace(fc_konten.id, talent())
            ft.commit()
        }
        btn_brief.setOnClickListener() {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            ft.replace(fc_konten.id, brief())
            ft.commit()
        }
        btn_account.setOnClickListener() {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            ft.replace(fc_konten.id, manager())
            ft.commit()
        }

    }
}