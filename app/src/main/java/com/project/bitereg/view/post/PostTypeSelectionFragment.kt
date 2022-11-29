package com.project.bitereg.view.post

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.project.bitereg.R
import com.project.bitereg.databinding.FragmentPostTypeSelectionBinding
import com.project.bitereg.view.base.BaseFragment
import com.project.bitereg.view.base.Inflate

class PostTypeSelectionFragment : BaseFragment<FragmentPostTypeSelectionBinding>() {

    override fun inflate(): Inflate<FragmentPostTypeSelectionBinding> {
        return FragmentPostTypeSelectionBinding::inflate
    }

    override fun onViewCreated(
        binding: FragmentPostTypeSelectionBinding,
        savedInstanceState: Bundle?
    ) {
        initViews(binding)
    }

    private fun initViews(binding: FragmentPostTypeSelectionBinding) = with(binding) {
        choiceNotice.setOnClickListener {
            findNavController().navigate(
                R.id.action_postTypeSelectionFragment_to_createNoticeFragment
            )
        }

        choiceEvent.setOnClickListener {
            findNavController().navigate(
                R.id.action_postTypeSelectionFragment_to_createEventFragment
            )
        }

        choiceQuote.setOnClickListener {
            findNavController().navigate(
                R.id.action_postTypeSelectionFragment_to_createQuoteFragment
            )
        }

        choiceJobIntern.setOnClickListener {
            findNavController().navigate(
                R.id.action_postTypeSelectionFragment_to_createJobFragment
            )
        }

        backBtn.setOnClickListener {
            requireActivity().finish()
        }

    }

}