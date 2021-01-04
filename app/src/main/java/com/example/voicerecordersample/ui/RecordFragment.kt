package com.example.voicerecordersample.ui

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.voicerecordersample.R
import com.example.voicerecordersample.databinding.FragmentRecordBinding
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val LOG_TAG = "Record Fragment"
private const val RECORD_PERMISSION: String = Manifest.permission.RECORD_AUDIO
private const val PERMISSION_CODE: Int = 200

class RecordFragment : Fragment() {

    companion object {
        fun newInstance() = RecordFragment()
    }

    private lateinit var binding: FragmentRecordBinding
    private lateinit var viewModel: RecordViewModel
    private lateinit var navController: NavController

    private var mediaRecorder: MediaRecorder? = null
    private var recordingFileName: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_record, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecordViewModel::class.java)
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
                stopRecording()

                binding.recordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_mic_48, null))
                binding.recordBtn.background = resources.getDrawable(R.drawable.bg_record_ready, null)
                viewModel.toggleIsRecording()
            } else {
                // start recording
                if (checkPermissions()) {
                    startRecording()

                    binding.recordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_mic_off_48, null))
                    binding.recordBtn.background = resources.getDrawable(R.drawable.bg_record_running, null)
                    viewModel.toggleIsRecording()
                }
            }
        }
    }

    private fun stopRecording() {
        binding.recordTimer.stop()

        binding.recordFilename.text = "Recording Stopped, File Name : " + recordingFileName

        mediaRecorder?.apply {
            stop()
            reset()
            release()
        }
        mediaRecorder = null
    }

    private fun startRecording() {
        binding.recordTimer.base = SystemClock.elapsedRealtime()
        binding.recordTimer.start()

        var newRecordFilePath = requireActivity().getExternalFilesDir("/")!!.absolutePath
        var formatter = SimpleDateFormat("yyyy_MM_dd__hh_mm_ss", Locale.JAPAN)
        var now = Date()
        recordingFileName = "Recording_" + formatter.format(now) + ".3gp";

        binding.recordFilename.text = "Recording, File Name : " + recordingFileName

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile("$newRecordFilePath/$recordingFileName")
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }

            start()
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(requireContext(), RECORD_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(RECORD_PERMISSION), PERMISSION_CODE)
            return false
        }
    }
}