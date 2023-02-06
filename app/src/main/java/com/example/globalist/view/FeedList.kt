package com.example.globalist.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.globalist.adepter.CountryAdepter
import com.example.globalist.databinding.FragmentFeedListBinding
import com.example.globalist.viewModel.FeedViewModel


class FeedList : Fragment() {
    private val countryAdepter = CountryAdepter(arrayListOf())
    private lateinit var viewModel: FeedViewModel
    private lateinit var binding: FragmentFeedListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment


        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()
        binding.countryListRec.adapter = countryAdepter
        binding.countryListRec.layoutManager = LinearLayoutManager(context)
        binding.swipeRefresh.setOnRefreshListener {
            binding.countryListRec.visibility=View.GONE
            binding.countryListErorMassage.visibility=View.GONE
            binding.CountyListProgressBar.visibility=View.VISIBLE
            viewModel.refreshApiData()
            binding.swipeRefresh.isRefreshing=false

        }
        observeLiveData()

    }
        fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.countryListRec.visibility = View.VISIBLE
                countryAdepter.updateCountryList(it)
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.countryListErorMassage.visibility = View.VISIBLE

                } else {
                    binding.countryListErorMassage.visibility = View.GONE
                }
            }

        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.CountyListProgressBar.visibility = View.VISIBLE
                    binding.countryListErorMassage.visibility = View.GONE
                    binding.countryListRec.visibility = View.GONE


                } else {
                    binding.CountyListProgressBar.visibility = View.GONE
                }
            }

        })
    }
}






