package com.example.videopager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.fragment_video.*

private val videoUrls = listOf<String>(
    "http://192.168.2.100/v1",
    "http://192.168.2.100/v2",
    "http://192.168.2.100/v3",
    "http://192.168.2.100/v4",
    "http://192.168.2.100/v5",
    "http://192.168.2.100/v6",
    "http://192.168.2.100/v7",
    "http://192.168.2.100/v8",
    "http://192.168.2.100/v9"
)
class VideoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoViewPager.apply {
            adapter = object :FragmentStateAdapter(this@VideoFragment) {
                override fun getItemCount() = videoUrls.size

                override fun createFragment(position: Int) = PlayerFragment(videoUrls[position])
            }
            offscreenPageLimit = 5
        }
    }
}