package basearch.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.CacheIndex;

@Entity
@Table(name="users")
public class User extends PersistentObject {

	@Basic(optional=false)
	@Column(name="username",length=50,nullable=false,insertable=false,updatable=false,unique=true)
	@CacheIndex(updateable=false)
	private String username;
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="language_id",nullable=false,insertable=false,updatable=false)
	private Language language;

	@Basic(optional=false)
	@Column(name="enabled",nullable=false,insertable=false,updatable=false)
	private boolean enabled;
	
	// getters & setters
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}

}