package basearch.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import basearch.service.AuthService;
import basearch.test.BaseTest;

public class AuthServiceTests extends BaseTest {

	@Autowired
	private AuthService authService;

	@Test
	public void testLoadUserByUsername1() {
		Assert.notNull(authService.loadUserByUsername("test"));
	}

	@Test(expected=UsernameNotFoundException.class)
	public void testLoadUserByUsername2() {
		authService.loadUserByUsername("doesnotexist");
	}

}