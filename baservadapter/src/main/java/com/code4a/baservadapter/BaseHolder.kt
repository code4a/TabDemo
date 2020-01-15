package com.code4a.baservadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Desc: recycler view 适配器holder
 * <p>
 * @author Create by sinochem on 2020-01-08
 * <p>
 * Version: 1.0.0
 */
abstract class BaseHolder<T> :
    RecyclerView.ViewHolder, View.OnClickListener {

    private var onViewClickListener: OnViewClickListener? = null

    private var binding: ViewDataBinding? = DataBindingUtil.getBinding(this.itemView)

    constructor(parent: ViewGroup, @LayoutRes layoutId: Int) : super(
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        ).root
    ) {
        this.itemView.setOnClickListener(this)
    }

    /**
     * @param data   the data of bind
     * @param position the item position of recyclerView
     */
    abstract fun setData(data: T, position: Int)

    /**
     * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
     */
    fun baseBind(data: T, position: Int) {
        setData(data, position)
        binding!!.executePendingBindings()
    }

    @Suppress("UNCHECKED_CAST")
    fun <B : ViewDataBinding> getBinding(): B? {
        return binding as B?
    }

    /**
     * 在 Activity 的 onDestroy 中使用 {@link BaseAdapter#releaseAllHolder(RecyclerView)} 方法 (super.onDestroy() 之前)
     * {@link BaseHolder#onRelease()} 才会被调用, 可以在此方法中释放一些资源
     */
    fun onRelease() {}

    override fun onClick(v: View?) {
        if (onViewClickListener != null) {
            onViewClickListener!!.onViewClick(v, adapterPosition)
        }
    }

    /**
     * 设置条目点击事件
     */
    fun setOnItemClickListener(onViewClickListener: OnViewClickListener) {
        this.onViewClickListener = onViewClickListener
    }
}