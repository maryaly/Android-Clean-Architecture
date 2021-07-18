package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.data.model.City
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.extensions.showErrorAlertDialog
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.adapter.MainAdapter
import com.example.myapplication.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    @Inject
    lateinit var mAdapter: MainAdapter
    private val mViewModel: MainViewModel by viewModels()

    override fun setupView() {
        mBinding.swipeRefreshMainFragment.setOnRefreshListener {
            mViewModel.isSwipeRefresh.value = Event(true)
            mViewModel.getCitiesAndFoods()
        }

        mViewModel.getCitiesAndFoods()
    }

    private fun setupRecyclerView() {
        mBinding.recyclerMainFragment.adapter = mAdapter.apply {
            mOnItemCityClicked = { city ->
                navigateToDetailFragment(city)
            }
        }
    }

    override fun setupUiListener() {
        /* NO-OP */
    }

    override fun setupObservers() {
        mViewModel.mGetCitiesAndFoods.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Result.Status.LOADING -> { /* NO-OP */
                    }
                    Result.Status.SUCCESS -> {
                        mBinding.swipeRefreshMainFragment.isRefreshing = false

                        result.data?.let { list ->
                            mAdapter.differ.submitList(list)
                            setupRecyclerView()
                        }
                    }
                    Result.Status.ERROR -> {
                        result.message?.let { showErrorAlertDialog(it) }
                    }
                }
            }
        })
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false).apply {
        lifecycleOwner = this@MainFragment.viewLifecycleOwner
        mainViewModel = mViewModel
    }

    private fun navigateToDetailFragment(city: City) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(city)
        findNavController().navigate(action)
    }
}