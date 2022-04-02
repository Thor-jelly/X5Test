package com.jelly.thor.x5test.x5

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.tencent.smtt.sdk.TbsReaderView

/**
 * 类描述：文件阅读器 文件打开核心类 <br></br>
 * 创建人：吴冬冬<br></br>
 * 创建时间：2021/1/15 17:47 <br></br>
 */
class FileReaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), TbsReaderView.ReaderCallback {
    private var tbsReaderView: TbsReaderView?

    init {
        //2、创建TbsReaderView
        tbsReaderView = getTbsReaderView(context)
        //3、将TbsReaderView 添加到RootLayout中（可添加到自定义标题栏的下方）
        this.addView(
            tbsReaderView,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    /**
     * 务必在onDestroy方法中调用此方法，否则第二次打开无法浏览
     */
    fun stop() {
        //7、结束时一定调用onStop方法
        if (tbsReaderView != null) {
            tbsReaderView!!.onStop()
        }
    }


    private fun getTbsReaderView(context: Context): TbsReaderView {
        //1、设置回调
        return TbsReaderView(context, this)
    }

    /**
     * 初始化完布局调用此方法浏览文件
     * @param filePath 文件路径
     */
    fun show(filePath: String) {
        if (!TextUtils.isEmpty(filePath)) {
            //4、传入指定参数
            val localBundle = Bundle()
            localBundle.putString(TbsReaderView.KEY_FILE_PATH, filePath)
            localBundle.putString(
                TbsReaderView.KEY_TEMP_PATH,
                this.context.getExternalFilesDir("temp")!!.absolutePath
            )
            if (tbsReaderView == null) {
                tbsReaderView = getTbsReaderView(context)
            }

            //5、调用preOpen判断是否支持当前文件类型 （若tbs支持的文档类型返回false，则说明内核未加载成功）
            val result = tbsReaderView!!.preOpen(getFileType(filePath), false)
            if (result) {
                //6、调用openFile打开文件
                tbsReaderView!!.openFile(localBundle)
            }
        } else {
            //Log.e("TAG", "文件路径无效！");
        }
    }

    override fun onCallBackAction(integer: Int, o: Any, o1: Any) {
        //回调结果参考 TbsReaderView.ReaderCallback
    }

    /***
     * 获取文件类型
     */
    private fun getFileType(paramString: String): String {
        var str = ""
        if (TextUtils.isEmpty(paramString)) {
            return str
        }
        val i = paramString.lastIndexOf('.')
        if (i <= -1) {
            return str
        }
        str = paramString.substring(i + 1)
        return str
    }
}