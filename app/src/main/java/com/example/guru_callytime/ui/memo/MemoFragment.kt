package com.example.guru_callytime.ui.memo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import com.example.guru_callytime.R
import com.example.guru_callytime.SqliteHelper

class MemoFragment : Fragment() {

    lateinit var helper: SqliteHelper
    private lateinit var adapter: RecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var saveButton: Button
    private lateinit var editMemo: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_memo, container, false)

        helper = SqliteHelper(requireContext(), "memo", null, 1)
        recyclerView = view.findViewById(R.id.recyclerMemo)
        saveButton = view.findViewById(R.id.save)
        editMemo = view.findViewById(R.id.editMemo)

        adapter = RecyclerAdapter()
        adapter.listData.addAll(helper.selectMemo())
        adapter.helper = helper

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 저장 버튼 클릭 이벤트
        saveButton.setOnClickListener {
            if (editMemo.text.toString().isNotEmpty()) {
                val memo = Memo(null, editMemo.text.toString(), System.currentTimeMillis())
                helper.insertMemo(memo)
                adapter.listData.clear()
                adapter.listData.addAll(helper.selectMemo())
                adapter.notifyDataSetChanged() // 데이터 변경을 알림
                editMemo.setText("")
            }
        }

        return view
    }
}
