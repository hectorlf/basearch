package basearch.dao.impl;

import java.util.Locale;

import org.springframework.stereotype.Repository;

import basearch.dao.UserDao;
import basearch.model.User;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao {

	@Override
	public User getByUsername(String username) {
		if (username == null || username.isEmpty()) throw new IllegalArgumentException("username can't be null");
		return entity(User.class).by("username", username);
	}

	@Override
	public void setLocaleFromLocaleResolver(String username, Locale locale) {
		// TODO Auto-generated method stub
		
	}

}
