package com.example.mazaadytask.firstPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.mazaadytask.R

class CategoryBottomSheetFragment(private val options: List<String>, private val onItemClicked: (position:Int)->Unit) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listView: ListView = view.findViewById(R.id.listview)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, options)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            onItemClicked(position)
            dismiss()
        }
    }
}
