package com.mattiasossai.binancepushtrader

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private val signals = mutableListOf<Signal>()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val s = Signal(
                intent.getStringExtra("signal_symbol")!!,
                intent.getStringExtra("signal_side")!!,
                intent.getDoubleExtra("signal_qty", 0.0)
            )
            signals.add(s)
            recycler.adapter?.notifyItemInserted(signals.size - 1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler = findViewById(R.id.recyclerSignals)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = SignalAdapter(signals)

        registerReceiver(receiver, IntentFilter("com.mattiasossai.NEW_SIGNAL"))
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}
