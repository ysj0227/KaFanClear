package com.kafan.clear.ui.activity

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.util.Log
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.kafan.clear.R
import com.kafan.clear.base.BaseActivity
import com.kafan.clear.data.User
import com.kafan.clear.databinding.ActivityFlowBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


/**
 * @author ysj
 * @date 2021/11/18
 * @description
 **/
class FlowActivity : BaseActivity<ActivityFlowBinding>() {

    lateinit var flow: Flow<Int>
    lateinit var mFlow: Flow<MutableList<User>>

    override fun getLayoutId(): Int {
        return R.layout.activity_flow
    }

    @InternalCoroutinesApi
    override fun initData() {
        sendFlow()
        CoroutineScope(Dispatchers.Main).launch {
            flow.collect {
                Log.d(TAG, it.toString())
            }
        }

        flow11()
        setTabLayout()
    }

    override fun initView() {

    }

    private var mCountdownJob: Job? = null

    @SuppressLint("SetTextI18n")
    override fun startHttp() {
        mCountdownJob = countDownCoroutines(60, lifecycleScope,
            onTick = { second ->
                mBinding.textView.text = "${second}s后重发"
            }, onStart = {
                // countdown start
            }, onFinish = {
                // countdown finished, reset state
                mBinding.textView.text = "发送验证码"
            })


        mCountdownJob = countdownTimer(100, lifecycleScope,
            onTick = {

            },
            onStart = {

            },
            onFinish = {

            }
        )
    }

    private fun sendFlow() {
        flow = flow {
            (0..10).forEach {
                delay(100)
                Log.d(TAG, "Emitting $it")
                emit(it)
            }
        }.flowOn(Dispatchers.Default)
    }

    private fun mFlow(function: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            flow {
                for (i in 1..5) {
                    delay(100)
                    emit(i)
                }
            }.map {
                it * it
            }.flowOn(Dispatchers.IO)
                .collect {
                    println(it)
                }
        }


    }


    private fun countDownCoroutines(
        total: Int,
        scope: CoroutineScope,
        onTick: (Int) -> Unit,
        onStart: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null,
    ): Job {
        return flow {
            for (i in total downTo 0) {
                emit(i)
                delay(1000)
            }
        }.flowOn(Dispatchers.Main)
            .onStart { onStart?.invoke() }
            .onCompletion { onFinish?.invoke() }
            .onEach { onTick.invoke(it) }
            .launchIn(scope)
    }

    private fun countdownTimer(
        total: Int,
        scope: CoroutineScope,
        onTick: (Int) -> Unit,
        onStart: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ): Job {

        return flow {
            for (i in total downTo 0) {
                emit(i)
                delay(1000)
            }
        }.flowOn(Dispatchers.Main)
            .onStart { onStart?.invoke() }
            .onCompletion { onFinish?.invoke() }
            .onEach { onTick.invoke(it) }
            .launchIn(scope)
    }


    private fun flow11() {
        val str: StringBuffer = StringBuffer()

        val dataList: MutableList<User> = mutableListOf()
        repeat(4) {
            val u = User("$it", "aa $it")
            dataList.add(u)
        }

        mFlow = flow {
            emit(dataList)
        }.flowOn(Dispatchers.IO)
            .onStart {
                Log.d(TAG, "999999 start")
            }.catch {

            }.onCompletion {
                if (it != null) {
                    Log.d(TAG, "999999 onCompletion= " + it.message)
                }
            }
        GlobalScope.launch {
            Log.d(TAG, "999999 thread =" + Thread.currentThread().name)
            mFlow.collect {
                it.iterator().forEach {
                    Log.d(TAG, "999999 collect= ${it.id}")
                }
            }
        }

        lifecycleScope.launch {
            dataList.asFlow().flowOn(Dispatchers.IO)
                .onStart {
                    Log.d(TAG, "999999 start")
                }.catch {

                }.onCompletion {

                }.collect {
                    Log.d(TAG, "999999 collect ${it.id}  ${it.name}")
                    str.append("${it.id}  ${it.name}  \n")

                    mBinding.textView.text = str.toString()
                }
        }


        //多方式组合结果
        lifecycleScope.launch {
            val a = lifecycleScope.async { setInt1() }
            val b = lifecycleScope.async { setInt2() }

            val sum = a.await() + b.await()
            Log.d(TAG, "999999 组合= ----------------------------")
            Log.d(TAG, "999999 组合= $sum")


            val m = lifecycleScope.async { setList1() }
            val n = lifecycleScope.async { setList2() }

            val comlist: MutableList<User> = mutableListOf()
            comlist.addAll(m.await())
            comlist.addAll(n.await())

            comlist.iterator().forEach {
                Log.d(TAG, "999999 组合= ${it.id}  ${it.name}")
            }
        }
    }


    private fun setTabLayout() {
        val tabStr = arrayListOf<String>().apply {
            add("美妆个护")
            add("水果生鲜")
            add("食品饮料")
            add("母婴玩具")
            add("服饰")
            add("美妆")
            add("美妆")
            add("美妆")
            add("美妆")
            add("美妆")
            add("美妆")
            add("美妆")
        }

        //去掉tablayout点击阴影效果
        mBinding.tabLayout.tabRippleColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                this,
                android.R.color.transparent
            )
        )
        mBinding.tabLayout.removeAllTabs()
        tabStr.forEachIndexed { index, value ->
            val tab = mBinding.tabLayout.newTab()
            tab.text = value
            mBinding.tabLayout.addTab(tab, index, index == 1)
        }

        val str1 = 22
        val str2 = 22
        val str3 = "1111144a"
        val str4 = "1111144a"

        Log.d(TAG, "999999 equals=  ${str1 == str2}    ${str3 == str4} ")
        Log.d(TAG, "999999 equals=  ${str3 === str4}    ${str3.equals(str4)}    ${str3.equals(str4, true)}")
    }

    private suspend fun setList1(): MutableList<User> {
        val dataList: MutableList<User> = mutableListOf()
        repeat(2) {
            val u = User("$it", "白云$it")
            dataList.add(u)
        }
        return dataList
    }

    private suspend fun setList2(): MutableList<User> {
        val dataList: MutableList<User> = mutableListOf()

        repeat(2) {
            val u = User("$it", "天空$it")
            dataList.add(u)
        }
        return dataList
    }

    private suspend fun setInt1(): Int {

        return 2
    }

    private suspend fun setInt2(): Int {
        return 4
    }

    private fun button(){

    }

}