package com.example.globalist.countryViewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.globalist.model.Country
import com.example.globalist.service.CountryDao
import com.example.globalist.service.CountryDataBase
import com.example.globalist.viewModel.BaseViewModel
import kotlinx.coroutines.launch
import java.util.UUID


class CountryViewModel(application: Application):BaseViewModel(application) {
    val countryLiveData=MutableLiveData<Country>()
    fun getDataRoomBase(id: Int){
        launch {
           val dao = CountryDataBase(getApplication()).countryDao()
            val country =dao.getCountry(id)
            countryLiveData.value=country


        }

    }

}