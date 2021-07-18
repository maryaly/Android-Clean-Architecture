package com.example.myapplication.ui.main.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.example.myapplication.R
import com.example.myapplication.data.model.City
import com.example.myapplication.data.model.CustomObject
import com.example.myapplication.databinding.ItemCityBinding

class CityViewHolder(
    private val mBinding: ItemCityBinding,
    private val mOnItemCityClicked: ((City) -> Unit)?,
) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(currentItem: CustomObject) {
        val currentCity = currentItem.foodOrCityOrTitle as City

        mBinding.root.setOnClickListener {
            mOnItemCityClicked?.let { onClick -> onClick(currentCity) }
        }

        mBinding.imageViewItemCity.load(currentCity.mImage) {
            memoryCachePolicy(CachePolicy.ENABLED)
            crossfade(true)
        }

        mBinding.textViewItemCityCityName.text = currentCity.mName

        mBinding.textViewItemCityCityDescription.text = currentCity.mDescription
    }

    companion object {
        fun create(
            parent: ViewGroup,
            mOnItemCityClicked: ((City) -> Unit)?
        ): CityViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_city, parent, false)
            val binding = ItemCityBinding.bind(view)
            return CityViewHolder(
                binding,
                mOnItemCityClicked
            )
        }
    }
}