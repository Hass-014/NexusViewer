package com.example.nexusviewer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntityAdapter(
    private val data: List<Map<String, String>>
) : RecyclerView.Adapter<EntityAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView =
            itemView.findViewById(R.id.entitySummaryText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_entity, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        val species = item["species"] ?: ""
        val scientific = item["scientificName"] ?: ""
        val habitat = item["habitat"] ?: ""
        val diet = item["diet"] ?: ""
        val status = item["conservationStatus"] ?: ""
        val lifespan = item["averageLifespan"] ?: ""
        val description = item["description"] ?: ""

        val summary =
            """
            $species
            
            Scientific Name:
            $scientific
            
            Habitat:
            $habitat
            
            Diet:
            $diet
            """.trimIndent()

        val details =
            """
            Species:
            $species
            
            Scientific Name:
            $scientific
            
            Habitat:
            $habitat
            
            Diet:
            $diet
            
            Conservation Status:
            $status
            
            Average Lifespan:
            $lifespan years
            
            Description:
            $description
            """.trimIndent()

        holder.text.text = summary

        holder.itemView.setOnClickListener {
            val intent =
                Intent(
                    holder.itemView.context,
                    DetailsActivity::class.java
                )

            intent.putExtra("DETAILS", details)

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}