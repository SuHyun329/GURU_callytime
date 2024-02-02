package com.example.guru_callytime.ui.memo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guru_callytime.R
import com.example.guru_callytime.SqliteHelper
import java.text.SimpleDateFormat

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var listData = ArrayList<Memo>()
    var helper: SqliteHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_memo, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo: Memo = listData[position]
        holder.setMemo(memo)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var textId: TextView = itemView.findViewById(R.id.textId)
        private var textContent: TextView = itemView.findViewById(R.id.textContent)
        private var btnDel: Button = itemView.findViewById(R.id.btn_del)

        init {
            btnDel.setOnClickListener {
                val position = adapterPosition
                helper?.deleteMemo(listData[position])
                listData.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        fun setMemo(memo: Memo) {
            textId.text = memo.id.toString()
            textContent.text = memo.content
        }
    }
}
