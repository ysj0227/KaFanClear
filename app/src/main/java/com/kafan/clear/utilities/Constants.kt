//注意这里，它要放到 package 的前面
@file:JvmName("Constants")

package com.kafan.clear.utilities

/**
 * Constants used throughout the app.
 */
const val DATABASE_NAME = "sunflower-db"
const val PLANT_DATA_FILENAME = "plants.json"

const val BASE_URL = ""

const val ARTICLE_WEBSITE = "article"

const val HAS_NETWORK_KEY = "has_network"
const val IS_LOGIN = "isLogin"
// 拍照
const val IMAGE_CAPTURE = 1
// 从相册选择
const val IMAGE_SELECT = 2
// 照片缩小比例
const val SCALE = 3

const val HEAD_PIC_PATH = "image.jpg"
const val MY_BG_PIC_PATH = "bgImage"
const val SCORE_UNM = "score_num"
const val TOOLBAR_HEIGHT = 44

//http
const val DEFAULT_TIMEOUT: Long = 30
const val MAX_CACHE_SIZE: Long = 1024 * 1024 * 50 // 50M 的缓存大小
const val SET_COOKIE_KEY = "set-cookie"
const val COOKIE_NAME = "Cookie"
const val SAVE_USER_LOGIN_KEY = "user/login"
const val SAVE_USER_REGISTER_KEY = "user/register"

