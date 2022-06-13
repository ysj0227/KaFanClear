package com.kafan.clear.ui

/**
 * @author ysj
 * @date 2022/1/11
 * @description
 **/
object ColorMatrixUtils {

    // 黑白
    val heibai = floatArrayOf(
        0.8f, 1.6f, 0.2f, 0f,
        -163.9f, 0.8f, 1.6f, 0.2f, 0f, -163.9f, 0.8f, 1.6f, 0.2f, 0f,
        -163.9f, 0f, 0f, 0f, 1.0f, 0f
    )

    // 怀旧
    val huaijiu = floatArrayOf(
        0.2f, 0.5f, 0.1f, 0f,
        40.8f, 0.2f, 0.5f, 0.1f, 0f, 40.8f, 0.2f, 0.5f, 0.1f, 0f, 40.8f, 0f, 0f, 0f, 1f, 0f
    )

    // 哥特
    val gete = floatArrayOf(
        1.9f, -0.3f, -0.2f, 0f,
        -87.0f, -0.2f, 1.7f, -0.1f, 0f, -87.0f, -0.1f, -0.6f, 2.0f, 0f,
        -87.0f, 0f, 0f, 0f, 1.0f, 0f
    )

    // 淡雅
    val danya = floatArrayOf(
        0.6f, 0.3f, 0.1f, 0f,
        73.3f, 0.2f, 0.7f, 0.1f, 0f, 73.3f, 0.2f, 0.3f, 0.4f, 0f, 73.3f, 0f, 0f, 0f, 1.0f, 0f
    )

    // 蓝调
    val landiao = floatArrayOf(
        2.1f, -1.4f, 0.6f,
        0.0f, -71.0f, -0.3f, 2.0f, -0.3f, 0.0f, -71.0f, -1.1f, -0.2f, 2.6f,
        0.0f, -71.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f
    )

    // 光晕
    val guangyun =
        floatArrayOf(0.9f, 0f, 0f, 0f, 64.9f, 0f, 0.9f, 0f, 0f, 64.9f, 0f, 0f, 0.9f, 0f, 64.9f, 0f, 0f, 0f, 1.0f, 0f)

    // 梦幻
    val menghuan = floatArrayOf(
        0.8f, 0.3f, 0.1f,
        0.0f, 46.5f, 0.1f, 0.9f, 0.0f, 0.0f, 46.5f, 0.1f, 0.3f, 0.7f, 0.0f,
        46.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f
    )

    // 酒红
    val jiuhong = floatArrayOf(
        1.2f, 0.0f, 0.0f, 0.0f,
        0.0f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0f, 0f, 0f, 1.0f, 0f
    )

    // 胶片
    val fanse = floatArrayOf(
        -1.0f, 0.0f, 0.0f, 0.0f,
        255.0f, 0.0f, -1.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, -1.0f, 0.0f,
        255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f
    )

    // 湖光掠影
    val huguang = floatArrayOf(
        0.8f, 0.0f, 0.0f, 0.0f,
        0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f, 0f, 0f, 0f, 1.0f, 0f
    )

    // 褐片
    val hepian = floatArrayOf(
        1.0f, 0.0f, 0.0f, 0.0f,
        0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0f, 0f, 0f, 1.0f, 0f
    )

    // 复古
    val fugu = floatArrayOf(
        0.9f, 0.0f, 0.0f, 0.0f,
        0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0f, 0f, 0f, 1.0f, 0f
    )

    // 泛黄
    val huan_huang = floatArrayOf(
        1.0f, 0.0f, 0.0f,
        0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f,
        0.0f, 0f, 0f, 0f, 1.0f, 0f
    )

    // 传统
    val chuan_tong = floatArrayOf(
        1.0f, 0.0f, 0.0f, 0f,
        -10f, 0.0f, 1.0f, 0.0f, 0f, -10f, 0.0f, 0.0f, 1.0f, 0f, -10f, 0f, 0f, 0f, 1f, 0f
    )

    // 胶片2
    val jiao_pian = floatArrayOf(
        0.71f, 0.2f, 0.0f,
        0.0f, 60.0f, 0.0f, 0.94f, 0.0f, 0.0f, 60.0f, 0.0f, 0.0f, 0.62f,
        0.0f, 60.0f, 0f, 0f, 0f, 1.0f, 0f
    )

    // 锐色
    val ruise = floatArrayOf(
        4.8f, -1.0f, -0.1f, 0f,
        -388.4f, -0.5f, 4.4f, -0.1f, 0f, -388.4f, -0.5f, -1.0f, 5.2f, 0f,
        -388.4f, 0f, 0f, 0f, 1.0f, 0f
    )

    // 清宁
    val qingning = floatArrayOf(
        0.9f, 0f, 0f, 0f, 0f, 0f,
        1.1f, 0f, 0f, 0f, 0f, 0f, 0.9f, 0f, 0f, 0f, 0f, 0f, 1.0f, 0f
    )

    // 浪漫
    val langman =
        floatArrayOf(0.9f, 0f, 0f, 0f, 63.0f, 0f, 0.9f, 0f, 0f, 63.0f, 0f, 0f, 0.9f, 0f, 63.0f, 0f, 0f, 0f, 1.0f, 0f)

    // 夜色
    val yese = floatArrayOf(
        1.0f, 0.0f, 0.0f, 0.0f,
        -66.6f, 0.0f, 1.1f, 0.0f, 0.0f, -66.6f, 0.0f, 0.0f, 1.0f, 0.0f,
        -66.6f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f
    )
}