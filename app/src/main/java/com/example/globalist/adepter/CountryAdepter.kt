package com.example.globalist.adepter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.globalist.model.Country
import com.example.globalist.R
import com.example.globalist.databinding.ItemCountryBinding
import com.example.globalist.util.downloadFromUri
import com.example.globalist.util.placeholderProgressBar
import com.example.globalist.view.FeedListDirections
import io.reactivex.internal.schedulers.ImmediateThinScheduler


class CountryAdepter(val countryList :ArrayList<Country>):RecyclerView.Adapter<CountryAdepter.CountryHolder>(),CountryClickListener {
    class CountryHolder(var view: ItemCountryBinding):RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
     // val gorunum= LayoutInflater.from(parent.context).inflate(R.layout.item_country,parent,false)
        val gorunum =DataBindingUtil.inflate<ItemCountryBinding>(LayoutInflater.
        from(parent.context),R.layout.item_country,parent,false)
        return CountryHolder(gorunum)

    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.view.country=countryList[position]
        holder.view.listener=this
        /*
        holder.view.findViewById<TextView>(R.id.countryItemName).text=countryList[position].countryName
        holder.view.findViewById<TextView>(R.id.countryItemRegion).text=countryList[position].countryRegion
        holder.view.setOnClickListener(){
            val aksiyon=FeedListDirections.actionFeedListToCountryList()
            aksiyon.setCountryuidii(countryList.get(position).id)
            Navigation.findNavController(it).navigate(aksiyon)

        }

        holder.view.findViewById<ImageView>(R.id.countryImage).
        downloadFromUri(countryList[position].countryUri, placeholderProgressBar(holder.view.context))

         */

    }

    override fun getItemCount(): Int {
        return countryList.size

    }
    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()

    }

    override fun onCountryClicked(v: View) {
        super.onCountryClicked(v)
        val newUuid=v.findViewById<TextView>(R.id.uuidtext).text.toString().toInt()
        val aksiyon=FeedListDirections.actionFeedListToCountryList()
        aksiyon.setCountryuidii(newUuid)
        Navigation.findNavController(v).navigate(aksiyon)

    }
}