package basearch.integration;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

public class ChainedLocaleResolver implements LocaleContextResolver {

	private SessionBasedLocaleResolver sessionBasedLocaleResolver;
	private CookieBasedLocaleResolver cookieBasedLocaleResolver;
	private AcceptHeaderLocaleResolver acceptHeaderLocaleResolver;

	@Inject
	public ChainedLocaleResolver(SessionBasedLocaleResolver sessionBasedLocaleResolver, CookieBasedLocaleResolver cookieBasedLocaleResolver, AcceptHeaderLocaleResolver acceptHeaderLocaleResolver) {
		this.sessionBasedLocaleResolver = sessionBasedLocaleResolver;
		this.cookieBasedLocaleResolver = cookieBasedLocaleResolver;
		this.acceptHeaderLocaleResolver = acceptHeaderLocaleResolver;
	}

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Locale locale = sessionBasedLocaleResolver.resolveLocale(request);
		if (locale == null) locale = cookieBasedLocaleResolver.resolveLocale(request);
		if (locale == null) locale = acceptHeaderLocaleResolver.resolveLocale(request);
		return locale;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response,	Locale l) {
		sessionBasedLocaleResolver.setLocale(request, response, l);
		cookieBasedLocaleResolver.setLocale(request, response, l);
	}

	@Override
	public LocaleContext resolveLocaleContext(HttpServletRequest request) {
		LocaleContext localeContext = sessionBasedLocaleResolver.resolveLocaleContext(request);
		if (localeContext == null) localeContext = cookieBasedLocaleResolver.resolveLocaleContext(request);
		if (localeContext == null) {
			Locale l = acceptHeaderLocaleResolver.resolveLocale(request);
			localeContext = new SimpleLocaleContext(l);
		}
		return localeContext;
	}

	@Override
	public void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext lc) {
		sessionBasedLocaleResolver.setLocaleContext(request, response, lc);
		cookieBasedLocaleResolver.setLocaleContext(request, response, lc);
	}

}
