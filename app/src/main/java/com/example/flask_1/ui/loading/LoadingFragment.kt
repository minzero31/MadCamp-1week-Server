package com.example.navigation.ui.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flask_1.R
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

class LoadingFragment : Fragment() {

    private lateinit var callback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This callback will handle the back button press
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing to disable back button
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_loading, container, false)

        // Use GifDrawable to load GIF
        val gifImageView = root.findViewById<GifImageView>(R.id.gif_image_view)
        val gifDrawable = GifDrawable(resources, R.drawable.ic_loading_leaves)

        // Adjust GIF speed by modifying the loop count and frame duration
        gifDrawable.setSpeed(2.0f) // Speed up the GIF by 2 times

        gifImageView.setImageDrawable(gifDrawable)

        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onPause() {
        super.onPause()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback.remove()
    }
}

