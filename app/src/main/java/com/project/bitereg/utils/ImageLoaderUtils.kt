package com.project.bitereg.utils

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*

object ImageLoaderUtils {

    private val imagesRef by lazy { FirebaseStorage.getInstance().reference }

    fun loadFromFirebase(imageView: ImageView, firebasePath: String) {
        val file = File.createTempFile("img", "${UUID.randomUUID()}")

        FirebaseStorage.getInstance()
            .getReference(firebasePath).getFile(
                file
            ).addOnSuccessListener {
                Glide.with(imageView).load(Uri.fromFile(file))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.drawable.ic_outline_image_24)
                    .into(imageView)
            }.addOnFailureListener {
                it.printStackTrace()
                Log.e("ERROR AAYA", "bindEventData: $it")
            }
    }


}