package reformyourcountry.tag;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.Article;
import reformyourcountry.repository.ArticleRepository;
import reformyourcountry.util.DateUtil;
import reformyourcountry.web.ContextUtil;

public class DateDisplayTag extends SimpleTagSupport{
	
	private Date date;
	private Boolean duration;  // optionnal, means to display "xx days ago" instead of the date, then the date in tooltip.
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
	
	public Boolean getDuration() {
		return duration;
	}

	public void setDuration(Boolean duration) {
		this.duration = duration;
	}

	@Override
	public void doTag() throws JspException {
		if (date == null) {
			return;
		}
		
		String result;
		
		if (duration != null && duration == true) {
			result = "<span title='"+DateUtil.formatyyyyMMdd(date)+"'>"+DateUtil.formatIntervalFromToNowFR(date)+"</span>";
		} else {
			result = DateUtil.formatyyyyMMdd(date);
		}
		
		JspWriter out = this.getJspContext().getOut();
		try {
			out.println(result);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
