package com.jelly.thor.x5test.utils

import android.view.View
import com.jelly.thor.x5test.annotation.Visibility

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/4/2 17:21 <br/>
 */
/**
 * 设置view的显示模式
 */
fun View?.setNewVisibility(@Visibility visibility: Int) {
    if (this == null) {
        return
    }
    if (visibility == this.visibility) {
        return
    }
    this.visibility = visibility
}
