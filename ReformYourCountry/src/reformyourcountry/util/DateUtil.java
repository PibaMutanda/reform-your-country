package reformyourcountry.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	
	public static Date getDaysBeforeNow(int days) {
        GregorianCalendar cal = new GregorianCalendar();    
        cal.add(GregorianCalendar.DAY_OF_YEAR, ( - days) );
        return cal.getTime();
	}

	public static String formatyyyyMMddHHmm(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(new Date(date.getTime()));
	}

	public static String formatyyyyMMdd(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	public static Date parseyyyyMMdd(String str) {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    try {
            return formatter.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
	}


	public static String formatDuration(Date startDate, Date endDate) {
		return formatDuration(startDate, endDate, false);
	}

	public static String formatDuration(Date startDate, Date endDate, boolean shorten) {

		if (startDate.after(endDate)) { // swap
			Date tmp = startDate;
			startDate = endDate;
			endDate = tmp;
		}

		// Code copy/pasted/adapted from apache DurationFormatUtils.formatPeriod
		// To suppress too much detail for long duration, we will round the
		// diffMili.

		GregorianCalendar start = new GregorianCalendar();
		start.setTime(startDate);
		GregorianCalendar end = new GregorianCalendar();
		end.setTime(endDate);

		// initial estimates
		int seconds = end.get(Calendar.SECOND) - start.get(Calendar.SECOND);
		int minutes = end.get(Calendar.MINUTE) - start.get(Calendar.MINUTE);
		int hours = end.get(Calendar.HOUR_OF_DAY) - start.get(Calendar.HOUR_OF_DAY);
		int days = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH);
		int months = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
		int years = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);

		// each initial estimate is adjusted in case it is under 0
		while (seconds < 0) {
			seconds += 60;
			minutes -= 1;
		}
		while (minutes < 0) {
			minutes += 60;
			hours -= 1;
		}
		while (hours < 0) {
			hours += 24;
			days -= 1;
		}
		while (days < 0) {
			days += start.getActualMaximum(Calendar.DAY_OF_MONTH);
			months -= 1;
			start.add(Calendar.MONTH, 1);
		}
		while (months < 0) {
			months += 12;
			years -= 1;
		}

		String result = "";
		if (shorten) {
			
			if(months >= 10 && years == 0){
				return "a year";
			} if(months >= 5 && years == 0){
				return "a half-year";
			} else if (months >= 5 && years == 1){
				return "a year and a half";	
			}	else if (months >= 5 && years > 1){
				return "" + (years) + " years and a half";	
			} else if (years > 0){
				return "" + (years+1) + " years";	
			}

			if (years > 0) {
				result += " " + years + " year" + ((years > 1) ? "s" : "");
				return result.trim();
			}

			if(days >= 20 && months == 0){
				return "a month";
			} else if (months > 0){
				return "" + (months+1) + " months";	
			}
			
			if (months > 0) {
				result += " " + months + " month" + ((months > 1) ? "s" : "");
				return result.trim();
			}
			if (days > 0) {
				result += " " + days + " day" + ((days > 1) ? "s" : "");
				return result.trim();
			}
			if (hours > 0) {
//				result += " " + hours + " hour" + ((hours > 1) ? "s" : "");
                result += hours + "h";  // Shorter
				return result.trim();
			}
			if (minutes > 0) {
//                result += " " + minutes + " minute" + ((minutes > 1) ? "s" : "");
				result += minutes + "min"; // shorter
				return result.trim();
			}
			if (years == 0 && months == 0 && days == 0 && minutes == 0) {
				result = "less than 1 minute";
				return result.trim();
			}
		} else {
			if (years > 0) {
				result += " " + years + " year" + ((years > 1) ? "s" : "");
			}
			if (months > 0) {
				result += " " + months + " month" + ((months > 1) ? "s" : "");
			}
			if (years == 0 && days > 0) {
				result += " " + days + " day" + ((days > 1) ? "s" : "");
			}
			if (years == 0 && months == 0 && days == 0 && hours > 0) {
				result += " " + hours + " hour" + ((hours > 1) ? "s" : "");
			}
			if (years == 0 && months == 0 && days == 0 && minutes > 0) {
				result += " " + minutes + " minute" + ((minutes > 1) ? "s" : "");
			}
			if (years == 0 && months == 0 && days == 0 && minutes == 0) {
				result = "less than 1 minute";
			}
		}
		return result.trim(); // leading " "
	}

	public static String formatIntervalFromToNow(Date date) {
		return formatIntervalFromToNow(date, false);
	}
	
	public static String formatIntervalFromToNow(Date date, boolean shorten) {
		if (date == null) {
			return "-";
		}
		Date now = new Date();
		if (now.after(date)) { // date is in the past
			return formatDuration(date, now, shorten) + " ago"; // example:
			// "3 hours 23 minutes ago"
			// or "15 days ago"
		} else { // present or future
			return "in " + formatDuration(now, date, shorten); // example:
			// "in 3 hours 23 minutes"
			// or "in 15 days"
		}
	}



}
