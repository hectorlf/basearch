package basearch.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthDao {

	/**
	 * Implementation of Spring Security's UserDetailsService
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
