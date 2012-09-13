package reformyourcountry.converter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

import reformyourcountry.util.DateUtil;

public class DateFormatter implements Formatter<Date>{

	@Override
	public String print(Date date, Locale locale) {
		return DateUtil.formatyyyyMMdd(date);
	}

	@Override
	public Date parse(String text, Locale locale) throws ParseException {
			return DateUtil.parseyyyyMMddWithParseException(text);
	}

}
