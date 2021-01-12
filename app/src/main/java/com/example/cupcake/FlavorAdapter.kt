package com.example.cupcake

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.AccessibilityDelegate
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cupcake.model.Flavor
import com.example.cupcake.model.OrderViewModel

class FlavorAdapter(private val context: Context, private val sharedViewModel: OrderViewModel) : RecyclerView.Adapter<FlavorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.summary_flavor, parent, false)

        layout.accessibilityDelegate = AccessibilityDelegate()

        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flavor = sharedViewModel.flavors.value?.get(position)
        holder.textView.text = flavor?.name?.let { context.getString(it) }
    }

    override fun getItemCount(): Int {
        return sharedViewModel.flavors.value?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById<Button>(R.id.flavor)
    }

}