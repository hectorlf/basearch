package basearch.dao;

import java.util.Locale;

import basearch.model.User;

public interface UserDao {

	public User getByUsername(String username);
	
	public void setLocaleFromLocaleResolver(String username, Locale locale);

}
