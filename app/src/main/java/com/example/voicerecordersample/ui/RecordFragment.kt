package com.example.voicerecordersample.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.voicerecordersample.R
import com.example.voicerecordersample.databinding.FragmentRecordBinding

class RecordFragment : Fragment() {

    companion object {
        fun newInstance() = RecordFragment()
    }

    private lateinit var binding: FragmentRecordBinding
    private lateinit var viewModel: RecordViewModel
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_record, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecordViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        binding.audioListBtn.setOnClickListener {
            navController.navigate(R.id.action_recordFragment_to_audioLIstFragment)
        }

        binding.recordBtn.setOnClickListener {
            if (viewModel.isRecording) {
                // stop recording
                binding.recordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_mic_48, null))
                binding.recordBtn.background = resources.getDrawable(R.drawable.bg_record_ready, null)
                viewModel.toggleIsRecording()
            } else {
                // start recording
                binding.recordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_mic_off_48, null))
                binding.recordBtn.background = resources.getDrawable(R.drawable.bg_record_running, null)
                viewModel.toggleIsRecording()
            }
        }
    }
}