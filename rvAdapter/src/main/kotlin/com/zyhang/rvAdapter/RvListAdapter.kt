package com.zyhang.rvAdapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * ProjectName:YHRvAdapter
 * Description:
 * Created by zyhang on 2017/7/14.上午12:31
 * Modify by:
 * Modify time:
 * Modify remark:
 */

abstract class RvListAdapter<in D> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface DefaultCreator {
        fun onCreateView(parent: ViewGroup): View
        fun onBindView(holder: RecyclerView.ViewHolder)
    }

    interface DefaultMoreCreator {
        fun onCreateView(parent: ViewGroup): View
        fun onNoMore(holder: RecyclerView.ViewHolder)
        fun onLoadMore(holder: RecyclerView.ViewHolder)
        fun onLoadSuccess(holder: RecyclerView.ViewHolder)
        fun onLoadFail(holder: RecyclerView.ViewHolder): View
    }

    interface LoadMoreCallback {
        fun onLoadMore()
    }

    companion object {
        private const val TYPE_LOADING = 1
        private const val TYPE_EMPTY = 2
        private const val TYPE_ITEM = 3
        private const val TYPE_MORE = 4
        internal var sDefaultLoadingCreator: DefaultCreator? = null
        internal var sDefaultMoreCreator: DefaultMoreCreator = DefaultMoreCreatorHelper()
        fun setDefaultLoadingCreator(creator: DefaultCreator) {
            sDefaultLoadingCreator = creator
        }

        fun setDefaultMoreCreator(creator: DefaultMoreCreator) {
            sDefaultMoreCreator = creator
        }

        internal var sMinItemCountToActivateLoadMore = 9
    }

    private val list: ArrayList<D> = arrayListOf()
    private var showEmpty = false
    private var loadMoreState = MoreViewHolder.State.Default
    private var loadMoreCallback: LoadMoreCallback? = null

    /**
     * 1 for -> if(list.size==0){loading or empty} else {more}
     */
    override fun getItemCount(): Int = list.size + 1

    override fun getItemViewType(position: Int): Int = when (list.size) {
        0 -> {
            if (showEmpty) TYPE_EMPTY else TYPE_LOADING
        }
        else -> {
            if (position == list.size) TYPE_MORE else TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        TYPE_LOADING -> {
            LoadingViewHolder(parent)
        }
        TYPE_EMPTY -> {
            EmptyViewHolder(parent, getEmptyCreator())
        }
        TYPE_MORE -> {
            MoreViewHolder(parent)
        }
        else -> {
            onCreateListViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LoadingViewHolder -> {
                sDefaultLoadingCreator?.onBindView(holder)
            }
            is EmptyViewHolder -> {
                getEmptyCreator()?.onBindView(holder)
            }
            is MoreViewHolder -> {
                updateState(loadMoreState, holder)
                loadMoreCallback?.let {
                    if (list.size > sMinItemCountToActivateLoadMore && (loadMoreState == MoreViewHolder.State.Default || loadMoreState == MoreViewHolder.State.Success)) {
                        updateState(MoreViewHolder.State.Ready, holder)
                    }
                }
            }
            else -> {
                onBindListViewHolder(holder, position)
            }
        }
    }

    abstract fun getEmptyCreator(): DefaultCreator?
    abstract fun onCreateListViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun onBindListViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    fun setLoadMoreCallback(loadMoreCallback: LoadMoreCallback) {
        this.loadMoreCallback = loadMoreCallback
    }

    fun showLoading() {
        list.clear()
        showEmpty = false
        notifyDataSetChanged()
    }

    fun showEmpty() {
        list.clear()
        showEmpty = true
        notifyDataSetChanged()
    }

    fun setList(list: List<D>) {
        loadMoreCallback?.let {
            updateState(MoreViewHolder.State.Default)
        }
        this.list.clear()
        notifyItemRangeRemoved(0, itemCount)
        this.list.addAll(list)
        notifyItemRangeInserted(0, itemCount)
    }

    fun addList(list: List<D>) {
        loadMoreCallback?.let {
            updateState(MoreViewHolder.State.Success)
        }
        val lastIndex = this.list.size
        this.list.addAll(list)
        notifyItemRangeInserted(lastIndex, list.size)
    }

    fun loadMoreFail() {
        updateState(MoreViewHolder.State.Fail)
        notifyItemChanged(list.size)
    }

    fun loadNoMore() {
        updateState(MoreViewHolder.State.NoMore)
        notifyItemChanged(list.size)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        loadMoreCallback?.let { loadMoreCallback ->
            if (holder is MoreViewHolder && loadMoreState == MoreViewHolder.State.Ready) {
                updateState(MoreViewHolder.State.Loading, holder)//for view
                loadMoreCallback.onLoadMore()//for loadData
            }
        }
    }

    private fun updateState(state: MoreViewHolder.State, holder: MoreViewHolder? = null) {
        loadMoreState = state
        if (holder != null) {
            when (loadMoreState) {
                MoreViewHolder.State.Default, MoreViewHolder.State.NoMore -> {
                    sDefaultMoreCreator.onNoMore(holder)
                }
                MoreViewHolder.State.Ready, MoreViewHolder.State.Loading -> {
                    sDefaultMoreCreator.onLoadMore(holder)
                }
                MoreViewHolder.State.Success -> {
                    sDefaultMoreCreator.onLoadSuccess(holder)
                }
                MoreViewHolder.State.Fail -> {
                    sDefaultMoreCreator.onLoadFail(holder).setOnClickListener { v ->
                        //reLoadMore
                        updateState(MoreViewHolder.State.Loading, holder)//for view
                        loadMoreCallback?.onLoadMore()//for loadData
                        v.setOnClickListener(null)
                    }
                }
            }
        }
    }
}