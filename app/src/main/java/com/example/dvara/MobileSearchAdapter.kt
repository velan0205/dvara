package com.example.dvara

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dvara.databinding.MobileListRowBinding


class MobileSearchAdapter() :
    RecyclerView.Adapter<MobileSearchAdapter.ViewHolder>() {
    var list: ArrayList<Mobile> = ArrayList()

    inner class ViewHolder(val binding: MobileListRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MobileListRowBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.mobile_list_row,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mobile = list[position]
        holder.binding.setVariable(BR.mobileNumber, mobile.mobileNumber)
        holder.binding.setVariable(BR.upSpeed, mobile.upSpeed)
        holder.binding.setVariable(BR.timeStamp, mobile.timeStamp)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: ArrayList<Mobile>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun addData(mobile: Mobile) {
        this.list.add(mobile)
        notifyDataSetChanged()

    }

    fun clear() {
        this.list.clear()
        notifyDataSetChanged()
    }

}