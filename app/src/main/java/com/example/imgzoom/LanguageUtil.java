package com.example.imgzoom;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import java.util.Locale;

public class LanguageUtil {

    /**
     * @param isEnglish true  ：点击英文，把中文设置未选中
     *                  false ：点击中文，把英文设置未选中
     */
    public static void set(boolean isEnglish, MainActivity receptionActivity) {
        Configuration configuration = receptionActivity.getResources().getConfiguration();
        DisplayMetrics displayMetrics = receptionActivity.getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文ocale.ENGLISH
            configuration.locale = Locale.ENGLISH;
        } else {
            //设置中文
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        receptionActivity.getResources().updateConfiguration(configuration, displayMetrics);
        receptionActivity.recreate();
    }
}
