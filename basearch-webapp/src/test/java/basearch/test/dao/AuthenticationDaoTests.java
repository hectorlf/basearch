package basearch.test.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import basearch.dao.AuthDao;
import basearch.test.BaseSpringDbTest;

public class AuthenticationDaoTests extends BaseSpringDbTest {

//	@Autowired
//	private MockMvc mockMvc;

	@Autowired
	private AuthDao authDao;
	
	@Test
	public void testUserCreation() {
	}

	@Test
	public void testUserDetailsService() {
		Assert.notNull(authDao.loadUserByUsername("test"));
		Assert.isNull(authDao.loadUserByUsername("nonexistentuser"));
	}

}
