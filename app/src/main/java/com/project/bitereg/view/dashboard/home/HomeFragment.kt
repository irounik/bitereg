package com.project.bitereg.view.dashboard.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.bitereg.R
import com.project.bitereg.adapters.FeedAdapter
import com.project.bitereg.databinding.FragmentHomeBinding
import com.project.bitereg.models.Post
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
        binding.feedRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = FeedAdapter(posts = mutableListOf(), onPostClicked = ::onPostSelected)
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
        viewModel.posts.observe(viewLifecycleOwner) { handleFeed(it) }
    }

    private fun handleFeed(posts: List<Post>) = binding.run {
        (feedRv.adapter as? FeedAdapter)?.updateList(posts)
        circularProgressbar.visibility = View.GONE
        root.isRefreshing = false
        if (posts.isNotEmpty()) {
//            yourIssuesText.visibility = View.VISIBLE
        } else {
            noIssuesImg.visibility = View.VISIBLE
            noIssuesTest.visibility = View.VISIBLE
        }
    }

    private fun loadData() {
        binding.circularProgressbar.visibility = View.VISIBLE
        viewModel.fetchPosts()
    }

    private fun onPostSelected(post: Post) {
        Toast.makeText(context, post.title, Toast.LENGTH_SHORT).show()
    }

}