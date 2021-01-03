package com.example.voicerecordersample.ui

import androidx.lifecycle.ViewModel

class RecordViewModel : ViewModel() {

    var isRecording = false

    fun toggleIsRecording() {
        isRecording = !isRecording
    }
}