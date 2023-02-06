package com.example.globalist.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.globalist.model.Country
import com.example.globalist.service.CountryApiService
import com.example.globalist.service.CountryDataBase
import com.example.globalist.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class FeedViewModel(application: Application):BaseViewModel (application) {
    private var customSharedPreferences=CustomSharedPreferences(getApplication())
    private val disposable=CompositeDisposable()
    private val countryApiService= CountryApiService()
    private var refreshTime= 10*60*1000*1000*1000L


    val countries= MutableLiveData<List<Country>>()
    val countryError=MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime= customSharedPreferences.getTime()
        if (updateTime!=null && updateTime!= 0L && System.nanoTime() - updateTime< refreshTime){
            getFromSqLite()
        }else{
            getDataFromApi()

        }



    }
    fun refreshApiData(){
        getDataFromApi()
    }
    private fun getFromSqLite(){
        launch {
            val countries =CountryDataBase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries from Sqlite",Toast.LENGTH_LONG).show()
        }
    }


    private fun getDataFromApi(){
      countryLoading?.value=true
        disposable?.add(
            countryApiService.getData().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :DisposableSingleObserver<List<Country>>(){
                override fun onSuccess(t: List<Country>) {
                    storeInSqLite(t)
                    Toast.makeText(getApplication(),"Countires from Data",Toast.LENGTH_LONG).show()

                }

                override fun onError(e: Throwable) {
                    countryLoading.value=false
                    countryError.value=true
                    e.printStackTrace()
                }
            })
        )
    }
private fun showCountries (countryList: List<Country>){
    countries.value=countryList
    countryError.value=false
    countryLoading.value=false
}
    private fun storeInSqLite (list:List<Country>){
        launch {
            val dao =CountryDataBase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong=dao.insertAll(*list.toTypedArray()) // listenin içindeki tek bir elemanı alabiliyoruz
            var i= 0
            while (i<list.size){
                list[i].id=listLong[i].toInt()
                i=i+1
            }
            showCountries(list)



        }
        customSharedPreferences.saveTimte(System.nanoTime())
        println(System.nanoTime())


    }
}