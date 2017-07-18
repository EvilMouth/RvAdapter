package com.zyhang.rvAdapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.zyhang.rvAdapter.R
import com.zyhang.rvAdapter.inflate

/**
 * ProjectName:YHRvAdapter
 * Description:
 * Created by zyhang on 2017/7/15.下午2:45
 * Modify by:
 * Modify time:
 * Modify remark:
 */

internal class LoadingViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(RvListAdapter.sDefaultLoadingCreator?.onCreateView(parent) ?: parent.inflate(R.layout.vh_loading))

internal class EmptyViewHolder(parent: ViewGroup, creator: RvListAdapter.DefaultCreator?)
    : RecyclerView.ViewHolder(creator?.onCreateView(parent) ?: parent.inflate(R.layout.vh_empty))

internal class MoreViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(RvListAdapter.sDefaultMoreCreator.onCreateView(parent)) {

    enum class State {
        Default, //-2
        NoMore, //-1
        Ready, //0
        Loading, //1
        Success, //2
        Fail, //3
    }
}