package basearch.dao;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import basearch.model.Language;
import basearch.model.auth.Authority;
import basearch.model.auth.Principal;

public interface AuthDao {

	/**
	 * Implementation of Spring Security's UserDetailsService
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	public Authority getAuthority(String username, String authority);

	public Principal createPrincipal(String username, String password, boolean enabled, Language language, Collection<String> authorities);

	public void deletePrincipal(Principal principal);

	public Authority assignAuthority(Principal principal, String authority);

	public void unassignAuthority(Principal principal, String authority);

}
