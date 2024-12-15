package com.example.uas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uas.databinding.ItemPlantBinding
import com.example.uas.models.PlantData


class PlantAdapter(
    private val plantList: List<PlantData>,
    private val onEditClick: (PlantData) -> Unit
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding = ItemPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plantList[position]
        holder.bind(plant)
    }

    override fun getItemCount(): Int = plantList.size

    inner class PlantViewHolder(private val binding: ItemPlantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plant: PlantData) {
            binding.apply {
                tanggal.text = plant.tanggal
                tinggi.text = plant.tinggi
                jumlahDaun.text = plant.jumlahDaun
                panjangBatang.text = plant.panjangBatang
                editButton.setOnClickListener {
                    onEditClick(plant)
                }
            }
        }
    }
}
