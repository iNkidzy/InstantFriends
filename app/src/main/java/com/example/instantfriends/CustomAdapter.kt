package com.example.instantfriends

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instantfriends.data.BEPost
import java.io.File

class CustomAdapter(var context: Context, var posts: List<BEPost>):
    RecyclerView.Adapter<CustomAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_description)
        val imageView: ImageView = view.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = posts[position]
        holder.textView.text =  item.description
        holder.imageView.setImageURI(Uri.fromFile( File(item.photoPath)))
    }

    override fun getItemCount(): Int {
        return posts.size
    }


}