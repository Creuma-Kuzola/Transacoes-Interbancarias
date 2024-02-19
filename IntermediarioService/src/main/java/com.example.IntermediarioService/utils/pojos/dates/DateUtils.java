package com.example.IntermerdiarioService.utils.pojos.dates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {


    public static Date formatterDateTimeToStr() throws ParseException {
        SimpleDateFormat dateFormatGmy = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatGmy.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormatLocal.parse(dateFormatGmy.format(new Date()));
    }

    public static Date formatterDateTimeToStr(Date date) throws ParseException {
        SimpleDateFormat dateFormatGmy = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatGmy.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormatLocal.parse(dateFormatGmy.format(date));
    }
}
