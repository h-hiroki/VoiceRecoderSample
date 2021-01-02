package com.example.voicerecordersample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.voicerecordersample.R

class AudioListFragment : Fragment() {

    companion object {
        fun newInstance() = AudioListFragment()
    }

    private lateinit var viewModel: AudioListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_audio_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AudioListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}