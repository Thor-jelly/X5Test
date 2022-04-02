package com.jelly.thor.x5test.x5

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tencent.smtt.sdk.QbSdk

/**
 * 类描述：x5文件打开 <br/>
 * （1）调用之后，优先调起 QQ 浏览器打开文件。
 *      如果没有安装 QQ 浏览器，在 X5 内核下调起简版 QB 打开文件。
 *      如果使用的系统内核，则调起文件阅读器弹框。
 * （2）暂时只支持本地文件打开
 * https://x5.tencent.com/docs/access.html
 * 创建人：吴冬冬<br/>
 * 创建时间：2021/1/15 14:58 <br/>
 */
class X5FileOpenActivity : AppCompatActivity() {
    companion object {
        /**
         * @param filePath filePath: 文件路径。格式为 android 本地存储路径格式，例如:/sdcard/Download/xxx.doc. 不支持 file:/// 格式。暂不支持在线文件。
         */
        @JvmStatic
        fun startActivity(activity: AppCompatActivity, filePath: String) {
            val intent = Intent(activity, X5FileOpenActivity::class.java)
            intent.putExtra("path", filePath)
            activity.startActivity(intent)
        }
    }

    private val path by lazy {
        intent.getStringExtra("path") ?: throw IllegalArgumentException("未传递文件路径")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val params = HashMap<String, String>()
        // 表示是进入文件查看器，如果不设置或设置为“false”，则进入miniqb浏览器模式。不是必须设置项
        params["local"] = "false"
        // 0”表示文件查看器使用默认的UI样式。“1”表示文件查看器使用微信的UI样式。不设置此key或设置错误值，则为默认UI样式。
        params["style"] = "1"
        // 定制文件查看器的顶部栏背景色。格式为“#xxxxxx”，例“#2CFC47”;不设置此key或设置错误值，则为默认UI样式。
        params["topBarBgColor"] = "#f00";
        //该参数用来定制文件右上角弹出菜单，可传入菜单项的icon的文本，用户点击菜单项后，sdk会通过startActivity+intent的方式回调。menuData是jsonObject类型，结构格式如下
        //params["memuData"] = jsondata;
        /*
         jsondata =
            "{
            pkgName:\"com.example.thirdfile\", "
            + "className:\"com.example.thirdfile.IntentActivity\","
            + "thirdCtx: {pp:123},"
            + "menuItems:"
            + "["
            + "{id:0,iconResId:"+ R.drawable.ic_launcher +",text:\"menu0\"},
            {id:1,iconResId:" + R.drawable.bookmark_edit_icon + ",text:\"menu1\"},
             {id:2,iconResId:"+ R.drawable.bookmark_folder_icon +",text:\"菜单2\"}" + "]"
            +"
            }";

          pkgName：回调的包名
          className：回调的类名
          thirdCtx：App定义参数，需要是 jsonObject 类型，sdk 不会处理该参数，只是在菜单点击事件发生的时候原样 回传给调用方。
          menuItems：son 数组，表示菜单中的每一项。
         */

        /*
        （1）此方法在Qbsdk类下
        （2）调用之后，优先调起 QQ 浏览器打开文件。如果没有安装 QQ 浏览器，在 X5 内核下调起简版 QB 打开文 件。如果使用的系统内核，则调起文件阅读器弹框。
        （3）暂时只支持本地文件打开
         */
        QbSdk.openFileReader(this, path, params) {
            //您可以在出现以下回调时结束您的进程，节约内存占用
            if (isNeedClose(it)) {
                QbSdk.closeFileReader(this);
                finish()
            }
        }
    }

    //是否需要关闭
    private fun isNeedClose(s: String): Boolean {
        return when (s) {
            "openFileReader open in QB",
            "filepath error",
            "TbsReaderDialogClosed",
            "default browser",
            "fileReaderClosed" -> true
            else -> false
        }
    }
}
