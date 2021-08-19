package com.app.ui.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.common.base.BaseActivity
import com.app.common.model.RoomModel
import com.app.common.utils.AppConstants
import com.app.common.utils.RVEmptyObserver
import com.app.ui.R
import com.app.ui.adapter.RoomAdapter
import com.app.ui.databinding.ActivityTodoListBinding
import com.app.ui.login.LoginActivity
import com.app.ui.todocreate.TodoCreateActivity
import com.thekhaeng.pushdownanim.PushDownAnim

class TodoListActivity : BaseActivity<ActivityTodoListBinding>(R.layout.activity_todo_list) {

    lateinit var roomAdapter: RoomAdapter
    private lateinit var viewModel: TodoListActivityViewModel
    private var taskTodo: RoomModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        viewModel = ViewModelProvider(this)[TodoListActivityViewModel::class.java]
        roomAdapter = RoomAdapter()

        val bundle: Bundle? = intent.extras
        bundle.apply {
            taskTodo = intent.getSerializableExtra(AppConstants.TODOTASK) as RoomModel?
        }
        initView()
        setUpObserver()
        insertTodoBtn()
        logoutBtn()
    }

    private fun logoutBtn() {
        binding.imageLogout.setOnClickListener {
            prefs.preferClear()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun initView() {
        binding.recyclerViewList.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewList.adapter = roomAdapter
        roomAdapter.registerAdapterDataObserver(RVEmptyObserver(binding.txtNoData,
            binding.recyclerViewList))
        roomAdapter.setOnItemClickListener(object : RoomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                taskTodo = roomAdapter.roomModelList[position]
                val intent = Intent(this@TodoListActivity, TodoCreateActivity::class.java);
                intent.putExtra(AppConstants.TODOTASK, taskTodo)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            }

            override fun onDeleteClick(position: Int) {
                taskTodo = roomAdapter.roomModelList[position]
                viewModel.todoDelete(taskTodo!!)
                roomAdapter.roomModelList.removeAt(position)
                roomAdapter.notifyItemRemoved(position)
            }
        })
    }



    private fun setUpObserver() {
        /* viewModel.todoLists.observe(this, {

         })*/

        viewModel.todoLists.observe(this, { list ->
            roomAdapter.addAll(list)
        })

        viewModel.delete.observe(this, {
            //Do something
        })

        viewModel.loader.observe(this, {
            if (it) {
                binding.progressBarList.visibility = View.VISIBLE
            } else {
                binding.progressBarList.visibility = View.GONE
            }
        })
    }

    private fun insertTodoBtn() {
        binding.btnSubmit.setOnClickListener {
            startActivity(Intent(this, TodoCreateActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTodos()
    }
}