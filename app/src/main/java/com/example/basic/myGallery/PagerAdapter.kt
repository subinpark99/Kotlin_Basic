package com.example.basic.myGallery

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var uris= mutableListOf<Uri>()

    override fun getItemCount(): Int {
       return uris.size
    }

    override fun createFragment(position: Int): Fragment {
        return AddPhotoFragment.newInstance(uris[position])
    }
}