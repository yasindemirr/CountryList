package com.example.globalist.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.globalist.R
//glide ile görsel çekeceğiz.Bunun için ye bir dosya oluştuduk ve ImageView sınıfına yeni fonskiyonlar
//ekledik.Böylece istediğimiz zaman downloadFromUri ile glide ı kullanabileceğiz
fun ImageView.downloadFromUri(uri:String?,progressDrawable: CircularProgressDrawable){
   val options=RequestOptions().placeholder(progressDrawable).error(R.mipmap.ic_launcher)
//normalde gerekli olan asağıdaki satırlar
    Glide.with(context).
    setDefaultRequestOptions(options).
    load(uri).
    into(this)

}
//opsiyonel
 fun placeholderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth=8f
        centerRadius=40f
        start()
    }

}
@BindingAdapter("android:downloadUrl")
fun downLoadImage (view: ImageView,uri: String?){
    view.downloadFromUri(uri, placeholderProgressBar(view.context))
}
