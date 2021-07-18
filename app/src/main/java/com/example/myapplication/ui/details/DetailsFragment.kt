package com.example.myapplication.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myapplication.databinding.FragmentDetailsBinding
import com.example.myapplication.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    private val mViewModel: DetailsViewModel by viewModels()
    private val mArgs: DetailsFragmentArgs by navArgs()

    override fun setupView() {
        mViewModel.mCityDescription.value = mArgs.city.mDescription
        mViewModel.mCityName.value = mArgs.city.mName
        mBinding.imageViewFragmentDetailsCityImage.load(mArgs.city.mImage)
    }

    override fun setupUiListener() {
        /* NO-OP */
    }

    override fun setupObservers() {
        /* NO-OP */
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
        lifecycleOwner = this@DetailsFragment.viewLifecycleOwner
        detailsViewModel = mViewModel
    }
}