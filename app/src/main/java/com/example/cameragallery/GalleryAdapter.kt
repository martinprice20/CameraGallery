package com.example.cameragallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cameragallery.ui.gallery.GalleryFragment
import com.example.cameragallery.ui.gallery.GalleryFragmentDirections

class GalleryAdapter(private val activity: MainActivity, private val fragment: GalleryFragment) :
RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var photos = listOf<Photo>()

    inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var mImage = itemView.findViewById<View>(R.id.image) as ImageView
        init {
            itemView.isClickable = true
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener {
                fragment.showPopup(it, photos[layoutPosition])
                return@setOnLongClickListener true
            }
        }
        override fun onClick(v: View?) {
            val action = GalleryFragmentDirections.actionPhotoFilter(photos[layoutPosition])
            v!!.findNavController().navigate(action)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GalleryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_preview, parent, false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as GalleryViewHolder
        val current = photos[position]

        Glide.with(activity)
            .load(current.uri)
            .centerCrop()
            .into(holder.mImage)
    }

    override fun getItemCount(): Int {
        return photos.size
    }
}