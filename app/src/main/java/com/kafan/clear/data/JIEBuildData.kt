package com.kafan.clear.data

/**
 * @author ysj
 * @date 2021/11/19
 * @description
 **/
class JIEBuildData(var name: String, var age: Int) {

    operator fun component1() = name

    operator fun component2() = age
}
