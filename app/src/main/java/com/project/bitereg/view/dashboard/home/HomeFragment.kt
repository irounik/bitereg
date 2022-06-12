package com.project.bitereg.view.dashboard.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.bitereg.R
import com.project.bitereg.adapters.IssuesAdapter
import com.project.bitereg.databinding.FragmentHomeBinding
import com.project.bitereg.models.Issue
import com.project.bitereg.utils.NavBarController
import com.project.bitereg.view.base.BaseFragment
import com.project.bitereg.view.base.Inflate
import com.project.bitereg.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    override fun inflate(): Inflate<FragmentHomeBinding> = FragmentHomeBinding::inflate

    override fun onViewCreated(binding: FragmentHomeBinding, savedInstanceState: Bundle?) {
        initViews()
        intListeners()
        loadData()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as NavBarController).showNavBar()
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

}