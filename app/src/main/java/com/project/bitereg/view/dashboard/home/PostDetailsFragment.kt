package com.project.bitereg.view.dashboard.home

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.project.bitereg.databinding.FragmentPostDetailsBinding
import com.project.bitereg.models.Event
import com.project.bitereg.utils.ImageLoaderUtils
import com.project.bitereg.utils.NavBarController
import com.project.bitereg.view.base.BaseFragment
import com.project.bitereg.view.base.Inflate

class PostDetailsFragment : BaseFragment<FragmentPostDetailsBinding>() {
    override fun inflate(): Inflate<FragmentPostDetailsBinding> {
        return FragmentPostDetailsBinding::inflate
    }

    private val args by navArgs<PostDetailsFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? NavBarController)?.hideNavBar()
    }

    override fun onViewCreated(binding: FragmentPostDetailsBinding, savedInstanceState: Bundle?) {
        initViews(binding)
    }

    private fun initViews(binding: FragmentPostDetailsBinding) = with(binding) {
        when (args.post) {
            is Event -> {
                (args.post as Event).imageUrl.let {
                    ImageLoaderUtils.loadFromFirebase(photoView, it)
                }
            }
            else -> Unit
        }
    }

    override fun onDestroyView() {
        (context as? NavBarController)?.showNavBar()
        super.onDestroyView()
    }
}