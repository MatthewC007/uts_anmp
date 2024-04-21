package com.anmp.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.anmp.myapplication.databinding.LayoutItemBinding
import com.anmp.myapplication.model.News
import com.squareup.picasso.Picasso

class NewsListAdapter (private var newsList:ArrayList<News>) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListAdapter.ViewHolder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsListAdapter.ViewHolder, position: Int) {
        with(holder){
            var item=newsList[position]

            binding.txtDeskripsi.text=item.synopsis
            binding.txtJudul.text=item.title
            binding.txtPenulis.text=item.username
            Picasso.get()
                .load(item.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(binding.imageView)

            binding.btnRead.setOnClickListener{
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(item,item.title.toString())
                Navigation.findNavController(it).navigate(action)
            }


        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }




}