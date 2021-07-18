package com.example.myapplication.ui.main.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.example.myapplication.R
import com.example.myapplication.data.model.CustomObject
import com.example.myapplication.data.model.Food
import com.example.myapplication.databinding.ItemFoodBinding

class FoodViewHolder(private val mBinding: ItemFoodBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun bind(currentItem: CustomObject) {
        val currentFood = currentItem.foodOrCityOrTitle as Food

        mBinding.imageViewItemCity.load(currentFood.mImage) {
            memoryCachePolicy(CachePolicy.ENABLED)
            crossfade(true)
        }

        mBinding.textViewItemCityCityName.text = currentFood.mName
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): FoodViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_food, parent, false)
            val binding = ItemFoodBinding.bind(view)
            return FoodViewHolder(
                binding
            )
        }
    }
}