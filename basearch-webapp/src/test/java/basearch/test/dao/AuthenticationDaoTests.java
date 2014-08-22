package basearch.test.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import basearch.dao.AuthDao;
import basearch.dao.MetadataDao;
import basearch.model.Language;
import basearch.model.auth.Authority;
import basearch.model.auth.Principal;
import basearch.test.BaseSpringDbTest;

public class AuthenticationDaoTests extends BaseSpringDbTest {

//	@Autowired
//	private MockMvc mockMvc;

	@Autowired
	private MetadataDao metadataDao;

	@Autowired
	private AuthDao authDao;

	@Test
	public void testPrincipalCreation() {
		Language l = metadataDao.getDefaultLanguage();
		Assert.notNull(l);
		List<String> authorities = new ArrayList<String>(1);
		authorities.add("basicuser");
		Principal p = authDao.createPrincipal("newlycreateduser", "none", true, l, authorities);
		Assert.notNull(p);
		Assert.notNull(authDao.loadUserByUsername("newlycreateduser"));
		Assert.notEmpty(authDao.loadUserByUsername("newlycreateduser").getAuthorities());
	}

	@Test
	public void testPrincipalDeletion() {
		Language l = metadataDao.getDefaultLanguage();
		Assert.notNull(l);
		List<String> authorities = new ArrayList<String>(1);
		authorities.add("basicuser");
		Principal p = authDao.createPrincipal("newlycreateduser", "none", true, l, authorities);
		Assert.notNull(p);
		authDao.deletePrincipal(p);
		Assert.isNull(authDao.loadUserByUsername("newlycreateduser"));
	}

	@Test
	public void testAuthorityCreation() {
		Principal p = (Principal)authDao.loadUserByUsername("test");
		Assert.notNull(p);
		Authority a = authDao.assignAuthority(p, "specialuser");
		Assert.notNull(a);
		Principal ptest = (Principal)authDao.loadUserByUsername(p.getUsername());
		Assert.notEmpty(ptest.getAuthorities());
		boolean found = false;
		for (Authority atest : ptest.getAuthorities()) {
			if (atest.getAuthority().equals("specialuser")) {
				found = true;
				break;
			}
		}
		Assert.isTrue(found);
	}

	@Test
	public void testAuthorityDeletion1() {
		Principal p = (Principal)authDao.loadUserByUsername("test");
		Assert.notNull(p);
		Authority a = authDao.assignAuthority(p, "willbedeleted");
		Assert.notNull(a);
		Principal ptest = (Principal)authDao.loadUserByUsername(p.getUsername());
		Assert.notEmpty(ptest.getAuthorities());
		Iterator<Authority> it = ptest.getAuthorities().iterator();
		while (it.hasNext()) {
			Authority temp = it.next();
			if (temp.getAuthority().equals("willbedeleted")) {
				it.remove();
				break;
			}
		}
		ptest = (Principal)authDao.loadUserByUsername(p.getUsername());
		boolean found = false;
		for (Authority atest : ptest.getAuthorities()) {
			if (atest.getAuthority().equals("willbedeleted")) {
				found = true;
				break;
			}
		}
		Assert.isTrue(!found);
	}
	
	@Test
	public void testAuthorityDeletion2() {
		Principal p = (Principal)authDao.loadUserByUsername("test");
		Assert.notNull(p);
		Authority a = authDao.assignAuthority(p, "willbedeleted");
		Assert.notNull(a);
		Principal ptest = (Principal)authDao.loadUserByUsername(p.getUsername());
		Assert.notEmpty(ptest.getAuthorities());
		authDao.unassignAuthority(p, "willbedeleted");
		ptest = (Principal)authDao.loadUserByUsername(p.getUsername());
		boolean found = false;
		for (Authority atest : ptest.getAuthorities()) {
			if (atest.getAuthority().equals("willbedeleted")) {
				found = true;
				break;
			}
		}
		Assert.isTrue(!found);
	}

	@Test
	public void testUserDetailsService() {
		Assert.notNull(authDao.loadUserByUsername("test"));
		Assert.isNull(authDao.loadUserByUsername("nonexistentuser"));
	}

}
