package com.jelly.thor.x5test.x5

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jelly.thor.x5test.databinding.AX5ViewFileReadBinding
import com.jelly.thor.x5test.utils.setNewVisibility
import com.tencent.smtt.sdk.WebView

/**
 * 类描述： 使用x5自定义view  直接当前界面观看pdf<br></br>
 * 创建人：吴冬冬<br></br>
 * 创建时间：2021/1/15 17:50 <br></br>
 */
class X5ViewFileReadActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun startActivity(context: Context, path: String, titleName: String) {
            val intent = Intent(context, X5ViewFileReadActivity::class.java)
            intent.putExtra("path", path)
            intent.putExtra("titleName", titleName)
            context.startActivity(intent)
        }
    }

    private val path by lazy {
        intent.getStringExtra("path") ?: ""
    }

    private val titleName by lazy {
        intent.getStringExtra("titleName") ?: ""
    }

    private lateinit var binding: AX5ViewFileReadBinding

    public override fun onDestroy() {
        super.onDestroy()
        /*if (tbs_view != null) {
            tbs_view!!.stop()
        }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AX5ViewFileReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val wb = WebView(this)
        val isInstallX5Code = wb.x5WebViewExtension != null
        //Log.d("123===", "x5内核是否安装----：$isInstallX5Code")
        if (isInstallX5Code) {
            binding.tbsView.setNewVisibility(View.VISIBLE)
            binding.tbsView.show(path)
        } else {
            binding.hintTv.setNewVisibility(View.VISIBLE)
            binding.hintTv.setOnClickListener {
                X5WebViewActivity.startActivity(this, "X5内核安装界面", X5WebViewActivity.X5_CODE)
                //直接打开Android自带弹窗 给用户选择 其他程序打开pdf
                //CommonUtils.openPDFInFile(this@X5ViewFileReadActivity, File(path))
            }
        }
    }

}