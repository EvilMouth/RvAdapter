package com.zyhang.rvAdapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * ProjectName:YHRvAdapter
 * Description:
 * Created by zyhang on 2017/7/16.下午9:45
 * Modify by:
 * Modify time:
 * Modify remark:
 */

class DefaultMoreCreatorHelper : RvListAdapter.DefaultMoreCreator {
    override fun onCreateView(parent: ViewGroup): View {
        return parent.inflate(R.layout.vh_more)
    }

    override fun onNoMore(holder: RecyclerView.ViewHolder) {
        holder.itemView.findViewById(R.id.vh_more_dtv).visible()
        holder.itemView.findViewById(R.id.vh_more_pb).gone()
        holder.itemView.findViewById(R.id.vh_more_tv).gone()
    }

    override fun onLoadMore(holder: RecyclerView.ViewHolder) {
        holder.itemView.findViewById(R.id.vh_more_dtv).gone()
        holder.itemView.findViewById(R.id.vh_more_pb).visible()
        holder.itemView.findViewById(R.id.vh_more_tv).gone()
    }

    override fun onLoadSuccess(holder: RecyclerView.ViewHolder) {
        holder.itemView.findViewById(R.id.vh_more_dtv).gone()
        holder.itemView.findViewById(R.id.vh_more_pb).gone()
        holder.itemView.findViewById(R.id.vh_more_tv).visible()
        (holder.itemView.findViewById(R.id.vh_more_tv) as TextView).setText(R.string.vh_more_success)
    }

    override fun onLoadFail(holder: RecyclerView.ViewHolder): View {
        holder.itemView.findViewById(R.id.vh_more_dtv).gone()
        holder.itemView.findViewById(R.id.vh_more_pb).gone()
        holder.itemView.findViewById(R.id.vh_more_tv).visible()
        (holder.itemView.findViewById(R.id.vh_more_tv) as TextView).setText(R.string.vh_more_fail)
        return holder.itemView.findViewById(R.id.vh_more_tv)
    }
}