package com.example.newstoday

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(val context: Context, val newsList: List<Articals>): RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
//        val view = inflater.inflate(R.layout.news_item_view,parent,false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val artical = newsList[position]
        holder.newsTitle.text = artical.title
        holder.newsDescription.text = artical.description
        val image = artical.urlToImage
        if(image != null) {
            Glide.with(context)
                .load(artical.urlToImage)
                .into(holder.newsImagee)
        } else {
            Glide.with(context)
                .load(R.drawable.newsmg)
                .into(holder.newsImagee)
        }

        holder.itemView.setOnClickListener {
            Toast.makeText(context, artical.title, Toast.LENGTH_SHORT).show()
            val url = artical.url
            val builder =  CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()

            customTabsIntent.launchUrl(context, Uri.parse(url));

        }

    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsTitle = itemView.findViewById<TextView>(R.id.title)
        var newsDescription = itemView.findViewById<TextView>(R.id.description)
        var newsImagee = itemView.findViewById<ImageView>(R.id.newsImage)
    }

}


