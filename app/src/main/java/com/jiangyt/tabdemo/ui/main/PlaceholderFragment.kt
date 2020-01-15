package com.jiangyt.tabdemo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.code4a.baservadapter.OnRecyclerViewItemClickListener
import com.jiangyt.tabdemo.R
import com.jiangyt.tabdemo.databinding.FragmentMainBinding
import com.jiangyt.tabdemo.injection.ViewModelFactory
import com.jiangyt.tabdemo.model.Post
import com.jiangyt.tabdemo.ui.post.PostListViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var listViewModel: PostListViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(PostListViewModel::class.java)
                .apply {
                    setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
                }
        listViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.postList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        binding.postList.adapter = listViewModel.postListAdapter
        binding.viewModel = listViewModel
//        val textView: TextView = root.findViewById(R.id.section_label)
//        listViewModel.text.observe(this, Observer<String> {
//            textView.text = it
//        })
        listViewModel.postListAdapter.setOnItemClickListener(listener = object :
            OnRecyclerViewItemClickListener<Post> {
            override fun onItemClick(view: View?, viewType: Int, data: Post, position: Int) {
                Toast.makeText(activity, data.title, Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.app_name, listViewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}