package com.example.globalist.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.globalist.countryViewModel.CountryViewModel
import com.example.globalist.databinding.FragmentCountryListBinding
import com.example.globalist.model.Country
import com.example.globalist.util.downloadFromUri
import com.example.globalist.util.placeholderProgressBar


class CountryList : Fragment() {
    private lateinit var binding: FragmentCountryListBinding
    private lateinit var viewModel: CountryViewModel
    private var countryUid=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCountryListBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUid= CountryListArgs.fromBundle(it).countryuidii
        }

        viewModel=ViewModelProvider(this).get(CountryViewModel::class.java)

        viewModel.getDataRoomBase(countryUid)


        observeLiveData2()
    }
    fun observeLiveData2(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country->
            country?.let{
                binding.selectedCountry=country
                /*
                binding.countryNameId.text=country.countryName
                binding.countryCapital.text=country.countryCapital
                binding.countryLanguage.text=country.countryLanguage
                binding.countryCurrency.text=country.countryCurrency
                binding.countryRegion.text=country.countryRegion

                context?.let {
                    binding.countryListId.downloadFromUri(country.countryUri, placeholderProgressBar(it))
                }

                 */

            }


        })


    }


}