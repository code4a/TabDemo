package com.code4a.baservadapter

import android.view.View

/**
 * Desc: com.jiangyt.baservadapter
 * <p>
 * @author Create by sinochem on 2020-01-15
 * <p>
 * Version: 1.0.0
 */
interface OnRecyclerViewItemClickListener<T> {
    fun onItemClick(view: View?, viewType: Int, data: T, position: Int)
}