package com.example.myapplication.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.City
import com.example.myapplication.data.model.CustomObject
import com.example.myapplication.ui.main.viewholder.CityViewHolder
import com.example.myapplication.ui.main.viewholder.FoodViewHolder
import com.example.myapplication.ui.main.viewholder.TitleViewHolder
import com.example.myapplication.util.Constants
import javax.inject.Inject

class MainAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mOnItemCityClicked: ((City) -> Unit)? = null
    private val differCallback = object : DiffUtil.ItemCallback<CustomObject>() {

        override fun areItemsTheSame(
            oldItem: CustomObject,
            newItem: CustomObject,
        ): Boolean {
            return oldItem.viewType == newItem.viewType
        }

        override fun areContentsTheSame(
            oldItem: CustomObject,
            newItem: CustomObject,
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.VIEW_TYPE_TITLE -> TitleViewHolder.create(parent)
            Constants.VIEW_TYPE_FOOD -> FoodViewHolder.create(parent)
            Constants.VIEW_TYPE_CITY -> CityViewHolder.create(parent, mOnItemCityClicked)
            else -> TitleViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (differ.currentList[position]?.viewType) {
            Constants.VIEW_TYPE_TITLE -> (holder as TitleViewHolder).bind(
                differ.currentList[position]
            )
            Constants.VIEW_TYPE_FOOD -> (holder as FoodViewHolder).bind(
                differ.currentList[position]
            )
            Constants.VIEW_TYPE_CITY -> (holder as CityViewHolder).bind(
                differ.currentList[position]
            )
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position]?.viewType) {
            Constants.VIEW_TYPE_TITLE -> Constants.VIEW_TYPE_TITLE
            Constants.VIEW_TYPE_CITY -> Constants.VIEW_TYPE_CITY
            Constants.VIEW_TYPE_FOOD -> Constants.VIEW_TYPE_FOOD
            else -> Constants.VIEW_TYPE_TITLE
        }
    }
}