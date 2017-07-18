package com.zyhang.rvAdapter.example

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.zyhang.rvAdapter.RvListAdapter
import com.zyhang.rvAdapter.inflate

/**
 * ProjectName:RvAdapter
 * Description:
 * Created by zyhang on 2017/7/18.下午2:23
 * Modify by:
 * Modify time:
 * Modify remark:
 */

open class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter
    private val list = listOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RvListAdapter.setDefaultLoadingCreator(object : RvListAdapter.DefaultCreator {
            override fun onCreateView(parent: ViewGroup): View {
                return parent.inflate(R.layout.vh_custom_loading)
            }

            override fun onBindView(holder: RecyclerView.ViewHolder) {
            }
        })

        setContentView(R.layout.activity_main)

        val rv = findViewById(R.id.rv) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)

        adapter = MainAdapter()
        adapter.setLoadMoreCallback(object : RvListAdapter.LoadMoreCallback {
            override fun onLoadMore() {
                Handler().postDelayed({
                    count++
                    if (count == 1) {
                        adapter.loadMoreFail()
                    } else if (count == 4) {
                        adapter.loadNoMore()
                    } else {
                        adapter.addList(list)
                    }
                }, 2000)
            }
        })
        rv.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_loading -> {
                adapter.showLoading()
                return true
            }
            R.id.menu_empty -> {
                adapter.showEmpty()
                return true
            }
            R.id.menu_setList -> {
                adapter.setList(list)
                return true
            }
            R.id.menu_secondActivity -> {
                startActivity(Intent(this, SecondActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}