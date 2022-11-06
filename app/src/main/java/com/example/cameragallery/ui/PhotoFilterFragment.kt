package com.example.cameragallery.ui

import com.example.cameragallery.Photo
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.cameragallery.R
import com.example.cameragallery.databinding.FragmentPhotoFilterBinding


class PhotoFilterFragment : Fragment() {
    private var _binding : FragmentPhotoFilterBinding? = null
    private val binding get() = _binding
    private var photo: Photo? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        arguments?.let {
            val safeArgs = PhotoFilterFragmentArgs.fromBundle(it)
            photo = safeArgs.photo
        }
        setHasOptionsMenu(true)
        loadImage(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.filters_array,
            android.R.layout.simple_spinner_item,
        ).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.filterSpinner.adapter = adapter
        }
    }

    private fun loadImage(glideFilter: Transformation<Bitmap>?) {
        when {
            photo != null && glideFilter != null -> {
                Glide.with(this)
                    .load(photo!!.uri)
                    .transform(CenterCrop(), glideFilter)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding!!.selectedImage)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}