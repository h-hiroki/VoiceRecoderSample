package com.example.voicerecordersample.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.voicerecordersample.R
import com.example.voicerecordersample.databinding.AudioListItemBinding
import java.io.File

class AudioListRecyclerViewAdapter(private val allFiles: Array<File>)
    : RecyclerView.Adapter<AudioListRecyclerViewAdapter.AudioListItemViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioListItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AudioListItemViewHolder(DataBindingUtil.inflate<AudioListItemBinding>(layoutInflater,
                R.layout.audio_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: AudioListItemViewHolder, position: Int) {
        holder.bindTo(allFiles[position])
    }

    override fun getItemCount(): Int {
        return allFiles.size
    }


    class AudioListItemViewHolder(val binding: AudioListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(file: File) {
            binding.listFileName.text = file.name
            binding.listDate.text = file.lastModified().toString()
        }
    }
}