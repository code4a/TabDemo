package com.jiangyt.tabdemo.ui.post

import android.view.ViewGroup
import com.code4a.baservadapter.BaseHolder
import com.jiangyt.tabdemo.R
import com.jiangyt.tabdemo.databinding.ItemPostBinding
import com.jiangyt.tabdemo.model.Post

/**
 * Desc: com.jiangyt.tabdemo.ui.post
 * <p>
 * @author Create by sinochem on 2020-01-15
 * <p>
 * Version: 1.0.0
 */
class PostListHolder : BaseHolder<Post> {
    private val viewModel = PostViewModel()

    constructor(parent: ViewGroup) : super(parent, R.layout.item_post)

    override fun setData(data: Post, position: Int) {
        viewModel.bind(data)
        getBinding<ItemPostBinding>()!!.viewModel = viewModel
    }
}