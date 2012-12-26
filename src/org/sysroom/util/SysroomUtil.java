/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysroom.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author lisa
 */
public class SysroomUtil {

    public static TimeZone getTokyoTime() {
        return TimeZone.getTimeZone("Asia/Tokyo");
    }

    public static Calendar getCal() {
        return Calendar.getInstance(getTokyoTime(), Locale.JAPAN);
    }

    public static Date getOriginDay() {
        return getCal().getTime();
        //return cal.getTime();
    }

    public static Date getNewOrigin(Date cur, int n) {
        return DateUtils.addDays(cur, n);
    }

    public static FastDateFormat getFdf() {
        return FastDateFormat.getInstance("yyyy/MM/dd");
    }
    public static FastDateFormat getFdfymdhms() {
        return FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss");
    }
    
    public static Date getDateByString(String sDate, String sf) {
        try {
            return DateUtils.parseDate(sDate, sf);
        } catch (ParseException ex) {
            Logger.getLogger(SysroomUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param Date d
     * @param String sf fotmat
     * @return
     */
    public static String getISO_DATE_Format(Date d) {
        return DateFormatUtils.format(d, DateFormatUtils.ISO_DATETIME_FORMAT.getPattern());
    }

    public static String getStrDate(Date d, String sf) {
        return DateFormatUtils.format(d, sf);
    }

    public static String getStrDate(Calendar d, String sf) {
        return DateFormatUtils.format(d, sf);
    }

    public static long getDaysOfYear(int y) {
        String ymd = "" + y + "1231";
        Date d1 = getDateByString(ymd.trim(), "yyyyMMdd");
        long days = DateUtils.getFragmentInDays(d1, Calendar.YEAR);
        return days;
    }

    public static long getDaysOfYearThis(int ymd) {
        String valueOf = String.valueOf(ymd);
        Date d1 = getDateByString(valueOf.trim(), "yyyyMMdd");
        long days = DateUtils.getFragmentInDays(d1, Calendar.YEAR);
        return days;
    }

    public static long getDaysOfYearRest(int ymd) {
        String valueOf = String.valueOf(ymd);
        String y = valueOf.substring(0, 4);
        long last = getDaysOfYear(Integer.parseInt(y));
        return last - getDaysOfYearThis(ymd);

    }

    public static Date getDateByInt(int ymd) {
        String s = String.valueOf(ymd);
        if (s.length() != 8) {
            return null;
        }
        int y = Integer.valueOf(StringUtils.left(s, 4));
        int m = Integer.valueOf(StringUtils.mid(s, 4, 2));
        int d = Integer.valueOf(StringUtils.right(s, 2));
        Calendar cal = getCal();
        cal.set(y, m - 1, d);
        return cal.getTime();
    }

    public static List<Integer> getRangeDates(int start, int range) {
        List<Integer> lst = new ArrayList<Integer>(range);
        int orgin = start;
        lst.add(start);
        Date d = getDateByInt(orgin);
        for (int i = 0; i < (range - 1); i++) {
            d = DateUtils.addDays(d, 1);

            orgin = Integer.parseInt(SysroomUtil.getStrDate(d, "yyyyMMdd"));
            lst.add(orgin);
        }
        return lst;
    }

    public static int getAddDate(int orgin, int n) {
        Date d = getDateByInt(orgin);
        d = DateUtils.addDays(d, n);
        return Integer.parseInt(getStrDate(d, "yyyyMMdd"));
    }

    public static int[] getStartAndEnd(String s) {
        String[] ab = {"", "", ""};
        ab = StringUtils.split(s, '-');
        int[] rst = new int[3];
        rst[0] = Integer.parseInt(ab[0]);
        rst[1] = Integer.parseInt(ab[1]);
        rst[2] = Integer.parseInt(ab[2]);
        return rst;
    }

    public static String formaty4md(String s) {
        if (s.length() != 8) {
            return s;
        }
        String l = StringUtils.left(s, 4);
        String m = StringUtils.mid(s, 4, 2);
        String r = StringUtils.right(s, 2);
        return (l + "/" + m + "/" + r);
    }

    public static String formaty2md(String s) {
        if (s.length() != 8) {
            return s;
        }
        s = formaty4md(s);

        return (StringUtils.right(s, 8));
    }
    public static String formaty4byDate(Date d){
        int id = dateToInt(d);
        return formaty4md(String.valueOf(id));
    }
    public static String formatmd(String s) {
        if (s.length() != 8) {
            return s;
        }
        s = formaty4md(s);

        return (StringUtils.right(s, 5));
    }

    public static Date setToTime(Date date, Date time) {
        Calendar cs = DateUtils.toCalendar(date);
        
        Calendar ct = DateUtils.toCalendar(time);
        cs.set(Calendar.HOUR_OF_DAY, ct.get(Calendar.HOUR_OF_DAY));
        cs.set(Calendar.MINUTE, ct.get(Calendar.MINUTE));
        return cs.getTime();
    }
    public static int dateToInt(Date d){
        Calendar c = DateUtils.toCalendar(d);
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int dt = c.get(Calendar.DAY_OF_MONTH);
        
        String format = String.format("%1$4d%2$02d%3$02d", y,(m+1),dt);
        //System.out.println("dateToInt(Date d) " + format);
        return Integer.parseInt(format);
    }
    public static int dateToIntTime(Date d){
        Calendar c = DateUtils.toCalendar(d);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        String format = String.format("%1$02d%2$02d", h,m);
        return Integer.parseInt(format);
    }
    public static Date intToDateTime(int tm){
                
        String format = String.format("%1$04d",tm );
        
        String t = format.substring(0,2);
        String m = format.substring(2,4);
        
        
        Calendar c = getCal();
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t));
        c.set(Calendar.MINUTE, Integer.parseInt(m));
        return c.getTime();
        
        
    }
    public static int getYoubiByInt(int d){
        return  getYoubi(getDateByInt(d));
        
    }
    public static int getYoubi(Date d){
        Calendar toCalendar = DateUtils.toCalendar(d);
        int get = toCalendar.get(Calendar.DAY_OF_WEEK);
        //System.out.println("youbi = " + get);
        return get;
    }
    public static String hhmmFormat(int hm){
        //System.out.println("hm= " + hm);
        String valueOf = String.valueOf(hm);
        if(valueOf.length()<4){
            valueOf= "0" + valueOf;
        }
        if(valueOf.length()<4){
            valueOf= "0" + valueOf;
        }
        String l = StringUtils.left(valueOf, 2);
        //System.out.println("l=" + l);
        String r = StringUtils.right(valueOf, 2);
        //System.out.println("r=" + r);
        return (l + ":" + r);
    }
    public static String hhmmssFormat(int hm){
        //System.out.println("hm= " + hm);
        String valueOf = String.valueOf(hm);
        if(valueOf.length()<6){
            valueOf= "0" + valueOf;
        }
        if(valueOf.length()<6){
            valueOf= "0" + valueOf;
        }
        if(valueOf.length()<6){
            valueOf= "0" + valueOf;
        }
        if(valueOf.length()<6){
            valueOf= "0" + valueOf;
        }
        String l = StringUtils.left(valueOf, 2);
        //System.out.println("l=" + l);
        String m = StringUtils.mid(valueOf, 2, 2);
        String r = StringUtils.right(valueOf, 2);
        //System.out.println("r=" + r);
        return (l + ":" + m +":" + r);
    }
    public static String getWayoubi(int i){
        if(i == Calendar.SUNDAY){
            return "（日）";
        }else if(i == Calendar.MONDAY){
            return "（月）";
        }else if(i == Calendar.TUESDAY){
            return "（火）";
        }else if(i == Calendar.WEDNESDAY){
            return "（水）";
        }else if(i== Calendar.THURSDAY){
            return "（木）";
        }else if(i == Calendar.FRIDAY){
            return "(金）";
        }else if(i == Calendar.SATURDAY){
            return "（土）";
        }else{
            return "";
        }
    }
    
    public static int getStringToInt(String s){
        if(StringUtils.isNumeric(s)){
            return Integer.parseInt(s);
        }else{
            return 0;
        }
            
        
    }
    public static String numFormat01(Integer i){
        if(i == 0){
            return " ";
        }
        String format = String.format("%,7d", i);
        return format;
    }
    public static String numFormat02(Integer i){
        
        String format = String.format("%,7d", i);
        return format;
    }
    public static String numFormat01nc(Integer i){
        if(i == 0){
            return " ";
        }
        String format = String.format("%7d", i);
        return format;
    }
    public static String numFormat02nc(Integer i){
        
        String format = String.format("%7d", i);
        return format;
    }
     // this 2012.12.26 last row (314)
}