package com.app.ui.todocreate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.common.base.BaseViewModel
import com.app.common.data.database.DataBaseClient
import com.app.common.model.RoomModel
import com.app.ui.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoCreateActivityViewModel : BaseViewModel() {

    private val _insert = MutableLiveData<RoomModel>()
    val insert: LiveData<RoomModel> = _insert

    private val _update = MutableLiveData<RoomModel>()
    val update: LiveData<RoomModel> = _update

    fun insert(title: String, salary: Int, desc: String, time: String, type: String) {

        //Coroutine with room database
        viewModelScope.launch(Dispatchers.IO) {
            val roomModel = RoomModel()
            roomModel.setTitle(title)
            roomModel.setSalary(salary)
            roomModel.setDescription(desc)
            roomModel.setTime(time)
            roomModel.setType(type)

            dao.insert(roomModel)
            withContext(Dispatchers.Main) {
                _insert.postValue(roomModel)
            }
        }
        //RxJava
        /* val todoObservable = Observable.create { emitter: ObservableEmitter<RoomModel?> ->
             try {
                 val roomModel = RoomModel()
                 roomModel.setTitle(title)
                 roomModel.setSalary(salary)
                 roomModel.setDescription(desc)
                 roomModel.setTime(time)
                 roomModel.setType(type)

                 DataBaseClient.getInstance(MyApplication.getInstance())
                     .todoDao()
                     .insert(roomModel)

                 emitter.onNext(roomModel)
                 emitter.onComplete()

             } catch (ex: Exception) {
                 emitter.onError(ex)
             }
         }
         compositeDis.add(todoObservable
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe { roomModel: RoomModel? ->
                 _insert.postValue(roomModel)
             })*/
    }

    fun update(
        taskTodo: RoomModel,
        title: String,
        salary: Int,
        desc: String,
        time: String,
        type: String,
    ) {
        //Coroutine
        viewModelScope.launch(Dispatchers.IO) {
            taskTodo.setTitle(title)
            taskTodo.setSalary(salary)
            taskTodo.setDescription(desc)
            taskTodo.setTime(time)
            taskTodo.setType(type)

            dao.update(taskTodo)
            withContext(Dispatchers.Main) {
                _update.postValue(taskTodo)
            }
        }
        //RxJava
        /* val todoObservable = Observable.create { emitter: ObservableEmitter<RoomModel?> ->
             try {
                 taskTodo.setTitle(title)
                 taskTodo.setSalary(salary)
                 taskTodo.setDescription(desc)
                 taskTodo.setTime(time)
                 taskTodo.setType(type)

                 DataBaseClient.getInstance(MyApplication.getInstance())
                     .todoDao()
                     .update(taskTodo)

                 emitter.onNext(taskTodo)
                 emitter.onComplete()

             } catch (ex: Exception) {
                 emitter.onError(ex)
             }
         }
         compositeDis.add(todoObservable
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe { roomModel: RoomModel? ->
                 _update.postValue(roomModel)
             })*/
    }
}