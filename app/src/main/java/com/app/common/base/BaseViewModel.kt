package com.app.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.common.data.database.DataBaseClient
import com.app.common.data.network.APIClient
import com.app.common.data.network.APIInterface
import com.app.ui.MyApplication

//import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    //var compositeDis: CompositeDisposable = CompositeDisposable()

    var dao = DataBaseClient.getInstance(MyApplication
        .getInstance())
        .todoDao()

    var api: APIInterface = APIClient.getRetrofit().create(APIInterface::class.java)

    val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean> = _loader

    override fun onCleared() {
        super.onCleared()
        //compositeDis.clear()
    }
}