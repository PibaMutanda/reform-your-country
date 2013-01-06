package reformyourcountry.web;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/** Will prevent requests for static files to go through all the filters (to help performance).
 * 
 * @see  http://stackoverflow.com/questions/3738162/how-to-skip-a-filter-in-the-filter-chain-in-java
 * 
 * @author john
 *
 */
public class FiltersSkipFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
			String url = httpServletRequest.getRequestURL().toString();
			
			if (StringUtils.endsWithAny(url, ".js", ".css", ".jpg", ".jpeg", ".png", ".gif", ".pdf", ".swf", ".ico")) {
				// bypass all filters.
				request.getRequestDispatcher(httpServletRequest.getServletPath() + StringUtils.defaultString(httpServletRequest.getPathInfo()) ).forward(request, response);
				return;
			}
		}
			
		// Normal processing.
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
