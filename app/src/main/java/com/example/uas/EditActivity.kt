package com.example.uas

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uas.databinding.ActivityEditBinding
import com.example.uas.models.PlantData
import java.util.*

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data yang dikirimkan melalui Intent
        val plantId = intent.getStringExtra("plant_id")  // Menggunakan getStringExtra() untuk id sebagai String
        val tanggal = intent.getStringExtra("tanggal")
        val tinggi = intent.getStringExtra("tinggi")
        val jumlahDaun = intent.getStringExtra("jumlahDaun")
        val panjangBatang = intent.getStringExtra("panjangBatang")

        binding.editTextTanggal.setText(tanggal)
        binding.editTextTinggi.setText(tinggi)
        binding.editTextJumlahDaun.setText(jumlahDaun)
        binding.editTextPanjangBatang.setText(panjangBatang)

        binding.editTextTanggal.setOnClickListener {
            showDatePickerDialog()
        }

        binding.saveButton.setOnClickListener {
            val updatedPlant = PlantData(
                _id = intent.getParcelableExtra<PlantData>("plantData")?._id ?: "",
                tanggal = binding.editTextTanggal.text.toString(),
                tinggi = binding.editTextTinggi.text.toString(),
                jumlahDaun = binding.editTextJumlahDaun.text.toString(),
                panjangBatang = binding.editTextPanjangBatang.text.toString()
            )

            val resultIntent = Intent()
            resultIntent.putExtra("updatedPlant", updatedPlant)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Membuka DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.editTextTanggal.setText(date)  // Set tanggal yang dipilih ke EditText
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}
