package co.stayzeal.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化日期
 * @author ArthorK
 *
 */
public class DateFormatUtil {
	
	private static SimpleDateFormat formater;
	
	public DateFormatUtil(){
		
	}
	/**
	 * 格式化成年月日的形式
	 * @param date
	 * @param outType "yy-MM-dd" or "yy/MM/dd"
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String toYyMmDd(Date date,String outType){
		//formater=(SimpleDateFormat) SimpleDateFormat.getDateInstance();
		formater=new SimpleDateFormat(outType);
		
		return formater.format(date);
	}
}
