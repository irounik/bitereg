package com.project.bitereg.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.bitereg.adapters.IssuesAdapter
import com.project.bitereg.databinding.FragmentHomeBinding
import com.project.bitereg.models.Issue
import com.project.bitereg.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        intListeners()
        loadData()
    }

    private fun initViews() {
        binding.issuesRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = IssuesAdapter(issues = mutableListOf(), onIssueClicked = ::onIssueSelected)
        }

        binding.addIssueFab.setOnClickListener {
            // TODO: Add new issues
        }
    }

    private fun intListeners() {
        viewModel.issues.observe(viewLifecycleOwner) { issues ->
            (binding.issuesRv.adapter as? IssuesAdapter)?.updateList(issues)
            binding.circularProgressbar.visibility = View.GONE
        }
    }

    private fun loadData() {
        binding.circularProgressbar.visibility = View.VISIBLE
        viewModel.fetchIssues()
    }

    private fun onIssueSelected(issue: Issue) {
        Toast.makeText(context, issue.title, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}