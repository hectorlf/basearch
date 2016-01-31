package basearch.test.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import basearch.test.BaseSecurityTest;

public class SecurityTests extends BaseSecurityTest {

	private static final Logger logger = LoggerFactory.getLogger(SecurityTests.class);

	@Test
	@WithMockUser(username="admin",roles="ADMIN")
	public void testSessionLocale() throws Exception {
		mockMvc.perform(get("/index.page").secure(true))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Bienvenido")));
	}

	@Test
	public void requiresSecure() throws Exception {
		MvcResult result = mockMvc.perform(get("/secured.page"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("https://**"))
			.andReturn();
		logger.debug("Result requiresSecure(): " + result.getResponse().getStatus() + " - " + result.getResponse().getRedirectedUrl());
		result = mockMvc.perform(get("/login.page"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("https://**"))
			.andReturn();
		logger.debug("Result requiresSecure(): " + result.getResponse().getStatus() + " - " + result.getResponse().getRedirectedUrl());
	}

	@Test
	public void requiresAuthentication() throws Exception {
		MvcResult result = mockMvc.perform(get("/secured.page").secure(true))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("**/login.page*"))
			.andReturn();
		logger.debug("Result requiresAuthentication(): " + result.getResponse().getStatus() + " - " + result.getResponse().getRedirectedUrl());
	}

	@Test
	@WithMockUser(roles="ADMIN")
	public void accessGranted() throws Exception {
		this.mockMvc.perform(get("/secured.page").secure(true))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("ROLE_ADMIN")));
	}

	@Test
	@WithMockUser(roles="DENIED")
	public void accessDenied() throws Exception {
		this.mockMvc.perform(get("/secured.page").secure(true))
			.andExpect(status().isForbidden());
	}

	@Test
	public void loginIsAvailable() throws Exception {
		this.mockMvc.perform(get("/login.page").secure(true))
			.andExpect(status().isOk());
	}

	@Test
	public void loginPostIsAvailable() throws Exception {
		this.mockMvc.perform(post("/login").secure(true))
			.andExpect(status().is3xxRedirection());
	}

	@Test
	public void userAuthenticates() throws Exception {
		final String username = "admin";
		final String password = "admin";
		MvcResult result = mockMvc.perform(post("/login").param("username", username).param("password", password).secure(true))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/secured.page"))
			.andExpect(new ResultMatcher() {
				public void match(MvcResult mvcResult) throws Exception {
					HttpSession session = mvcResult.getRequest().getSession();
					SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
					Assert.assertEquals(securityContext.getAuthentication().getName(), username);
				}
			})
			.andReturn();
		logger.debug("Result userAuthenticates(): " + result.getResponse().getStatus() + " - " + result.getResponse().getRedirectedUrl());
	}

	@Test
	public void userAuthenticateFails() throws Exception {
		MvcResult result = mockMvc.perform(post("/login").param("username", "notexistent").param("password", "invalid").secure(true))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/login.page?error"))
			.andExpect(new ResultMatcher() {
				public void match(MvcResult mvcResult) throws Exception {
					HttpSession session = mvcResult.getRequest().getSession();
					SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
					Assert.assertNull(securityContext);
				}
			})
			.andReturn();
		logger.debug("Result userAuthenticateFails(): " + result.getResponse().getStatus() + " - " + result.getResponse().getRedirectedUrl());
	}

}