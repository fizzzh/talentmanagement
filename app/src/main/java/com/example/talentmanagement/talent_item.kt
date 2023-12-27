package com.example.talentmanagement

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class talent_item(
    val ini: Context,
    val id: MutableList<String>,
    val nama: MutableList<String>,
    val nomor: MutableList<String>,
    val category: MutableList<String>,
    val foto: MutableList<Bitmap>
) : RecyclerView.Adapter<talent_item.ViewHolder>() {
    // Method untuk membuat ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Membuat view baru dengan menggunakan layout 'activity_talent_item'
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_talent_item, parent, false)
        return ViewHolder(view) // Mengembalikan ViewHolder yang berisi view baru
    }

    // Inner class ViewHolder untuk menyimpan referensi elemen UI
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_nama: TextView = itemView.findViewById(R.id.txt_nama) // TextView untuk nama
        val txt_nomor: TextView = itemView.findViewById(R.id.txt_nomor) //TextView untuk nomor
        val txt_category: TextView = itemView.findViewById(R.id.txt_id) // TextView untuk kategori
        val iv_foto: ImageView = itemView.findViewById(R.id.iv_foto) // ImageView untuk foto
        val btn_hapus: Button = itemView.findViewById(R.id.btn_hapus) // Button untuk hapus
        val btn_ubah: Button = itemView.findViewById(R.id.btn_ubah) // Button untuk ubah
    }

    // Mengembalikan jumlah item dalam daftar
    override fun getItemCount(): Int {
        return nama.size
    }

    // Mengikat data ke ViewHolder di posisi tertentu
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_nama.text = nama[position] // Set teks nama
        holder.txt_nomor.text = nomor[position] // Set teks nomor
        holder.txt_category.text = category[position] // Set teks kategori
        holder.iv_foto.setImageBitmap(foto[position]) // Set gambar menggunakan Bitmap
        holder.btn_hapus.setOnClickListener {
            // Ketika tombol hapus ditekan, membuat Intent untuk menuju talent_hapus
            val id_talent_terpilih: String = id[position]
            val pindah: Intent = Intent(ini, talent_hapus::class.java)
            pindah.putExtra("id_talent_terpilih", id_talent_terpilih)
            ini.startActivity(pindah) // Memulai aktivitas talent_hapus dengan data yang diteruskan
        }
        holder.btn_ubah.setOnClickListener {
            // Ketika tombol ubah ditekan, membuat Intent untuk menuju talent_ubah
            val id_talent_terpilih: String = id[position]
            val pindah: Intent = Intent(ini, talent_ubah::class.java)
            pindah.putExtra("id_talent_terpilih", id_talent_terpilih)
            ini.startActivity(pindah) // Memulai aktivitas talent_ubah dengan data yang diteruskan
        }
    }
}