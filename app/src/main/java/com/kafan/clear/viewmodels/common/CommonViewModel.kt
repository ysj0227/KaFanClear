package com.kafan.clear.viewmodels.common

import com.kafan.clear.api.ApiRepository
import com.kafan.clear.base.BaseViewModel

/**
 * Creator： wq
 * Date：2021-06-24
 * Time: 14:53
 * <p/>
 * Description:通用的ViewModel,如登录等接口很多页面都用
 */

open class CommonViewModel : BaseViewModel() {

    protected val repository = ApiRepository()

}