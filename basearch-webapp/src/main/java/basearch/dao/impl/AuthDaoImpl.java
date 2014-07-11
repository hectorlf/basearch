package basearch.dao.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import basearch.dao.AuthDao;
import basearch.model.auth.Principal;
import basearch.model.auth.Principal_;

@Repository
public class AuthDaoImpl extends BaseDao implements AuthDao {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return entity(Principal.class).with(Principal_.username, username).find();
	}

}
