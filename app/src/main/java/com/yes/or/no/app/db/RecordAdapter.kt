package com.yes.or.no.app.db

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yes.or.no.app.R

class RecordAdapter(
    private val onRecordClickListener: OnRecordClickListener
) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {
    private var itemList = mutableListOf<Record>()

    inner class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recordText = itemView.findViewById<TextView>(R.id.record_text)
        private val recordDate = itemView.findViewById<TextView>(R.id.record_date)
        private val recordAnswer = itemView.findViewById<TextView>(R.id.record_answer)
        private val recordId = itemView.findViewById<TextView>(R.id.record_id)

        fun bind(record: Record, position: Int) {
            recordText.text = record.recordText
            recordDate.text = record.recordDate
            recordId.text = record.id.toString()
            recordAnswer.text = record.recordAnswer

            itemView.setOnClickListener {
                onRecordClickListener.onRecordClick(record, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.record_row, parent, false)

        return RecordViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) =
        holder.bind(itemList[position], position)

    fun update(items: MutableList<Record>) {
        itemList = items
        notifyDataSetChanged()
    }
    interface OnRecordClickListener {
        fun onRecordClick(record: Record, position: Int)
    }
}

class RecycleItemDecoration(
    val spaceHeight: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = spaceHeight
    }
}