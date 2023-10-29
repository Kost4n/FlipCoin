package com.yes.or.no.app

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yes.or.no.app.databinding.FragmentRecordsBinding
import com.yes.or.no.app.db.RecordAdapter
import com.yes.or.no.app.db.RecordDb
import com.yes.or.no.app.db.RecycleItemDecoration
import kotlinx.coroutines.launch
import com.yes.or.no.app.db.Record


class RecordsFragment: Fragment() {
    private var _binding: FragmentRecordsBinding? = null
    private val binding get() = _binding!!
    private val recordDao by lazy {
        RecordDb.getDatabase(requireContext().applicationContext).getRecordDao()
    }
    private lateinit var adapter: RecordAdapter
    private lateinit var viewModel: RecViewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[RecViewModel::class.java]
        _binding = FragmentRecordsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
    }

    private fun setRecycler() {
        val recView = binding.recycler
        val noteClickListener = object : RecordAdapter.OnRecordClickListener {

            override fun onRecordClick(record: Record, position: Int) {
                watchNote(record)
            }
        }
        adapter = RecordAdapter(noteClickListener)
        recView.layoutManager = LinearLayoutManager(context)
        recView.setHasFixedSize(true)
        recView.addItemDecoration(RecycleItemDecoration(4))

        recView.adapter = adapter
        observe()
    }
    private fun watchNote(record: Record) {
        val view = layoutInflater.inflate(R.layout.record_row, null)
        view.findViewById<TextView>(R.id.record_text).text = record.recordText
        view.findViewById<TextView>(R.id.record_answer).text = record.recordAnswer
        view.findViewById<TextView>(R.id.record_date).text = record.recordDate
        AlertDialog.Builder(context)
            .setTitle("Do you want to delete an entry?")
            .setView(view)
            .setPositiveButton("Yes") { d, _ ->
                viewModel.deleteRecord(record)
                adapter.notifyDataSetChanged()
                d.dismiss()
            }
            .setNegativeButton("No") { d, _ ->
                d.dismiss()
            }
            .show()
    }

    private fun observe() {
        lifecycleScope.launch {
            recordDao.getNotes().collect() {
                if (it.isNotEmpty()) {
                    adapter.update(it.toMutableList())
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}