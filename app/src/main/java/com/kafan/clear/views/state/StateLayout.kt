package com.kafan.clear.views.state

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.Keep
import androidx.annotation.LayoutRes
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ScreenUtils
import com.kafan.clear.R

/**
 * 多状态试图
 */
@Keep
class StateLayout : FrameLayout {
    private val TAG = StateLayout::class.java.simpleName
    private var mContentView: View? = null
    private var mLoadingView: View? = null
    private var mErrorView: View? = null
    private var mEmptyView: View? = null
    private var mNetworkView: View? = null

    @ViewType
    private var mViewType = ViewType.CONTENT
    private var mStateListener: OnStateListener? = null
    private var mRetryListener: OnRetryListener? = null
    private var mLayoutInflater: LayoutInflater? = null

    @LayoutRes
    private var loadingLayoutId = R.layout.state_loading

    @LayoutRes
    private var emptyLayoutId = R.layout.state_empty

    @LayoutRes
    private var errorLayoutId = R.layout.state_error

    @LayoutRes
    private var networkLayoutId = R.layout.state_network
    fun setOnStateListener(listener: OnStateListener?) {
        mStateListener = listener
    }

    interface OnStateListener {
        fun onStateChanged(@ViewType state: Int)
    }

    interface OnRetryListener {
        fun onRetry()
    }

    fun setOnRetryListener(listener: OnRetryListener?) {
        mRetryListener = listener
    }

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet? = null) : super(
        context!!, attrs
    ) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.StateLayout)
            loadingLayoutId =
                a.getResourceId(R.styleable.StateLayout_loadingView, R.layout.state_loading)
            emptyLayoutId =
                a.getResourceId(R.styleable.StateLayout_emptyView, R.layout.state_empty)
            errorLayoutId =
                a.getResourceId(R.styleable.StateLayout_errorView, R.layout.state_error)
            networkLayoutId =
                a.getResourceId(R.styleable.StateLayout_networkView, R.layout.state_network)
            mViewType = a.getInt(R.styleable.StateLayout_viewState, ViewType.CONTENT)

            a.recycle()
        }
        mLayoutInflater = LayoutInflater.from(context)
        addLoadingView()
        addEmptyView()
        addErrorView()
        addNetworkView()
    }

    private fun addLoadingView() {
        mLoadingView = mLayoutInflater!!.inflate(loadingLayoutId, this, false)
        mLoadingView?.let {
            mLoadingView?.visibility = GONE
            addView(it, it.layoutParams)
        }
    }

    private fun addEmptyView() {
        mEmptyView = mLayoutInflater!!.inflate(emptyLayoutId, this, false)
        mEmptyView?.let {
            it.visibility = GONE
            it.findViewById<View>(R.id.empty_hint_text)
                .setOnClickListener {
                    if (mRetryListener != null) {
                        mRetryListener!!.onRetry()
                    }
                }
            val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            params.topMargin = ScreenUtils.getAppScreenHeight() / 2 - ConvertUtils.dp2px(100f)
            addView(it, params)
        }
    }

    private fun addErrorView() {
        mErrorView = mLayoutInflater!!.inflate(errorLayoutId, this, false)
        mErrorView?.let {
            it.visibility = GONE
            it.findViewById<View>(R.id.error_hint_text)
                .setOnClickListener { v: View? ->
                    if (mRetryListener != null) {
                        mRetryListener!!.onRetry()
                    }
                }
            val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            params.topMargin = ScreenUtils.getAppScreenHeight() / 2 - ConvertUtils.dp2px(80f)
            addView(it, params)
        }
    }

    private fun addNetworkView() {
        mNetworkView = mLayoutInflater!!.inflate(networkLayoutId, this, false)
        mNetworkView?.let {
            it.visibility = GONE
            it.findViewById<View>(R.id.network_hint_text_view)
                .setOnClickListener { v: View? ->
                    if (mRetryListener != null) {
                        mRetryListener!!.onRetry()
                    }
                }
            val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            params.topMargin = ScreenUtils.getAppScreenHeight() / 2 - ConvertUtils.dp2px(140f)
            addView(it, params)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        requireNotNull(mContentView) { "Content view is not defined" }
    }

    override fun addView(child: View) {
        getContentView(child)
        super.addView(child)
    }

    override fun addView(child: View, index: Int) {
        getContentView(child)
        super.addView(child, index)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        getContentView(child)
        super.addView(child, index, params)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        getContentView(child)
        super.addView(child, params)
    }

    override fun addView(child: View, width: Int, height: Int) {
        getContentView(child)
        super.addView(child, width, height)
    }

    override fun addViewInLayout(child: View, index: Int, params: ViewGroup.LayoutParams): Boolean {
        getContentView(child)
        return super.addViewInLayout(child, index, params)
    }

    override fun addViewInLayout(
        child: View,
        index: Int,
        params: ViewGroup.LayoutParams,
        preventRequestLayout: Boolean
    ): Boolean {
        getContentView(child)
        return super.addViewInLayout(child, index, params, preventRequestLayout)
    }

    private fun getContentView(child: View) {
        if (!(mContentView != null && mContentView !== child)
            && child !== mLoadingView && child !== mErrorView
            && child !== mEmptyView && child !== mNetworkView
        ) {
            mContentView = child
        }
    }

    private fun switchViewState(@ViewType type: Int) {
        mViewType = type
        if (mLoadingView != null) {
            mLoadingView!!.visibility =
                if (mViewType == ViewType.LOADING) VISIBLE else GONE
        } else {
            throw NullPointerException("Loading View")
        }
        if (mErrorView != null) {
            mErrorView!!.visibility =
                if (mViewType == ViewType.ERROR) VISIBLE else GONE
        } else {
            throw NullPointerException("ErrorView View")
        }
        if (mEmptyView != null) {
            mEmptyView!!.visibility =
                if (mViewType == ViewType.EMPTY) VISIBLE else GONE
        } else {
            throw NullPointerException("EmptyView View")
        }
        if (mNetworkView != null) {
            mNetworkView!!.visibility =
                if (mViewType == ViewType.NETWORK) VISIBLE else GONE
        } else {
            throw NullPointerException("NetworkView View")
        }
        if (mContentView != null) {
            mContentView!!.visibility =
                if (mViewType == ViewType.CONTENT) VISIBLE else GONE
        } else {
            throw NullPointerException("ContentView View")
        }
        if (mStateListener != null) {
            mStateListener!!.onStateChanged(mViewType)
        }
    }

    fun resetStateView(view: View?, @ViewType state: Int, switchToState: Boolean) {
        when (state) {
            ViewType.LOADING -> {
                if (mLoadingView != null) {
                    removeView(mLoadingView)
                }
                mLoadingView = view
                addView(mLoadingView!!)
            }
            ViewType.EMPTY -> {
                if (mEmptyView != null) {
                    removeView(mEmptyView)
                }
                mEmptyView = view
                addView(mEmptyView!!)
            }
            ViewType.ERROR -> {
                if (mErrorView != null) {
                    removeView(mErrorView)
                }
                mErrorView = view
                addView(mErrorView!!)
            }
            ViewType.CONTENT -> {
                if (mContentView != null) {
                    removeView(mContentView)
                }
                mContentView = view
                addView(mContentView!!)
            }
            ViewType.NETWORK -> {
                if (mNetworkView != null) {
                    removeView(mNetworkView)
                }
                mNetworkView = view
                addView(mNetworkView!!)
            }
        }
        switchViewState(ViewType.CONTENT)
        if (switchToState) {
            switchViewState(state)
        }
    }

    fun resetStateView(view: View?, @ViewType type: Int) {
        resetStateView(view, type, false)
    }

    private fun resetStateView(
        @LayoutRes layoutRes: Int,
        @ViewType state: Int,
        switchToState: Boolean
    ) {
        val view = LayoutInflater.from(context).inflate(layoutRes, this, false)
        resetStateView(view, state, switchToState)
    }

    fun resetStateView(@LayoutRes layoutRes: Int, @ViewType state: Int) {
        resetStateView(layoutRes, state, false)
    }

    /**
     * 使用默认的加载视图
     */
    fun showLoading() {
        switchViewState(ViewType.LOADING)
    }

    /**
     * 自定义加载视图显示内容
     *
     * @param message 要显示的内容
     */
    fun showLoading(message: CharSequence?) {
        switchViewState(ViewType.LOADING)
        if (TextUtils.isEmpty(message)) {
            Log.i(TAG, "showLoading: The message is empty, using default")
            return
        }
        try {
            (mLoadingView!!.findViewById<View>(R.id.loading_msg_text) as TextView).text = message
        } catch (e: Exception) {
            Log.e(TAG, "The R.id.loading_msg_text is not found in the custom loading view")
        }
    }

    /**
     * 使用自定义的加载视图id显示内容
     *
     * @param message           要显示的内容
     * @param messageTextViewId 显示TextView控件的id
     */
    fun showLoading(message: CharSequence?, @IdRes messageTextViewId: Int) {
        switchViewState(ViewType.LOADING)
        try {
            (mLoadingView!!.findViewById<View>(messageTextViewId) as TextView).text = message
        } catch (e: Exception) {
            Log.e(TAG, "The $messageTextViewId id is not found in the custom loading view")
        }
    }

    /**
     * 显示内容视图
     */
    fun showContent() {
        switchViewState(ViewType.CONTENT)
    }

    /**
     * 使用默认错误视图显示
     */
    fun showError() {
        switchViewState(ViewType.ERROR)
    }

    /**
     * 使用默认错误视图显示自定义的内容
     *
     * @param message 显示内容
     */
    fun showError(message: CharSequence?) {
        switchViewState(ViewType.ERROR)
        if (TextUtils.isEmpty(message)) {
            Log.i(TAG, "showError: The message is empty, using default")
            return
        }
        try {
            (mErrorView!!.findViewById<View>(R.id.error_msg_text) as TextView).text = message
        } catch (e: Exception) {
            Log.e(TAG, "The R.id.error_msg_text is not found in the custom error view")
        }
    }

    /**
     * 使用自定义的错误视图id显示内容
     *
     * @param message           要显示的内容
     * @param messageTextViewId 显示TextView控件的id
     */
    fun showError(message: CharSequence?, @IdRes messageTextViewId: Int) {
        switchViewState(ViewType.ERROR)
        try {
            (mErrorView!!.findViewById<View>(messageTextViewId) as TextView).text = message
        } catch (e: Exception) {
            Log.e(TAG, "The $messageTextViewId id is not found in the custom error view")
        }
    }

    fun showNetwork() {
        switchViewState(ViewType.NETWORK)
    }

    fun showNetwork(message: CharSequence?) {
        switchViewState(ViewType.NETWORK)
        if (TextUtils.isEmpty(message)) {
            Log.i(TAG, "showNetwork: The message is empty, using default")
            return
        }
        try {
            (mNetworkView!!.findViewById<View>(R.id.network_msg_text_view) as TextView).text =
                message
        } catch (e: Exception) {
            Log.e(TAG, "The R.id.network_msg_text_view is not found in the custom empty view")
        }
    }

    /**
     * 使用自定义的网络视图id显示内容
     *
     * @param message           要显示的内容
     * @param messageTextViewId 显示TextView控件的id
     */
    fun showNetwork(message: CharSequence?, @IdRes messageTextViewId: Int) {
        switchViewState(ViewType.NETWORK)
        try {
            (mNetworkView!!.findViewById<View>(messageTextViewId) as TextView).text = message
        } catch (e: Exception) {
            Log.e(TAG, "The $messageTextViewId id is not found in the custom network view")
        }
    }

    fun showEmpty() {
        switchViewState(ViewType.EMPTY)
    }

    fun showEmpty(message: CharSequence?) {
        switchViewState(ViewType.EMPTY)
        if (TextUtils.isEmpty(message)) {
            Log.i(TAG, "showEmpty: The message is empty, using default")
            return
        }
        try {
            (mEmptyView!!.findViewById<View>(R.id.empty_msg_text) as TextView).text = message
        } catch (e: Exception) {
            Log.e(TAG, "The R.id.empty_msg_text is not found in the custom empty view")
        }
    }

    /**
     * 使用自定义的空视图id显示内容
     *
     * @param message           要显示的内容
     * @param messageTextViewId 显示TextView控件的id
     */
    fun showEmpty(message: CharSequence?, @IdRes messageTextViewId: Int) {
        switchViewState(ViewType.EMPTY)
        try {
            (mEmptyView!!.findViewById<View>(messageTextViewId) as TextView).text = message
        } catch (e: Exception) {
            Log.e(TAG, "The $messageTextViewId id is not found in the custom empty view")
        }
    }

    fun getView(@ViewType state: Int): View? {
        return when (state) {
            ViewType.LOADING -> mLoadingView
            ViewType.CONTENT -> mContentView
            ViewType.EMPTY -> mEmptyView
            ViewType.ERROR -> mErrorView
            ViewType.NETWORK -> mNetworkView
            else -> {
                Log.e(TAG, "error!!!")
                null
            }
        }
    }
}