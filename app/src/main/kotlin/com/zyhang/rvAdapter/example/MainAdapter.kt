package com.zyhang.rvAdapter.example

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zyhang.rvAdapter.RvListAdapter
import com.zyhang.rvAdapter.inflate

/**
 * ProjectName:YHRvAdapter
 * Description:
 * Created by zyhang on 2017/7/14.上午12:32
 * Modify by:
 * Modify time:
 * Modify remark:
 */

internal class MainAdapter : RvListAdapter<String>() {

    override fun onCreateListViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return MainViewHolder(parent)
    }

    override fun onBindListViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainViewHolder) {
            val tv = holder.itemView.findViewById(R.id.vh_main_tv) as TextView
            tv.text = position.toString()
        }
    }

    override fun getEmptyCreator(): DefaultCreator? {
        return object : DefaultCreator {
            override fun onCreateView(parent: ViewGroup): View {
                return parent.inflate(R.layout.vh_custom_empty)
            }

            override fun onBindView(holder: RecyclerView.ViewHolder) {
                (holder.itemView as TextView).setText(R.string.vh_custom_empty)
            }
        }
    }

    inner class MainViewHolder(parent: ViewGroup)
        : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.vh_main, parent, false))
}