package reformyourcountry.misc;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.time.DurationFormatUtils;

/**
 * @author Aymeric Levaux
 * 
 */
public class DateUtil {

	/** Number of millis in an minute */
	public static final long MINUTE_MILLIS = 60000;
	/** Number of millis in an hour */
	public static final long HOUR_MILLIS = MINUTE_MILLIS * 60;
	/** Number of millis in an day */
	public static final long DAY_MILLIS = HOUR_MILLIS * 24;

	public static final long SECOND_MILLIS = 1000;

	
	public static Date getDaysBeforeNow(int days) {
        GregorianCalendar cal = new GregorianCalendar();    
        cal.add(GregorianCalendar.DAY_OF_YEAR, ( - days) );
        return cal.getTime();
	}
	
	/**
	 * @return the time at GMT (note: Timestamp extends java.util.Date ;-)
	 */
	@Deprecated  // TODO : v5 remove.
	public static Timestamp getGMTTime() {
		return new Timestamp(getGMTTimeInMillis());
	}

	@Deprecated  // TODO : v5 remove.
	public static Timestamp getNow() {
		return new Timestamp(new Date().getTime());
	}
	
	
	@Deprecated  // TODO : v5 remove.
	public static long getGMTTimeInMillis() {
		GregorianCalendar cal = new GregorianCalendar();
		TimeZone tz = cal.getTimeZone();
		return cal.getTimeInMillis() - tz.getRawOffset() - tz.getDSTSavings();
	}

	/**
	 * Convert GMT Timestamp to a locale Timestamp.
	 * 
	 * @param gmt
	 *            a Timestamp set with a GMT time.
	 * @return a Timestamp shift with the correct offset.
	 */
	@Deprecated  // TODO : v5 remove.
	public static Timestamp getLocalTimeFromGMT(Timestamp gmt) {

		return new Timestamp(getLocalTimeFromGMTInMillis(gmt));

	}

	@Deprecated  // TODO : v5 remove.
	public static long getLocalTimeFromGMTInMillis(Timestamp gmt) {
		GregorianCalendar cal = new GregorianCalendar();
		TimeZone tz = cal.getTimeZone();
		return gmt.getTime() + tz.getRawOffset() + tz.getDSTSavings();
	}

	@Deprecated  // TODO : v5 remove.
	public static Date getLocalTimeFromGMT(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		TimeZone tz = cal.getTimeZone();
		return new Date(date.getTime() + tz.getRawOffset() + tz.getDSTSavings());
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

	public static String formatMMddHHmm(Timestamp date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		return formatter.format(new Date(date.getTime()));
	}

	@Deprecated  // Use formatyyyyMMdd instead, for consistency in the application.  TODO: delete this after v5.
	public static String formatMMddyyyy(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		if (date == null) {
			return "";
		}
		return formatter.format(new Date(date.getTime()));
	}

	public static String formatHHmm(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm z");
		return formatter.format(date);
	}

	public static String formatDurationInHoursMinutesSeconds(long timeInSeconds) {
		Long hours = timeInSeconds / 3600;
		timeInSeconds %= 3600;
		Long minutes = timeInSeconds / 60;
		timeInSeconds %= 60;

		return String.format("%1d:%02d:%02d", hours, minutes, timeInSeconds);
	}

	public static String formatDurationInTextDaysHoursMinutesSeconds(long timeInSeconds) {
		long days = timeInSeconds / (60 * 60 * 24);
		timeInSeconds %= (60 * 60 * 24);
		long hours = timeInSeconds / (60 * 60);
		timeInSeconds %= (60 * 60);
		long minutes = timeInSeconds / 60;
		timeInSeconds %= 60;

		String result = formatDurationInTextDaysHoursMinutesSecondsHelper(days, "day");
		result += formatDurationInTextDaysHoursMinutesSecondsHelper(hours, "hour");
		result += formatDurationInTextDaysHoursMinutesSecondsHelper(minutes, "minute");

		return result;
	}

	private static String formatDurationInTextDaysHoursMinutesSecondsHelper(long number, String word) {
		String result;
		if (number > 0) {
			result = number + " " + word; // example: "2 day" or "18 hour"
			if (number > 1) {
				result += "s ";
			} else {
				result += " ";
			}
		} else {
			result = "";
		}
		return result;
	}

	/**
	 * Give time between two dates.<br /> It will return a positive long if
	 * time2 later than time1.
	 * 
	 * @param time1
	 *            first date.
	 * @param time2
	 *            second date.
	 * @return time between two dates in milliseconds.
	 */
	public static long millisBetweenDates(Date time1, Date time2) {

		return time2.getTime() - time1.getTime();

	}

	public static long getDaysBetweenDates(Date time1, Date time2) {
		return millisBetweenDates(time1, time2) / DAY_MILLIS;
	}

	public static String TimeBetweenDates(Timestamp time1, Timestamp time2) {

		if (time1.getTime() > time2.getTime()) {
			throw new IllegalArgumentException("time1 must be a date before time2.");
		}

		int distance = 0;
		String result = null;

		long time = millisBetweenDates(time1, time2);

		if (time < HOUR_MILLIS) {
			result = "less than 1 hour";
		} else if (time < DAY_MILLIS) {
			distance = (int) Math.floor(time / HOUR_MILLIS);
			result = "" + distance + " hour" + ((distance > 1) ? "s" : "");
		} else {
			distance = (int) Math.floor(time / DAY_MILLIS);
			result = "" + distance + " day" + ((distance > 1) ? "s" : "");
		}

		return result;

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

	public static String TimeBetweenDatesWithSeconds(Timestamp time1, Timestamp time2) {

		if (time1.getTime() > time2.getTime()) {
			throw new IllegalArgumentException("time1 must be a date before time2.");
		}

		int days = 0;
		int hours = 0;
		int min = 0;
		// int sec=0;
		String result = "";

		long time = millisBetweenDates(time1, time2);

		if (time > DAY_MILLIS) {
			days = (int) Math.floor(time / DAY_MILLIS);
			result = "" + days + "d ";
		}
		if (time > HOUR_MILLIS) {
			hours = (int) Math.floor(time / HOUR_MILLIS);
			result = result + (hours - (days * 24)) + "h ";
		}

		min = (int) Math.floor(time / MINUTE_MILLIS);
		result = result + (min - (hours * 60)) + "m";

		return result;

	}

	public static String TimeBetweenDatesWithSec(Timestamp time1, Timestamp time2) {
		if (time1.getTime() > time2.getTime()) {
			throw new IllegalArgumentException("time1 must be a date before time2.");
		}

		int days = 0;
		int hours = 0;
		int min = 0;
		int sec = 0;
		String result = "";

		long time = millisBetweenDates(time1, time2);

		if (time > DAY_MILLIS) {
			days = (int) Math.floor(time / DAY_MILLIS);
			result = "" + days + "d ";
		}
		if (time > HOUR_MILLIS) {
			hours = (int) Math.floor(time / HOUR_MILLIS);
			result = result + (hours - (days * 24)) + "h ";
		}
		if (time > MINUTE_MILLIS) {
			min = (int) Math.floor(time / MINUTE_MILLIS);
			result = result + (min - (hours * 60)) + "m ";
		}
		sec = (int) Math.floor(time / SECOND_MILLIS);
		result = result + (sec - (min * 60)) + "s";
		// }

		return result;

	}

	// TODO AA mwanji: is getNLettersOfMonth() used?
	public static String getNLettersOfMonth(int n, int month) {
		String[] names = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November",
				"December" };
		String name = names[month];
		if (n < name.length()) {
			name = name.substring(0, n);
		}
		return name;
	}
	
	
	/**
	 * Jan = 1, Dec = 12
	 */
	private static final String[] MONTH_NAMES = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Augt", "Sep", "Oct", "Nov", "Dec"};
	public static String getAbbreviatedMonthName(int month) {
		String name = MONTH_NAMES[month - 1];
		return name;
	}

	public static String formatTimeInMinAndSec(long secondsRemaining) {

		int min = (int) Math.floor(secondsRemaining / 60);
		int sec = (int) (secondsRemaining % 60);

		StringBuffer result = new StringBuffer(" ");
		if (min > 0) {
			result.append(min);
			result.append(" minute");
			if (min > 1) {
				result.append("s");
			}
		}
		if (min > 0) {
			result.append(" and ");
		}

		result.append(sec);
		result.append(" second");
		if (sec != 1) {
			result.append("s");
		}
		return result.toString();
	}

	public static String getMonthName(int monthNumber) {
		String month = "invalid";
		DateFormatSymbols dfs = new DateFormatSymbols(Locale.ENGLISH);
		String[] months = dfs.getMonths();
		if (monthNumber >= 0 && monthNumber <= 11) {
			month = months[monthNumber];
		}
		return month;
	}

}
