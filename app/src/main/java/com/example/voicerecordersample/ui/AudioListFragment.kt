package com.example.voicerecordersample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voicerecordersample.R
import com.example.voicerecordersample.databinding.FragmentAudioListBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import java.io.File

class AudioListFragment : Fragment() {

    private lateinit var binding: FragmentAudioListBinding
    private lateinit var audioListAdapter: AudioListRecyclerViewAdapter

    private var allFiles: Array<File> = emptyArray()

    companion object {
        fun newInstance() = AudioListFragment()
    }

    private lateinit var viewModel: AudioListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_audio_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AudioListViewModel::class.java)

        var path: String = requireActivity().getExternalFilesDir("/")!!.absolutePath
        var directory: File = File(path)
        allFiles = directory.listFiles();

        audioListAdapter = AudioListRecyclerViewAdapter(allFiles)
        binding.audioListView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = audioListAdapter
        }


        BottomSheetBehavior.from(binding.audioPlayerSheet).apply {
            this.addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        this@apply.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // not use in this app
                }
            })
        }
    }

}