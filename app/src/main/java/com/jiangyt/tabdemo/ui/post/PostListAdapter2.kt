package com.jiangyt.tabdemo.ui.post

import android.view.ViewGroup
import com.code4a.baservadapter.BaseAdapter
import com.code4a.baservadapter.BaseHolder
import com.jiangyt.tabdemo.model.Post

/**
 * Desc: com.jiangyt.tabdemo.ui.post
 * <p>
 * @author Create by sinochem on 2020-01-15
 * <p>
 * Version: 1.0.0
 */
class PostListAdapter2 : BaseAdapter<Post>(arrayListOf()) {

    override fun getBaseHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<Post> {
        return if (viewType == 0) PostListHolder(parent) else PostList2Holder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) 0 else 1
    }

    fun updatePostList(postList: MutableList<Post>) {
        this.dataList.clear()
        this.dataList.addAll(postList)
        notifyDataSetChanged()
    }
}