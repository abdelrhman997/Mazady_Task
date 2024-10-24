package com.example.mazaadytask.firstPage

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.core.models.OptionsItem
import com.example.mazaadytask.R

class SearchBottomSheetFragment(
    val title: String,
    val catList: ArrayList<OptionsItem>,
    val onItemSelected: (OptionsItem) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var searchItemAdapter: SearchItemAdapter
    private lateinit var searchEditText: EditText
    private lateinit var noMatchingItems: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_bottom_sheet, container, false)
        searchEditText = view.findViewById(R.id.search_edit_text)
        noMatchingItems = view.findViewById(R.id.no_matching_items)
        recyclerView = view.findViewById(R.id.recycler_view)
        view.findViewById<AppCompatImageButton>(R.id.cancel_button).setOnClickListener {
            dismiss()
        }
        view.findViewById<TextView>(R.id.title_text).text = title

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                dismissKeyboard()
                true
            } else {
                false
            }
        }

        setupRecyclerView()
        setupList(catList)
        return view
    }

    private fun setupRecyclerView() {
        searchItemAdapter = SearchItemAdapter(catList) { selectedItem ->
            onItemSelected(selectedItem)
            dismiss()
        }
        recyclerView.adapter = searchItemAdapter
    }

    private fun setupList(data: ArrayList<OptionsItem>) {
        data.removeLastOrNull()
        data.add(data.size,OptionsItem(name = "other"))
        updateList(data)

        searchEditText.addTextChangedListener { text ->
            searchItemAdapter.filter.filter(text)
            updateList(searchItemAdapter.filteredItems)
        }
    }

    private fun updateList(data: ArrayList<OptionsItem>) {
        noMatchingItems.visibility = if (data.isEmpty()) {
            VISIBLE
        } else {
            GONE
        }
    }

    private fun dismissKeyboard() {
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchEditText.windowToken, 0)
    }
}

