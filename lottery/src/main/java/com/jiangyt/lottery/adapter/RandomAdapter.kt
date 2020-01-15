package com.jiangyt.lottery.adapter

import android.app.Activity
import android.content.Context
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.jiangyt.lottery.R
import com.jiangyt.lottery.data.RandomData
import com.jiangyt.lottery.widget.SingleRandomText

/**
 * Desc: com.jiangyt.lottery.adapter
 * <p>
 * @author Create by sinochem on 2020-01-13
 * <p>
 * Version: 1.0.0
 */
class RandomAdapter : RecyclerView.Adapter<RandomAdapter.ViewHolder> {

    private var context: Context
    private var datas: ArrayList<RandomData>
    private var changing: Boolean = false


    constructor(context: Context, datas: ArrayList<RandomData>) : super() {
        this.context = context
        this.datas = datas
    }

    fun getData(position: Int): RandomData {
        return datas[position]
    }

    fun notifyRandomChange(activity: Activity, running: Boolean) {
        if (changing) {
            Toast.makeText(activity, "状态改变中,请稍后...", Toast.LENGTH_SHORT).show()
            return
        }
        Thread(Runnable {
            changing = true
            for (i in datas.indices) {
                datas[i].running = running
                activity.runOnUiThread {
                    notifyItemChanged(i, NOTIFY_ITEM)
                }
                SystemClock.sleep(50)
            }
            changing = false
        }).start()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.item_lottery, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            if (payloads[0] is Int) {
                val flag: Int = payloads[0] as Int
                if (flag == NOTIFY_ITEM) {
                    holder.bind(getData(position))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getData(position))
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var randomText: SingleRandomText

        constructor(itemView: View) : super(itemView) {
            randomText = itemView.findViewById(R.id.single_random_tv)
        }

        fun bind(randomData: RandomData) {
            if (randomData.running) {
                randomText.startTask()
            } else {
                randomText.stopTask()
            }
        }
    }

    companion object {
        const val NOTIFY_ITEM: Int = 0x100
    }
}