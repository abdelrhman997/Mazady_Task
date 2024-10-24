package com.example.mazaadytask.firstPage

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.core.models.CategoriesItem
import com.example.core.models.CategoryProperty
import com.example.core.models.ChildrenItem
import com.example.core.services.NetworkResult
import com.example.mazaadytask.R
import com.example.mazaadytask.databinding.ActivityFirstPageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstPageActivity : AppCompatActivity() {

    private val optionsMap = HashMap<String,String>()
    private val selectedSubCatOptionsList =  ArrayList<CategoryProperty>()
    private var selectedSubCat: ChildrenItem? = null
    private var selectedMainCat: CategoriesItem? = null
    private val subCatList = ArrayList<ChildrenItem>()
    private val mainCatList = ArrayList<CategoriesItem>()
    private val mViewModel: FirstPageViewModel by viewModels()
    lateinit var binding: ActivityFirstPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initResultCollecting()
    }

    private fun initResultCollecting() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mViewModel.mainCatState.collect {
                        when (it) {
                            is NetworkResult.Success -> {
                                dismissLoading()
                                mainCatList.clear()
                                it.data?.data?.categories?.run {
                                    mainCatList.addAll(this)
                                }
                            }
                            is NetworkResult.Error -> {
                                dismissLoading()
                             //   showToast(it.message ?: "")
                            }
                            else -> {
                                showLoading()
                            }
                        }
                    }
                }
                launch {
                    mViewModel.catPropertiesState.collect {
                        when (it) {
                            is NetworkResult.Success -> {
                                dismissLoading()
                                selectedSubCatOptionsList.clear()
                                it.data?.data?.run {
                                    selectedSubCatOptionsList.addAll(this)
                                }
                                addNewOptionsToPage()
                            }
                            is NetworkResult.Error -> {
                                dismissLoading()
                               // showToast(it.message ?: "")
                            }
                            else -> {
                                showLoading()
                            }
                        }
                    }
                }
                launch {
                    mViewModel.allOptionsState.collect {
                        when (it) {
                            is NetworkResult.Success -> {
                                dismissLoading()
                            }
                            is NetworkResult.Error -> {
                                dismissLoading()
                                //showToast(it.message ?: "")
                            }
                            else -> {
                                showLoading()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addNewOptionsToPage() {
        binding.subCatOptionsLayout.removeAllViews()
        for(item in selectedSubCatOptionsList)
        {
            initViewsForEachOption(item)
        }
    }

    private fun initViewsForEachOption(item: CategoryProperty) {
        val inflatedView = layoutInflater.inflate(R.layout.option_item_layout, binding.subCatOptionsLayout, false)
        val ll = inflatedView.findViewById<LinearLayout>(R.id.container)
        val textInputLayout= inflatedView.findViewById<TextInputLayout>(R.id.text_input_layout)
        textInputLayout.hint = item.name?:""
        val labelText= inflatedView.findViewById<AutoCompleteTextView>(R.id.label_text)
        labelText.setOnClickListener {
            SearchBottomSheetFragment(item.name?:"",item.options?: arrayListOf()){
                if(it.id == -1)
                {
                    val inflatedOtherEditTextLayout = layoutInflater.inflate(R.layout.option_other_layout, ll, false)
                    val otherEditText = inflatedOtherEditTextLayout.findViewById<TextInputEditText>(R.id.other_edit_text)
                    otherEditText.setOnEditorActionListener { _, actionId, _ ->
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            optionsMap[item.name?:""] = otherEditText.text.toString()
                            return@setOnEditorActionListener true
                        }
                        false
                    }
                    ll.addView(inflatedOtherEditTextLayout)
                }
                else{
                    optionsMap[item.name?:""] = it.name?:""
                    labelText.setText(it.name?:"")
                    callAPIToFetchAllOptionsOfAnotherOption(it.id?:0)
                }
            }.apply {
                show(supportFragmentManager, this.tag)
            }
        }
        labelText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                labelText.performClick()
            }
        }
        binding.subCatOptionsLayout.addView(inflatedView)
    }

    private fun callAPIToFetchAllOptionsOfAnotherOption(id: Int) {
        mViewModel.fetchAllOptions(id)
    }

    private fun initViews() = with(binding) {
        mainCat.setOnClickListener {
            CategoryBottomSheetFragment(mainCatList.map { it.name ?: "" }) {
                selectedMainCat = mainCatList[it]
                selectedMainCat?.children?.run {
                    subCatList.clear()
                    subCatList.addAll(this)
                    selectedSubCat = null
                    subCat.setText("")
                }
                mainCat.setText(selectedMainCat?.name ?: "")
            }.apply {
                show(supportFragmentManager, this.tag)
            }
        }
        mainCat.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mainCat.performClick()
            }
        }
        subCat.setOnClickListener {
            selectedMainCat?.children?.let { subCatList ->
                CategoryBottomSheetFragment(subCatList.map { it.name ?: "" }) {
                    selectedSubCat = subCatList[it]
                    subCat.setText(selectedSubCat?.name ?: "")
                    callAPIToFetchAllOptionsOfSubCat()
                }.apply {
                    show(supportFragmentManager, this.tag)
                }
            }
        }
        subCat.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    subCat.performClick()
                }
            }
        submit.setOnClickListener{
            val bundle = Bundle()
            for ((key, value) in optionsMap) {
                bundle.putString(key, value)
            }
            val intent = Intent(this@FirstPageActivity, SubmitActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun callAPIToFetchAllOptionsOfSubCat() {
        mViewModel.fetchCatProperties(selectedSubCat?.id ?: 0)
    }

    private fun showLoading() {
        binding.loadingText.visibility = VISIBLE
    }

    private fun dismissLoading() {
        binding.loadingText.visibility = GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }


}