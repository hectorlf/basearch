package basearch.model.auth;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import basearch.model.PersistentObject;

@Entity
@Table(name="authorities")
public class Authority extends PersistentObject implements GrantedAuthority {

	private static final long serialVersionUID = -5992698407860058855L;

	@Basic(optional=false)
	@Column(name="username",nullable=false,length=50)
	private String username;

	@Basic(optional=false)
	@Column(name="authority",nullable=false,length=50)
	private String authority;

	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="username",nullable=false,insertable=false,updatable=false)
	private Principal principal;

	// getters & setters
	
	@Override
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public Principal getPrincipal() {
		return principal;
	}
	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}

}
