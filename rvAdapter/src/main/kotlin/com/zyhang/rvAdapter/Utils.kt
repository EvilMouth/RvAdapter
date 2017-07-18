package com.zyhang.rvAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * ProjectName:YHRvAdapter
 * Description:
 * Created by zyhang on 2017/7/17.下午1:54
 * Modify by:
 * Modify time:
 * Modify remark:
 */

fun ViewGroup.inflate(layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}