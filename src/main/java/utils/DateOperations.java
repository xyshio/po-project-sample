package eu.ets.eucr.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
//import org.apache.james.mime4j.field.datetime.DateTime;
//import org.apache.poi.ss.usermodel.DateUtil;

public class DateOperations {

    static Calendar localCalendar 	= Calendar.getInstance(TimeZone.getDefault());
    final static Date currentTime 		= localCalendar.getTime();
    final static int currentDay 		= localCalendar.get(Calendar.DATE);
    final static int currentMonth 		= localCalendar.get(Calendar.MONTH) + 1;
    final static int currentYear 		= localCalendar.get(Calendar.YEAR);
    final static int currentDayOfWeek 	= localCalendar.get(Calendar.DAY_OF_WEEK);
    final static int currentDayOfMonth 	= localCalendar.get(Calendar.DAY_OF_MONTH);
    final static int CurrentDayOfYear 	= localCalendar.get(Calendar.DAY_OF_YEAR);

	
    public static String GET_DATE_DAY(){
    	return date_get_NOW_IN_DateFormat_DD_MM_YYY();
    }
	public static String date_GetDay() {
        return Integer.toString(currentDay); 
	}

	public static String date_GetDay(int zeroCounted){
		String m=date_GetDay();
		if(m.length()==2){
			return m;	
		}else{
			return "0"+m;
		}

	
	}
	public static String date_GetCurrentMonth(){
		return Integer.toString(currentMonth);
	}
	public static String date_GetCurrentMonth(int zeroCounted){
		String d=date_GetCurrentMonth();
		if(d.length()==2){
			return d;	
		}else{
			return "0"+d;
		}
	}

	public static String date_GetCurrentYear(){
        return Integer.toString(currentYear); 
	}
	public static String GET_YEAR_CURRENT(){
        return Integer.toString(currentYear); 
	}
	
	public static String GET_YEAR_PREVIOUS(){
		return GET_YEAR_PREVIOUS(0);
	}
	
	public static String GET_YEAR_PREVIOUS(int howMuch){
		if(howMuch==0 || howMuch==1){
			return date_GetYearBeforecurrent();	
		}else{
			return date_YEAR_SUB_TO_CURENT(howMuch);
		}
	}
	
	
	public static String date_GetYearBeforecurrent(){
        return Integer.toString(currentYear - 1); 
	}
	public static String date_GetTwoYearsBeforecurrent(){
        return Integer.toString(currentYear - 2); 
	}
	public static String date_GetNextYearAfterCurrent(){
        return Integer.toString(currentYear + 1); 
	}
	public static String date_YEAR_ADD_TO_CURENT(int howManyYearsToAdd){
        return Integer.toString(currentYear + howManyYearsToAdd); 
	}
	public static String date_YEAR_SUB_TO_CURENT(int howManyYearsToAdd){
        return Integer.toString(currentYear - howManyYearsToAdd); 
	}
	
	public static String get_NEXT_YEAR(int howManyYearsToAdd){
        return Integer.toString(currentYear + howManyYearsToAdd); 
	}
	
	
	
	public static String date_getDateInFormat_422(){ /*yyyy-MM-dd*/
//usage:	      DateOperations.date_getDateInFormat_422();
		//  "yyyy-MM-dd HH:mm:ss"
//		return date_GetCurrentYear()+"-"+date_GetCurrentMonth(0)+"-"+date_GetDay(0);
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		    Date now = new Date();
		    return sdfDate.format(now);
	}

	public static String date_get_NOW_IN_DateFormat_DD_MM_YYY(){ /*yyyy-MM-dd*/
				SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyy");
				    Date now = new Date();
				    String s = sdfDate.format(  now );
					return s.toUpperCase();
		}

	public static String date_get_NOW_TIME_IN_Format_HH_MM_SS(){ /*14:57:34*/ // Twilio Compliant 
		SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
		    Date now = new Date();
		    String s = sdfDate.format(  now );
			return s.toUpperCase();
}	
	
	public static String date_get_NOW_IN_DateFormat_2MON2(){ /*yyyy-MM-dd*/
		SimpleDateFormat sdfDate = new SimpleDateFormat("ddMMMyy");
		    Date now = new Date();
		    String s = sdfDate.format(  now );
			return s.toUpperCase();
}	
	public static String date_SUBSTR_DAYS_FROM_NOW_getDateFormat_2MON2(int howManyDaysSubstr){ /*yyyy-MM-dd*/
		SimpleDateFormat sdfDate = new SimpleDateFormat("ddMMMyy");
		    Date now = new Date();
		    Date substracted = DateUtils.addDays(now, -howManyDaysSubstr);
		    String s = sdfDate.format(  substracted );
			return s.toUpperCase();
	}
	public static String date_DIGIT(){ /*yyyy-MM-dd*/
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMddHHmmss");
		    Date now = new Date();
		    
		    String s = sdfDate.format(  now );
			return s.toUpperCase();
	}
	public static String time_DIGIT(){ /*yyyy-MM-dd*/
		SimpleDateFormat sdfDate = new SimpleDateFormat("HHmmss");
		    Date now = new Date();
		    
		    String s = sdfDate.format(  now );
			return s.toUpperCase();
	}

	public static String date_ADD_DAYS_TO_NOW_getDateFormat_2MON2(int howManyDaysSubstr){ /*dd-MMM-yy*/
		SimpleDateFormat sdfDate = new SimpleDateFormat("ddMMMyy");
		    Date now = new Date();
		    Date substracted = DateUtils.addDays(now, howManyDaysSubstr);
		    String s = sdfDate.format(  substracted );
			return s.toUpperCase();
	}
	
	public static String date_get_DATE_TODAY_IN_DateFormat_GLUED_224(){ /*dd/MM/yyyy*/
		SimpleDateFormat sdfDate = new SimpleDateFormat("ddMMyyyy");
		    Date now = new Date();
		    String s = sdfDate.format(  now );
			return s.toUpperCase();
}
public static String date_get_NOW_IN_DateFormat_SLASHED_224(){ /*dd/MM/yyyy*/
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
		    Date now = new Date();
		    String s = sdfDate.format(  now );
			return s.toUpperCase();
}
public static String date_get_NOW_IN_DateTimeFormat_SLASHED_224(){ /*dd/MM/yyyy HH:mm:ss*/ //24/10/2017 15:21:13
	SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    Date now = new Date();
	    String s = sdfDate.format(  now );
		return s.toUpperCase();
}


public static String date_get_NOW_IN_DateFormat_SLASHED_224(int howManyDays){ /*dd/MM/yyyy*/
	SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
    Date now = new Date();
    Date substracted = DateUtils.addDays(now, howManyDays);
    String s = sdfDate.format(  substracted );
	return s.toUpperCase();
}

public static String DATE_SLASHED_224_NOW(){
	return DateOperations.date_get_NOW_IN_DateFormat_SLASHED_224(0);
}
public static String DATE_SLASHED_224_ADD(int howMany){
	return DateOperations.date_get_NOW_IN_DateFormat_SLASHED_224(howMany);
}
public static String DATE_SLASHED_224_SUBSTR(int howMany){
	return DateOperations.date_get_NOW_IN_DateFormat_SLASHED_224(-howMany);
}

public static String DATETIME_GET_NOW_IN_DATETIME_DASHED_422_222(){ /* [2015-04-30 13-30-00]  */
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	    Date now = new Date();
	    String s = sdfDate.format(  now );
		return s.toUpperCase();
}

// FORMAT: 2MON2: 01-MAR-12
public static String date_get_NOW_IN_DateFormat_2MON2_DASHED(){ /*01-MAR-12*/
	SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yy");
	    Date now = new Date();
	    String s = sdfDate.format(  now );
		return s.toUpperCase();
}
public static String date_ADD_DAYS_TO_NOW_getDateFormat_2MON2_DASHED(int howManyDaysSubstr){
	String NOW_DATE = date_get_NOW_IN_DateFormat_2MON2_DASHED();
	return date_ADD_DAYS_TO_NOW_getDateFormat_2MON2_DASHED(howManyDaysSubstr, NOW_DATE);
}
public static String date_ADD_DAYS_TO_NOW_getDateFormat_2MON2_DASHED(int howManyDaysSubstr, String FROM_WHICH_DATE){ /*dd-MMM-yy*/
	SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yy");
	    Date now = new Date(FROM_WHICH_DATE);
	    Date substracted = DateUtils.addDays(now, howManyDaysSubstr);
	    String s = sdfDate.format(  substracted );
		return s.toUpperCase();
}
public static String date_SUBSTR_DAYS_TO_NOW_getDateFormat_2MON2_DASHED(int howManyDaysSubstr){
	String NOW_DATE = date_get_NOW_IN_DateFormat_2MON2_DASHED();
	return date_SUBSTR_DAYS_TO_NOW_getDateFormat_2MON2_DASHED(howManyDaysSubstr, NOW_DATE);
}
public static String date_SUBSTR_DAYS_TO_NOW_getDateFormat_2MON2_DASHED(int howManyDaysSubstr, String FROM_WHICH_DATE){ /*dd-MMM-yy*/
	SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yy");
	    Date now = new Date(FROM_WHICH_DATE);
	    Date substracted = DateUtils.addDays(now, -howManyDaysSubstr);
	    String s = sdfDate.format(  substracted );
		return s.toUpperCase();
}

public static String DASH_ADD_DAYS_TO_DATE(int HowManydays, String DateFrom){
	return date_ADD_DAYS_TO_NOW_getDateFormat_2MON2_DASHED(HowManydays, DateFrom);
}
public static String DASH_SUB_DAYS_FROM_DATE(int HowManydays, String DateFrom){
	return date_SUBSTR_DAYS_TO_NOW_getDateFormat_2MON2_DASHED(HowManydays, DateFrom);
}

//[ AND OF DASHED FORMAT ]//////////////////////////////

public static String GET_NICE_NOW(){
	return DateOperations.date_get_NOW_IN_DateFormat_SLASHED_224()+" "+DateOperations.GET_HOUR_2D()+":"+DateOperations.GET_MIN_2D()+":"+DateOperations.GET_SECS_2D();
}
public static String LOG_NOW(){
	return "["+DateOperations.GET_NICE_NOW()+"]";
} 


public static String date_SUBSTR_DAYS_FROM_NOW_getDateFormat_SLASHED_224(int howManyDaysSubstr){ /*dd/MM/yyyy*/
SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
    Date now = new Date();
    Date substracted = DateUtils.addDays(now, -howManyDaysSubstr);
    String s = sdfDate.format(  substracted );
	return s.toUpperCase();
}
public static String date_ADD_DAYS_TO_NOW_getDateFormat_SLASHED_224(int howManyDaysSubstr){ /*dd/MM/yyyy*/
SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
    Date now = new Date();
    Date substracted = DateUtils.addDays(now, howManyDaysSubstr);
    String s = sdfDate.format(  substracted );
	return s.toUpperCase();
}

	
	
	public static String GET_HOUR_2D(){
		return GET_HOUR_2D("NONE", 0);
	} 
	public static String GET_HOUR_2D(String AddRemoveFactor, int AddRemove){
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		if (AddRemoveFactor.equals("ADD")){
			calendar.add(Calendar.HOUR_OF_DAY, AddRemove);
		}else if(AddRemoveFactor.equals("REM")){
			calendar.add(Calendar.HOUR_OF_DAY, -AddRemove);
		}
		int hours = calendar.get(Calendar.HOUR_OF_DAY); // [HOUR_OF_DAY|MINUTE|SECOND]
		String rets = ToolFunctions.IntegerToString(hours); 
		if(rets.length()==1){
			return "0"+rets;
		}else{
			return rets;
		}
	} 
	public static String GET_MIN_2D(){
		return GET_MIN_2D("NONE", 0);
	} 
	public static String GET_MIN_2D(String AddRemoveFactor, int AddRemove){
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		if (AddRemoveFactor.equals("ADD")){
			calendar.add(Calendar.MINUTE, AddRemove);
		}else if(AddRemoveFactor.equals("REM")){
			calendar.add(Calendar.MINUTE, -AddRemove);
		}
		int minutes = calendar.get(Calendar.MINUTE);
		String rets = ToolFunctions.IntegerToString(minutes); 
		if(rets.length()==1){
			return "0"+rets;
		}else{
			return rets;
		}
		
	} 
	

	public static String GET_SECS_2D(){
		return GET_SECS_2D("NONE", 0);
	} 
	public static String GET_SECS_2D(String AddRemoveFactor, int AddRemove){
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		if (AddRemoveFactor.equals("ADD")){
			calendar.add(Calendar.SECOND, AddRemove);
		}else if(AddRemoveFactor.equals("REM")){
			calendar.add(Calendar.SECOND, -AddRemove);
		}
		int seconds = calendar.get(Calendar.SECOND);
		return Zeros(String.valueOf(seconds));
	} 	
	
	public static String Zeros(String x){
		return (x.length()==1)?"0"+x:x;
	}	
	
	public static String GET_UNIQUE_TIMESTAMP_NOW(){
		return GET_HOUR_2D()+GET_MIN_2D()+GET_SECS_2D();
	}
	public static String GET_UNIQUE_FULL_TIMESTAMP_NOW(){ // 08072015164259
		return date_get_DATE_TODAY_IN_DateFormat_GLUED_224()+GET_HOUR_2D()+GET_MIN_2D()+GET_SECS_2D();
	}
	public static String GET_CURR_TIMESTAMP(){ // 20150708_164702
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
	    Date now = new Date();
	    String s = sdfDate.format(  now );
		return s.toUpperCase();		
	}
	public static String GET_CURR_TIMESTAMP_COLONED(){ // 20150708_164702
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss");
	    Date now = new Date();
	    String s = sdfDate.format(  now );
		return s.toUpperCase();		
	}
	public static String GET_CURR_DATESTAMP_TODAY_ONLY(){ // 20150708_164702
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
	    Date now = new Date();
	    String s = sdfDate.format(  now );
		return s.toUpperCase();		
	}
	
	public static String TotalTimeTestCalculator(String dateStart, String dateStop){
//		String dateStart = "01/14/2012 09:29:58";
//		String dateStop = "01/15/2012 10:31:48";
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String out = "";
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			//in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			out += diffDays + " days, ";
			out += diffHours + " hours, ";
			out += diffMinutes + " minutes, ";
			out += diffSeconds + " seconds.";
			
		} catch (Exception e) {
			e.printStackTrace();
			out += ""; 
		}
	return out;
}	
	
	public static String GET_CURR_TIME(){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String GET_CURR_TIMESTAMP_FORMAT(String DatTimeFormat){ 
		String format = null;
		if(DatTimeFormat == null || DatTimeFormat.isEmpty()){
			DatTimeFormat = "MM/dd/yyyy HH:mm:ss";
		}
		SimpleDateFormat sdfDate = new SimpleDateFormat(DatTimeFormat);
	    Date now = new Date();
	    String s = sdfDate.format(  now );
		return s.toUpperCase();		
	}	
	
	public static ArrayList<String> ADD_MINUTES_TO_CURRENT_TIME(int mins){
		ArrayList<String> returnPair_HOUR_MINS=new ArrayList<String>();
		String CurrentHour = DateOperations.GET_HOUR_2D();
		String CurrentMint = DateOperations.GET_MIN_2D(); 
		if (ToolFunctions.StringToInteger(CurrentMint) > 57){
			CurrentHour = GET_HOUR_2D("ADD", 1);
			CurrentMint = GET_MIN_2D("ADD", mins);
		}else{
			CurrentHour = GET_HOUR_2D();
			CurrentMint = GET_MIN_2D("ADD", mins);
		}
		returnPair_HOUR_MINS.add(CurrentHour);
		returnPair_HOUR_MINS.add(CurrentMint);
		return returnPair_HOUR_MINS;		
	}
	
	public static String DATE_CONVERT_FORMAT_DASHED_2_SLASHED(String INPUT_DATE_DASHED){
//		String DATE_DASHED = "18-MAR-2012";
		@SuppressWarnings("deprecation")
		java.util.Date  ss1=new Date(INPUT_DATE_DASHED);
		SimpleDateFormat formatter5=new SimpleDateFormat("dd/MM/yyyy");
		String formats1 = formatter5.format(ss1);
		return formats1;		
	}
	public static String DATE_CONVERT_FORMAT_SLASHED_2_DASHED(String INPUT_DATE_SLASHED){
//		input:  DATE_DASHED = "20/03/2012";
//		output: DATE_DASHED = "20-MAR-2012";
		String[] dSlashed = INPUT_DATE_SLASHED.split("/");
		String MON = DateOperations.GET_MON_NAME_BY_STRING_NUMBER(dSlashed[1]);
		String out = dSlashed[0]+"-"+MON+"-"+dSlashed[2]; 
		return out;		
	}
	public static boolean CHECK_IF_TIME_IS_AFTER_REFER_TIME(String ReferTime, String TimeToCompareWithRefer){
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
		Date curentTIME = null;
		Date grabedTIME = null;
		try {
			curentTIME = parser.parse(ReferTime);
			grabedTIME = parser.parse(TimeToCompareWithRefer);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(ReferTime.equals(TimeToCompareWithRefer)){
			return true;
		}else{
			return grabedTIME.after(curentTIME);	
		}
	}

	public static String GET_TODATE_AS_AIRLINE_STANDARD(){
		String PATTERN="ddMMMyy";
		SimpleDateFormat dateFormat=new SimpleDateFormat();
		dateFormat.applyPattern(PATTERN);
		String date1=dateFormat.format(Calendar.getInstance().getTime());
		return date1.toUpperCase();

	}
	
	public static boolean DateCompareTwoDates_FirstDateIsEarlier(String Date1, String Date2, String Mask){
		SimpleDateFormat sdf1 = new SimpleDateFormat(Mask /* "yyyy/MM/dd HH:mm:ss" */);
	    try {
			Date matchDateTime1 = sdf1.parse(Date1);
			Date matchDateTime2 = sdf1.parse(Date2);
			return matchDateTime1.before(matchDateTime2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problem comaring two dates for [Date1="+Date1+"][Date2="+Date2+"] and mask["+Mask+"]");
			return false;
		}
		
	}

	public static String GET_MON_NAME_BY_STRING_NUMBER(String MON_NUMBER_STRING){
		String MON = null;
		switch (MON_NUMBER_STRING) {
		case ("01") : MON = "JAN"; break;
		case ("1") 	: MON = "JAN"; break;
		case ("02") : MON = "FEB"; break;
		case ("2") 	: MON = "FEB"; break;
		case ("03") : MON = "MAR"; break;
		case ("3") 	: MON = "MAR"; break;
		case ("04") : MON = "APR"; break;
		case ("4") 	: MON = "APR"; break;
		case ("05") : MON = "MAY"; break;
		case ("5") 	: MON = "MAY"; break;
		case ("06") : MON = "JUN"; break;
		case ("6") 	: MON = "JUN"; break;
		case ("07") : MON = "JUL"; break;
		case ("7") 	: MON = "JUL"; break;
		case ("08") : MON = "AUG"; break;
		case ("8") 	: MON = "AUG"; break;
		case ("09") : MON = "SEP"; break;
		case ("9") 	: MON = "SEP"; break;
		case ("10") : MON = "OCT"; break;
		case ("11") : MON = "NOV"; break;
		case ("12") : MON = "DEC"; break;}
			return MON;		}
	
	
}
