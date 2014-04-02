package basearch.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import basearch.dao.UserDao;
import basearch.dao.impl.UserDaoImpl;
import basearch.service.UserService;
import basearch.service.impl.UserServiceImpl;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@Import(value={ThymeleafConfiguration.class})
public class AppConfiguration {

	private static String[] messageSourceBasenames = { "applicationResources" };

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
		ms.setBasenames(messageSourceBasenames);
		return ms;
	}
	
	@Bean
	public SessionBasedLocaleResolver sessionBasedLocaleResolver() {
		return new SessionBasedLocaleResolver(userDao());
	}
	
	@Bean
	public CookieBasedLocaleResolver cookieBasedLocaleResolver() {
		return new CookieBasedLocaleResolver();
	}

	@Bean
	public LocaleResolver localeResolver() {
		LocaleResolver lr = new ChainedLocaleResolver(sessionBasedLocaleResolver(), cookieBasedLocaleResolver(), new AcceptHeaderLocaleResolver());
		return lr;
	}

	
	// dao layer
	
	@Bean
	public UserDao userDao() {
		return new UserDaoImpl();
	}

	// service layer
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl(userDao());
	}

}
