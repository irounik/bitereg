package com.project.bitereg.view.dashboard.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.bitereg.R
import com.project.bitereg.adapters.IssuesAdapter
import com.project.bitereg.databinding.FragmentHomeBinding
import com.project.bitereg.models.Issue
import com.project.bitereg.utils.NavBarController
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

    override fun onResume() {
        super.onResume()
        (requireActivity() as NavBarController).showNavBar()
        loadData()
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
            (requireActivity() as NavBarController).hideNavBar()
            findNavController().navigate(R.id.action_homeFragment_to_reportIssueFragment)
        }

        binding.root.setOnRefreshListener {
            loadData()
        }
    }

    private fun intListeners() = binding.run {
        viewModel.issues.observe(viewLifecycleOwner) { handleIssues(it) }
    }

    private fun handleIssues(issues: List<Issue>) = binding.run {
        (issuesRv.adapter as? IssuesAdapter)?.updateList(issues)
        circularProgressbar.visibility = View.GONE
        root.isRefreshing = false
        if (issues.isNotEmpty()) {
            yourIssuesText.visibility = View.VISIBLE
        } else {
            noIssuesImg.visibility = View.VISIBLE
            noIssuesTest.visibility = View.VISIBLE
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