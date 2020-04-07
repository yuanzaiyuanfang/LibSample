package com.yzyfdf.library.view.globalloading

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.yzyfdf.library.view.globalloading.Gloading.Adapter

/**
 * manage loading status view
 * <pre>
 * usage:
 * //if set true, logs will print into logcat
 * Gloading.debug(trueOrFalse);
 * //init the default loading status view creator ([Adapter])
 * Gloading.initDefault(adapter);
 * //wrap an activity. return the holder
 * Holder holder = Gloading.getDefault().wrap(activity);
 * //wrap an activity and set retry task. return the holder
 * Holder holder = Gloading.getDefault().wrap(activity).withRetry(retryTask);
 *
 * holder.showLoading() //show loading status view by holder
 * holder.showLoadSuccess() //show load success status view by holder (frequently, hide gloading)
 * holder.showFailed() //show load failed status view by holder (frequently, needs retry task)
 * holder.showEmpty() //show empty status view by holder. (load completed, but data is empty)
</pre> *
 *
 * @author billy.qi
 * @since 19/3/18 17:49
 */
class Gloading private constructor() {
    private var mAdapter: Adapter? = null

    /**
     * Status view to show current page loading status
     */
    interface Adapter {
        /**
         * get view for current status
         *
         * @param holder      Holder
         * @param convertView The old view to reuse, if possible.
         * @param status      current status
         * @param icon
         * @param str
         * @return status view to show. Maybe convertView for reuse.
         * @see Holder
         */
        fun getView(holder: Holder, convertView: View?, status: Int, icon: Int, str: Int): View?
    }

    /**
     * Gloading(loading status view) wrap the whole activity
     * wrapper is android.R.id.content
     *
     * @param activity current activity object
     * @return holder of Gloading
     */
    fun wrap(activity: Activity): Holder {
        val wrapper = activity.findViewById<ViewGroup>(android.R.id.content)
        return Holder(mAdapter, activity, wrapper)
    }

    /**
     * Gloading(loading status view) wrap the specific view.
     *
     * @param view view to be wrapped
     * @return Holder
     */
    fun wrap(view: View): Holder {
        val wrapper = FrameLayout(view.context)
        val lp = view.layoutParams
        if (lp != null) {
            wrapper.layoutParams = lp
        }
        if (view.parent != null) {
            val parent = view.parent as ViewGroup
            val index = parent.indexOfChild(view)
            parent.removeView(view)
            parent.addView(wrapper, index)
        }
        val newLp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        wrapper.addView(view, newLp)
        return Holder(mAdapter, view.context, wrapper)
    }

    /**
     * Gloading holder
     * create by [Gloading.wrap] or [Gloading.wrap]
     * the core API for showing all status view
     */
    class Holder constructor(
        private val mAdapter: Adapter?, val context: Context,
        /**
         * get wrapper
         *
         * @return container of gloading
         */
        val wrapper: ViewGroup?
    ) {
        /**
         * get retry task
         *
         * @return retry task
         */
        lateinit var retryTask: () -> Unit

        private var mCurStatusView: View? = null
        private var curState: Int = 0
        private val mStatusViews = SparseArray<View>(4)
        private var mData: Any? = null

        /**
         * set retry task when user click the retry button in load failed page
         *
         * @param task when user click in load failed UI, run this task
         * @return this
         */
        fun withRetry(task: () -> Unit): Holder {
            retryTask = task
            return this
        }

        /**
         * set extension data. maybe we need this data within [Adapter.getView]
         *
         * @param data extension data
         * @return this
         */
        fun withData(data: Any): Holder {
            this.mData = data
            return this
        }

        /**
         * show UI for status: [.STATUS_LOADING]
         */
        fun showLoading() {
            showLoadingStatus(STATUS_LOADING)
        }

        /**
         * show UI for status: [.STATUS_LOAD_SUCCESS]
         */
        fun showLoadSuccess() {
            showLoadingStatus(STATUS_LOAD_SUCCESS)
        }

        /**
         * show UI for status: [.STATUS_LOAD_FAILED]
         */
        fun showLoadFailed(@DrawableRes icon: Int = 0, @StringRes str: Int = 0) {
            showLoadingStatus(STATUS_LOAD_FAILED, icon, str)
        }

        /**
         * show UI for status: [.STATUS_EMPTY_DATA]
         */
        fun showEmpty(@DrawableRes icon: Int = 0, @StringRes str: Int = 0) {
            showLoadingStatus(STATUS_EMPTY_DATA, icon, str)
        }

        /**
         * Show specific status UI
         *
         * @param status status
         * @see .showLoading
         * @see .showLoadFailed
         * @see .showLoadSuccess
         * @see .showEmpty
         */
        private fun showLoadingStatus(
            status: Int, @DrawableRes icon: Int = 0, @StringRes str: Int = 0
        ) {
            if (curState == status || !validate()) {
                return
            }
            curState = status
            //first try to reuse status view
            var convertView: View? = mStatusViews.get(status)
            if (convertView == null) {
                //secondly try to reuse current status view
                convertView = mCurStatusView
            }
            try {
                //call customer adapter to get UI for specific status. convertView can be reused
                val view = mAdapter!!.getView(this, convertView, status, icon, str)
                if (view == null) {
                    printLog(mAdapter.javaClass.name + ".getView returns null")
                    return
                }
                if (view !== mCurStatusView || wrapper!!.indexOfChild(view) < 0) {
                    if (mCurStatusView != null) {
                        wrapper!!.removeView(mCurStatusView)
                    }
                    wrapper!!.addView(view)
                    val lp = view.layoutParams
                    if (lp != null) {
                        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT
                    }
                } else if (wrapper.indexOfChild(view) != wrapper.childCount - 1) {
                    // make sure loading status view at the front
                    view.bringToFront()
                }
                mCurStatusView = view
                mStatusViews.put(status, view)
            } catch (e: Exception) {
                if (DEBUG) {
                    e.printStackTrace()
                }
            }

        }

        private fun validate(): Boolean {
            if (mAdapter == null) {
                printLog("Gloading.Adapter is not specified.")
            }
            if (context == null) {
                printLog("Context is null.")
            }
            if (wrapper == null) {
                printLog("The mWrapper of loading status view is null.")
            }
            return mAdapter != null && context != null && wrapper != null
        }

        /**
         * get extension data
         *
         * @param <T> return type
         * @return data
        </T> */
        fun <T> getData(): T? {
            try {
                return mData as T?
            } catch (e: Exception) {
                if (DEBUG) {
                    e.printStackTrace()
                }
            }

            return null
        }
    }

    companion object {
        val STATUS_LOADING = 1
        val STATUS_LOAD_SUCCESS = 2
        val STATUS_LOAD_FAILED = 3
        val STATUS_EMPTY_DATA = 4

        private var DEBUG = false

        /**
         * set debug mode or not
         *
         * @param debug true:debug mode, false:not debug mode
         */
        fun debug(debug: Boolean) {
            DEBUG = debug
        }

        /**
         * Create a new Gloading different from the default one
         *
         * @param adapter another adapter different from the default one
         * @return Gloading
         */
        fun from(adapter: Adapter): Gloading {
            val gloading = Gloading()
            gloading.mAdapter = adapter
            return gloading
        }

        /**
         * get default Gloading object for global usage in whole app
         *
         * @return default Gloading object
         */
        val default: Gloading by lazy {
            Gloading()
        }

        /**
         * init the default loading status view creator ([Adapter])
         *
         * @param adapter adapter to create all status views
         */
        fun initDefault(adapter: Adapter) {
            default.mAdapter = adapter
        }

        private fun printLog(msg: String) {
            if (DEBUG) {
                Log.e("Gloading", msg)
            }
        }
    }
}
