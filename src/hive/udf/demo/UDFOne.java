package hive.udf.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.hive.ql.exec.UDF;

public class UDFOne extends UDF {//继承UDF，必须实现evaluate函数
    public String evaluate(Date date, int type) {
        if (type == 0) {// 生肖
            return getZodica(date);
        } else if (type == 1) {// 星座
            return getConstellation(date);
        } else {
            return "NULL";
        }
    }
    
    public String evaluate(String date,int type) throws ParseException{
    	SimpleDateFormat df=new SimpleDateFormat("MM-dd-yyyy");
    	Date date2=df.parse(date);
    	return evaluate(date2, type);
    }

    public final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔","龙", "蛇", "马", "羊" };
    public final String[] constellationArr = { "水瓶座", "双鱼座", "白羊座", "金牛座","双子座", "巨蟹座", "狮子座", "处女座", 
            "天秤座", "天蝎座", "射手座", "魔羯座" };
    public final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23,    23, 23, 22, 22 };

    /**
     * 根据日期获取生肖
     */
    public String getZodica(java.util.Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return zodiacArr[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 根据日期获取星座
     */
    public String getConstellation(java.util.Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationArr[month];
        }
        // default to return 魔羯
        return constellationArr[11];
    }
    

}