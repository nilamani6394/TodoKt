package com.app.common.base

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.app.common.data.prefs.PrefsHelper
import com.app.ui.MyApplication

abstract class BaseActivity<T : ViewDataBinding>(private val getLayout: Int) : AppCompatActivity() {
    protected lateinit var binding: T
    protected lateinit var prefs: PrefsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayout)
        prefs = MyApplication.getInstance().getPrefs()
    }

    fun showToast(mess: String) {
        Toast.makeText(this, mess, Toast.LENGTH_LONG).show();
    }
}