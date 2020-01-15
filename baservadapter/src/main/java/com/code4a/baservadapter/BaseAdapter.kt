package com.code4a.baservadapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Desc: com.jiangyt.baservadapter
 * <p>
 * @author Create by sinochem on 2020-01-08
 * <p>
 * Version: 1.0.0
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseHolder<T>> {

    protected var dataList: MutableList<T>
    protected var listener: OnRecyclerViewItemClickListener<T>? = null
    private lateinit var baseHolder: BaseHolder<T>

    constructor(dataList: MutableList<T>) : super() {
        this.dataList = dataList
    }

    fun getDatas(): MutableList<T> {
        return dataList
    }

    /**
     * 返回数据的个数
     *
     * @return
     */
    override fun getItemCount(): Int {
        return this.dataList.size
    }

    /**
     * 获得某个 {@code position} 上的 item 的数据
     *
     * @param position
     * @return
     */
    fun getItem(position: Int): T {
        return dataList[position]
    }

    fun defaultNotifyItemChange(position: Int) {
        notifyItemChanged(position,
            NOTIFY_ITEM
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T> {
        baseHolder = getBaseHolder(parent, viewType)
        baseHolder.setOnItemClickListener(onViewClickListener = object :
            OnViewClickListener {
            override fun onViewClick(v: View?, position: Int) {
                if (listener != null && dataList.isNotEmpty()) {
                    listener!!.onItemClick(v, viewType, dataList[position], position)
                }
            }
        })
        return baseHolder
    }

    abstract fun getBaseHolder(parent: ViewGroup, viewType: Int): BaseHolder<T>

    /**
     * 绑定数据
     *
     * @param holder holder
     * @param position 位置
     * @param payloads payloads
     */
    override fun onBindViewHolder(
        holder: BaseHolder<T>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            if (payloads[0] is Int) {
                val flag: Int = payloads[0] as Int
                if (flag == NOTIFY_ITEM) {
                    holder.baseBind(getItem(holder.adapterPosition), holder.adapterPosition)
                }
            }
        }
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        holder.baseBind(dataList[position], position)
    }

    fun setOnItemClickListener(listener: OnRecyclerViewItemClickListener<T>) {
        this.listener = listener
    }

    companion object {

        /**
         * 条目刷新
         */
        const val NOTIFY_ITEM: Int = 0x101

        /**
         * 遍历所有{@link BaseHolder},释放他们需要释放的资源
         *
         * @param recyclerView
         */
        fun releaseAllHolder(recyclerView: RecyclerView?) {
            if (recyclerView == null) return
            for (i in recyclerView.childCount - 1 downTo 0) {
                val child: View = recyclerView.getChildAt(i)
                var viewHolder: RecyclerView.ViewHolder? = recyclerView.getChildViewHolder(child)
                if (viewHolder != null && viewHolder is BaseHolder<*>) {
                    viewHolder.onRelease()
                }
            }
        }
    }
}