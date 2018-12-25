package com.xuanxuan.csu.util.impl;


import com.xuanxuan.csu.util.DateTranStrategy;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @implNote 实现了抽象策略接口, 使用传统的日期显示方式(前天, 昨天, 今天.....)
 */


@Component
public class OrdinalDateTranStrategyImpl implements DateTranStrategy {

    @Override
    public String conver2Show(Date date) {
        //首先得到系统当前日期
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        //进行判断
        if (date.getYear() == now.getYear()) {
            //如果是同一年
            if (now.getMonth() == date.getMonth() && now.getDay() == date.getDay()) {
                //是今天
                SimpleDateFormat dateFormat = new SimpleDateFormat("今天HH:mm");
                return dateFormat.format(date);
            }
            calendar.add(Calendar.DAY_OF_MONTH, -1);//退后一天
            now = calendar.getTime();
            if (now.getMonth() == date.getMonth() && now.getDay() == date.getDay()) {
                //是昨天
                SimpleDateFormat dateFormat = new SimpleDateFormat("昨天HH:mm");
                return dateFormat.format(date);
            }
            calendar.add(Calendar.DAY_OF_MONTH, -1);//再后退一天
            now = calendar.getTime();
            if (now.getMonth() == date.getMonth() && now.getDay() == date.getDay()) {
                //是前天
                SimpleDateFormat dateFormat = new SimpleDateFormat("前天HH:mm");
                return dateFormat.format(date);
            }
            //不是前天也不是昨天,但是是同一年,则直接显示月份和时间,不显示年份
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日HH:mm");
            return dateFormat.format(date);
        } else {
            //如果不是同一年,则显示全部时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
            return dateFormat.format(date);
        }
    }
}
