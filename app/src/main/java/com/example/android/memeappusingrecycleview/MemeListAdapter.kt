package com.example.android.memeappusingrecycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MemeListAdapter(private val listener:MemeItemCLicked): RecyclerView.Adapter<MemeViewHolder>() {

    private val items:ArrayList<Meme> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.meme_image,parent,false)
        val viewHolder = MemeViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        Glide.with(holder.itemView.context).load(currentItem.url).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateMemes(updatedItems:ArrayList<Meme>){
        items.clear()
        items.addAll(updatedItems)

        notifyDataSetChanged()
    }

}

class MemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView : TextView = itemView.findViewById(R.id.title)
    val imageView : ImageView = itemView.findViewById(R.id.image)

}

interface MemeItemCLicked{
    fun onItemClicked(item : Meme)
}
