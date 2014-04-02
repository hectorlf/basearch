package basearch.integration;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

public class CookieBasedLocaleResolver extends CookieGenerator implements LocaleContextResolver {

	private static final Logger logger = LoggerFactory.getLogger(CookieBasedLocaleResolver.class);
	
	private static final String LOCALE_REQUEST_ATTRIBUTE_NAME = "CookieBasedLocaleResolver.LOCALE";

	public CookieBasedLocaleResolver() {
		setCookieName(Constants.LOCALE_RESOLVER_COOKIE_NAME);
	}

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		// Check request for pre-parsed or preset locale
		Locale locale = (Locale)request.getAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME);
		if (locale != null) return locale;
		// Retrieve and parse cookie value
		Cookie cookie = WebUtils.getCookie(request, getCookieName());
		if (cookie == null || cookie.getValue() == null || cookie.getValue().length() == 0) return null;
		locale = Locale.forLanguageTag(cookie.getValue());
		logger.debug("Parsed cookie value [{}] into locale '{}'", cookie, locale);
		if (locale != null) request.setAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME, locale);
		return locale;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response,	Locale l) {
		if (l != null) {
			// Set request attribute and add cookie
			request.setAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME, l);
			addCookie(response, l.toLanguageTag());
		} else {
			// Remove cookie and request attribute
			request.removeAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME);
			removeCookie(response);
		}
	}

	@Override
	public LocaleContext resolveLocaleContext(HttpServletRequest request) {
		// currently, timezone is not persisted
		Locale locale = resolveLocale(request);
		return new SimpleLocaleContext(locale);
	}

	@Override
	public void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext lc) {
		// currently, timezone is not persisted
		setLocale(request, response, lc.getLocale());
	}

}
