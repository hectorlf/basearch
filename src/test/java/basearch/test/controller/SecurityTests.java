package basearch.test.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import basearch.test.BaseSecurityTest;

@Ignore
public class SecurityTests extends BaseSecurityTest {

	@Test
	@WithMockUser(roles="ADMIN")
	public void testSessionLocale() throws Exception {
		mockMvc.perform(get("/index.page"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")));
	}

	@Test
	public void requiresAuthentication() throws Exception {
		mockMvc.perform(get("/secured.page"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost/login.page"));
	}

	@Test
	@WithMockUser(roles="ADMIN")
	public void accessGranted() throws Exception {
		this.mockMvc.perform(get("/secured.page"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("ROLE_ADMIN")));
	}

	@Test
	@WithMockUser(roles="DENIED")
	public void accessDenied() throws Exception {
		this.mockMvc.perform(get("/secured.page"))
			.andExpect(status().isForbidden());
	}

	@Test
	public void loginIsAvailable() throws Exception {
		this.mockMvc.perform(get("/login.page"))
			.andExpect(status().isOk());
	}

	@Test
	public void loginPostIsAvailable() throws Exception {
		this.mockMvc.perform(post("/login.action"))
			.andExpect(status().is3xxRedirection());
	}

	@Test
	public void userAuthenticates() throws Exception {
		final String username = "test";
		final String password = "test";
		mockMvc.perform(post("/login.action").param("username", username).param("password", password))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/secured.page"))
			.andExpect(new ResultMatcher() {
				public void match(MvcResult mvcResult) throws Exception {
					HttpSession session = mvcResult.getRequest().getSession();
					SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
					Assert.assertEquals(securityContext.getAuthentication().getName(), username);
				}
			});
	}

	@Test
	public void userAuthenticateFails() throws Exception {
		mockMvc.perform(post("/login.action").param("username", "notexistent").param("password", "invalid"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/login.page?error=1"))
			.andExpect(new ResultMatcher() {
				public void match(MvcResult mvcResult) throws Exception {
					HttpSession session = mvcResult.getRequest().getSession();
					SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
					Assert.assertNull(securityContext);
				}
			});
	}

}