package com.jelly.thor.x5test.x5

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jelly.thor.x5test.R
import com.jelly.thor.x5test.databinding.AX5WebviewBinding
import com.jelly.thor.x5test.utils.setNewVisibility
import com.tencent.smtt.export.external.extension.interfaces.IX5WebSettingsExtension
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * 类描述：x5内核webView<br/>
 * 创建人：吴冬冬<br/>
 * 创建时间：2020/10/29 15:51 <br/>
 */
class X5WebViewActivity : AppCompatActivity() {
    companion object {
        const val X5_CODE = "http://debugtbs.qq.com"

        @JvmStatic
        @Override
        fun startActivity(context: Context, titleName: String, url: String = X5_CODE) {
            val intent = Intent(context, X5ViewFileReadActivity::class.java)
            intent.putExtra("titleName", titleName)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }

    private val titleName by lazy {
        intent.getStringExtra("titleName") ?: throw IllegalArgumentException("x5WebView界面 未传递标题数据")
    }
    private val urlStr by lazy {
        intent.getStringExtra("url") ?: throw IllegalArgumentException("x5WebView界面 未传递url数据")
    }

    private lateinit var binding: AX5WebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        R.layout.a_x5_webview
        initView()
    }

    private fun initView() {
        initX5()
        binding.x5Wb.loadUrl(urlStr)
    }

    private fun initX5() {
        //设置属性
        binding.x5Wb.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //防止加载网页时调起系统浏览器
                view.loadUrl(url)
                return true
            }
        }
        binding.x5Wb.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, p: Int) {
                super.onProgressChanged(view, p)
                if (p == 100) {
                    binding.pb.setNewVisibility(View.GONE)
                } else {
                    binding.pb.setNewVisibility(View.VISIBLE)
                    binding.pb.progress = p
                }
            }
        }
        binding.x5Wb.settings.apply {
            //设置JS可用
            javaScriptEnabled = true
            //可以访问文件
            allowFileAccess = true
            //缩放可用
            useWideViewPort = true
            setSupportZoom(true)
            displayZoomControls = false //隐藏原生的缩放控件
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS //设置缩放功能 //能不能缩放 取决于网页设置
            loadWithOverviewMode = true
            builtInZoomControls = true


            //可以关闭定位功能，内核默认是开启的
            setGeolocationEnabled(false)
            /*//有图：正常加载显示所有图片
            loadsImagesAutomatically = true
            blockNetworkImage = false*/
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 适配图片加载不出来的问题
            mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
            }*/
        }
        binding.x5Wb.settingsExtension?.apply {
            // 对于刘海屏机器如果webview被遮挡会自动padding
            setDisplayCutoutEnable(true);

            setPicModel(IX5WebSettingsExtension.PicModel_NORMAL)//IX5WebSettingsExtension.PicModel_NORMAL正常加载显示所有图片
        }
        binding.x5Wb.x5WebViewExtension?.apply {
            //竖直快速滑块，设置null可去除
            //setVerticalTrackDrawable(Drawable drawable)
            //判断水平滚动条是否启动
            //isHorizontalScrollBarEnabled()
            //启用或禁用水平滚动条
            //setHorizontalScrollBarEnabled(boolean enabled)
            //判断竖直滚动条是否启动
            //isVerticalScrollBarEnabled()
            //启用或禁用竖直滚动条
            //setVerticalScrollBarEnabled(boolean enabled)
            //设置滚动条渐隐消失的时间段
            //setScrollBarFadeDuration(200)
            //设置滚动条多久开始渐隐消失
            setScrollBarDefaultDelayBeforeFade(200)
        }
    }
}
