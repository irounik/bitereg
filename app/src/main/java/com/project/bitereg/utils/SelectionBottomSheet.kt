package com.project.bitereg.utils

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.bitereg.R
import com.project.bitereg.adapters.SelectionListAdapter
import com.project.bitereg.databinding.SelectionSheetLayoutBinding

class SelectionBottomSheet : BottomSheetDialogFragment() {

    private var _binding: SelectionSheetLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemList: List<String>
    private lateinit var onItemSelected: (String) -> Unit

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SelectionSheetLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            selectionItemsList.apply {
                layoutManager = LinearLayoutManager(view.context)
                adapter = SelectionListAdapter(itemList) {
                    onItemSelected(it)
                    dismissAllowingStateLoss()
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun getInstance(
            list: List<String>,
            onItemSelected: (String) -> Unit
        ): SelectionBottomSheet {
            synchronized(lock = list) {
                return SelectionBottomSheet().apply {
                    this.itemList = list
                    this.onItemSelected = onItemSelected
                }
            }
        }
    }
}