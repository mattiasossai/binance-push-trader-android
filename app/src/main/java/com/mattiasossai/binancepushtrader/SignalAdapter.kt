package com.mattiasossai.binancepushtrader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SignalAdapter(private val signals: List<Signal>) :
    RecyclerView.Adapter<SignalAdapter.SignalViewHolder>() {

    class SignalViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val symbolTv: TextView = item.findViewById(R.id.tvSymbol)
        val sideTv: TextView = item.findViewById(R.id.tvSide)
        val qtyTv: TextView  = item.findViewById(R.id.tvQty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_signal, parent, false)
        return SignalViewHolder(view)
    }

    override fun onBindViewHolder(holder: SignalViewHolder, position: Int) {
        val signal = signals[position]
        holder.symbolTv.text = signal.symbol
        holder.sideTv.text   = signal.side
        holder.qtyTv.text    = signal.qty.toString()
    }

    override fun getItemCount(): Int = signals.size
}
