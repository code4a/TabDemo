package com.jiangyt.tabdemo.ui.post

import android.view.ViewGroup
import com.code4a.baservadapter.BaseHolder
import com.jiangyt.tabdemo.R
import com.jiangyt.tabdemo.databinding.ItemPost2Binding
import com.jiangyt.tabdemo.model.Post

/**
 * Desc: com.jiangyt.tabdemo.ui.post
 * <p>
 * @author Create by sinochem on 2020-01-15
 * <p>
 * Version: 1.0.0
 */
class PostList2Holder : BaseHolder<Post> {
    private val viewModel = PostViewModel()

    constructor(parent: ViewGroup) : super(parent, R.layout.item_post_2)

    override fun setData(data: Post, position: Int) {
        viewModel.bind(data)
        getBinding<ItemPost2Binding>()!!.viewModel = viewModel
    }
}