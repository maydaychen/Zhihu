package com.example.administrator.zhihu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by JTR on 2016/8/23.
 */
public class Config {
    public final static String TAG = "CacheActivity1";

    public static final Date nowdate = new Date();
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public static final String today = sdf.format(nowdate);
}
