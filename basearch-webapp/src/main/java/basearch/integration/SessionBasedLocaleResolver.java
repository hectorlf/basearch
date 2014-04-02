package basearch.integration;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleContextResolver;

import basearch.dao.UserDao;
import basearch.model.User;

public class SessionBasedLocaleResolver implements LocaleContextResolver {

	private UserDao userDao;

	@Inject
	public SessionBasedLocaleResolver(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.isAuthenticated() || (auth instanceof AnonymousAuthenticationToken)) return null;
		User u = userDao.getByUsername(auth.getName());
		return u.getLanguage().toLocale();
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response,	Locale l) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.isAuthenticated() || (auth instanceof AnonymousAuthenticationToken)) return;
		userDao.setLocaleFromLocaleResolver(auth.getName(), l);
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
