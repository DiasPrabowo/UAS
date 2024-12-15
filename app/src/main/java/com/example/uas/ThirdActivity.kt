package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uas.databinding.ActivityThirdBinding
import com.example.uas.models.PlantData
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private val plantList = mutableListOf<PlantData>()
    private lateinit var adapter: PlantAdapter

    companion object {
        const val EDIT_REQUEST_CODE = 100 // Request code untuk EditActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        adapter = PlantAdapter(plantList) { plant ->
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("plantData", plant) // Kirim data ke EditActivity
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Button Tambah Data
        binding.btnTambah.setOnClickListener {
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
        }

        // Load data dari API
        loadDataFromApi()
    }

    private fun loadDataFromApi() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://ppapb-a-api.vercel.app/JgfzY/UrbanPonic")
            .get()
            .build()

        Thread {
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val jsonArray = JSONArray(response.body?.string())
                    plantList.clear() // Hapus data lama
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val plant = PlantData(
                            _id = jsonObject.getString("_id"),
                            tanggal = jsonObject.getString("tanggal"),
                            tinggi = jsonObject.getString("tinggi"),
                            jumlahDaun = jsonObject.getString("jumlahDaun"),
                            panjangBatang = jsonObject.getString("panjangBatang")
                        )
                        plantList.add(plant)
                    }
                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this, "Gagal memuat data!", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            val updatedPlant = data?.getParcelableExtra<PlantData>("updatedPlant")
            updatedPlant?.let { plant ->
                updatePlantInList(plant)
            }
        }
    }

    private fun updatePlantInList(updatedPlant: PlantData) {
        val index = plantList.indexOfFirst { it._id == updatedPlant._id }
        if (index != -1) {
            plantList[index] = updatedPlant // Perbarui data di list
            adapter.notifyItemChanged(index) // Beritahu adapter bahwa data telah berubah
            Toast.makeText(this, "Data berhasil diperbarui!", Toast.LENGTH_SHORT).show()
        }
    }
}
