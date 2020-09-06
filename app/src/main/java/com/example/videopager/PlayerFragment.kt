package com.example.videopager

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerFragment(private val url:String) : Fragment() {
    private val mediaPlayer = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaPlayer.apply {
            setOnPreparedListener {
                progressBarH.max = mediaPlayer.duration
                seekTo(1)
                progressBar.visibility = View.INVISIBLE
            }
            setDataSource(url)
            prepareAsync()
            progressBar.visibility = View.VISIBLE
        }
        lifecycleScope.launch {
            while (true) {
                progressBarH.progress = mediaPlayer.currentPosition
                delay(500)
            }
        }
        surfaceView.holder.addCallback(object :SurfaceHolder.Callback{
            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                mediaPlayer.setDisplay(p0)
                mediaPlayer.setScreenOnWhilePlaying(true)
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {}

            override fun surfaceCreated(p0: SurfaceHolder) {}

        })
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
        lifecycleScope.launch {
            while (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                delay(500)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }
}