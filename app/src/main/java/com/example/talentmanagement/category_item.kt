package com.example.talentmanagement

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class category_item(
    val ini: Context,
    val id: MutableList<String>,
    val nama: MutableList<String>,
    val category: MutableList<String>
) : RecyclerView.Adapter<category_item.ViewHolder>() {

    // Mengimplementasikan fungsi onCreateViewHolder yang membuat ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Membuat tampilan dari layout activity_category_item sebagai item dalam RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_category_item, parent, false)
        return ViewHolder(view)
    }

    // Mendefinisikan ViewHolder untuk menyimpan referensi elemen tampilan dalam setiap item
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_nama: TextView = itemView.findViewById(R.id.txt_nama)
        val txt_category: TextView = itemView.findViewById(R.id.txt_id)
        val btn_hapus: Button = itemView.findViewById(R.id.btn_hapus)
        val btn_ubah: Button = itemView.findViewById(R.id.btn_ubah)
    }

    // Mengembalikan jumlah item dalam daftar
    override fun getItemCount(): Int {
        return nama.size
    }

    // Mengikat data ke ViewHolder di posisi tertentu dalam RecyclerView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_nama.text = nama[position] // Mengatur teks nama dari data pada posisi tertentu
        holder.txt_category.text = category[position] // Mengatur teks kategori dari data pada posisi tertentu

        // Menambahkan fungsi onClickListener untuk tombol hapus
        holder.btn_hapus.setOnClickListener {
            val id_category_terpilih: String = id[position] // Mengambil ID kategori pada posisi tertentu
            val pindah: Intent = Intent(ini, category_hapus::class.java) // Membuat Intent untuk activity category_hapus
            pindah.putExtra("id_category_terpilih", id_category_terpilih) // Menyertakan data ID kategori yang dipilih
            ini.startActivity(pindah) // Memulai activity category_hapus dengan Intent yang sudah dibuat
        }

        // Menambahkan fungsi onClickListener untuk tombol ubah
        holder.btn_ubah.setOnClickListener {
            val id_category_terpilih: String = id[position] // Mengambil ID kategori pada posisi tertentu
            val pindah: Intent = Intent(ini, category_ubah::class.java) // Membuat Intent untuk activity category_ubah
            pindah.putExtra("id_category_terpilih", id_category_terpilih) // Menyertakan data ID kategori yang dipilih
            ini.startActivity(pindah) // Memulai activity category_ubah dengan Intent yang sudah dibuat
        }
    }
}
