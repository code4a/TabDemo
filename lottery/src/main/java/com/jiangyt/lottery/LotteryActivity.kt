package com.jiangyt.lottery

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jiangyt.lottery.adapter.RandomAdapter
import com.jiangyt.lottery.data.Randoms

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LotteryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RandomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_lottery)
        supportActionBar?.hide()

        initView()
    }

    private fun initView() {
        findViewById<Button>(R.id.fun_45).setOnClickListener(this)
        findViewById<Button>(R.id.fun_88).setOnClickListener(this)
        findViewById<Button>(R.id.fun_start).setOnClickListener(this)
        findViewById<Button>(R.id.fun_pause).setOnClickListener(this)
        findViewById<Button>(R.id.fun_timer).setOnClickListener(this)
        recyclerView = findViewById(R.id.random_rv)
        changeLayout(true)
    }

    private fun changeLayout(is45: Boolean) {
        if (is45) {
            recyclerView.layoutManager = GridLayoutManager(this, 4)
            adapter = RandomAdapter(this, Randoms.get45RandomData())
            recyclerView.adapter = adapter
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 8)
            adapter = RandomAdapter(this, Randoms.get88RandomData())
            recyclerView.adapter = adapter
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fun_45 -> {
                changeLayout(true)
            }
            R.id.fun_88 -> {
                changeLayout(false)
            }
            R.id.fun_start -> {
                adapter.notifyRandomChange(this, true)
            }
            R.id.fun_pause -> {
                adapter.notifyRandomChange(this, false)
            }
            R.id.fun_timer -> {
            }
            else -> {
            }
        }
    }

}
