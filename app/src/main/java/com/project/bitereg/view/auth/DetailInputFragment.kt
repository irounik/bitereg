package com.project.bitereg.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.project.bitereg.databinding.FragmentDetailInputBinding
import com.project.bitereg.utils.SelectionBottomSheet

class DetailInputFragment : Fragment() {

    private var _binding: FragmentDetailInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailInputBinding.inflate(layoutInflater, container, false)
        initLayout()
        return binding.root
    }

    private fun initLayout() {
    }
}