package hive.udf.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.hive.ql.exec.UDF;

public class UDFOne extends UDF {//�̳�UDF������ʵ��evaluate����
    public String evaluate(Date date, int type) {
        if (type == 0) {// ��Ф
            return getZodica(date);
        } else if (type == 1) {// ����
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

    public final String[] zodiacArr = { "��", "��", "��", "��", "��", "ţ", "��", "��","��", "��", "��", "��" };
    public final String[] constellationArr = { "ˮƿ��", "˫����", "������", "��ţ��","˫����", "��з��", "ʨ����", "��Ů��", 
            "�����", "��Ы��", "������", "ħ����" };
    public final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23,    23, 23, 22, 22 };

    /**
     * �������ڻ�ȡ��Ф
     */
    public String getZodica(java.util.Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return zodiacArr[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * �������ڻ�ȡ����
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
        // default to return ħ��
        return constellationArr[11];
    }
    

}