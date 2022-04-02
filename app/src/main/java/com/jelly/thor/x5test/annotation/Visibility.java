package com.jelly.thor.x5test.annotation;

import android.view.View;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 类描述：显示隐藏注解<br/>
 * 创建人：吴冬冬<br/>
 * 创建时间：2019/6/27 10:56 <br/>
 */
@IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
@Retention(RetentionPolicy.SOURCE)
public @interface Visibility {
}