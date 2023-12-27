package com.example.talentmanagement

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class manager : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_manager, container, false)

        //deklarasi variable
        val txt_nama_user: TextView = view.findViewById(R.id.txt_nama_user)
        val txt_email_user: TextView = view.findViewById(R.id.txt_email_user)
        val btn_logout: Button = view.findViewById(R.id.btn_logout)

        //Menampilkan Nama dan Email Admin Manager
        val tiket: SharedPreferences = requireActivity().getSharedPreferences("tb_admin", AppCompatActivity.MODE_PRIVATE)
        val nama_user: String? = tiket.getString("Name", null)
        txt_nama_user.text = nama_user ?: ""
        val email_user: String? = tiket.getString("Email", null)
        txt_email_user.text = email_user ?: ""

        //aksi tombol logout
        btn_logout.setOnClickListener {
            val edittiket = tiket.edit()
            edittiket.clear()
            edittiket.apply()

            val keluar = Intent(requireActivity(), login::class.java)
            startActivity(keluar)
            requireActivity().finish()
        }

        return view
    }
}