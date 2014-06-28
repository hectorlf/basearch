package basearch.dao;

import java.util.List;
import java.util.Locale;

import basearch.model.User;

public interface UserDao {

	public List<User> findAllUsers();

	public User getByUsername(String username);
	
	public void setLocaleFromLocaleResolver(String username, Locale locale);

}
