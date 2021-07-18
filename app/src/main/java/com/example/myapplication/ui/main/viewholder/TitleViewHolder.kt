package com.example.myapplication.ui.main.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.CustomObject
import com.example.myapplication.databinding.ItemTitleBinding

class TitleViewHolder(private val mBinding: ItemTitleBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun bind(currentItem: CustomObject) {
        val currentTitle = currentItem.foodOrCityOrTitle as String
        mBinding.textViewItemTitle.text = currentTitle
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): TitleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_title, parent, false)
            val binding = ItemTitleBinding.bind(view)
            return TitleViewHolder(
                binding
            )
        }
    }
}