package com.example.uas

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uas.databinding.ActivityAddDataBinding
import com.example.uas.models.PlantData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

class AddDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menambahkan DatePickerDialog pada EditText
        binding.editTextTanggal.setOnClickListener {
            // Mendapatkan tanggal saat ini
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Menampilkan DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                    // Format tanggal yang dipilih
                    val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.editTextTanggal.setText(formattedDate)  // Set tanggal ke EditText
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        // Menyimpan data ke API saat tombol save ditekan
        binding.saveButton.setOnClickListener {
            val tanggal = binding.editTextTanggal.text.toString()
            val tinggi = binding.editTextTinggi.text.toString()
            val jumlahDaun = binding.editTextJumlahDaun.text.toString()
            val panjangBatang = binding.editTextPanjangBatang.text.toString()

            if (tanggal.isNotEmpty() && tinggi.isNotEmpty() && jumlahDaun.isNotEmpty() && panjangBatang.isNotEmpty()) {
                    val newPlant = PlantData(
                        _id = System.currentTimeMillis().toString(),
                        tanggal = tanggal,
                        tinggi = tinggi,
                        jumlahDaun = jumlahDaun,
                        panjangBatang = panjangBatang
                    )

                // Post data ke API
                postDataToApi(newPlant)
            } else {
                Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun postDataToApi(plantData: PlantData) {
        val client = OkHttpClient()
        val jsonObject = JSONObject().apply {
            put("_id", plantData._id)
            put("tanggal", plantData.tanggal)
            put("tinggi", plantData.tinggi)
            put("jumlahDaun", plantData.jumlahDaun)
            put("panjangBatang", plantData.panjangBatang)
        }

        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            jsonObject.toString()
        )

        val request = Request.Builder()
            .url("https://ppapb-a-api.vercel.app/JgfzY/UrbanPonic")
            .post(requestBody)
            .build()

        Thread {
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this, "Gagal menambahkan data!${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }
}
