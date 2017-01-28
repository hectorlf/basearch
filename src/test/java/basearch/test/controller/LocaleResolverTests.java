package basearch.test.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import javax.servlet.http.Cookie;

import org.junit.Test;

import basearch.Constants;
import basearch.test.BaseMvcTest;

public class LocaleResolverTests extends BaseMvcTest {

	@Test
	public void testSessionLocale() throws Exception {
		// tested in the security tests
	}

	@Test
	public void testNoLocale() throws Exception {
		mockMvc.perform(get("/index.page"))
			.andExpect(status().isOk());
	}

	@Test
	public void testAcceptHeaderLocale1() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("es-ES")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Bienvenido")));
	}

	@Test
	public void testAcceptHeaderLocale2() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("en-GB")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")));
	}

	@Test
	public void testAcceptHeaderLocale3() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("en-US")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")));
	}

	@Test
	public void testAcceptHeaderLocale4() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("pt-BR")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Bienvenido")));
	}

	@Test
	public void testAcceptHeaderLocale5() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("en-GB")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")));
	}

	@Test
	public void testAcceptHeaderLocale6() throws Exception {
		mockMvc.perform(get("/index.page").header("Accept-Language","en-US, en-GB, en"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")));
	}

	@Test
	public void testCookieLocale1() throws Exception {
		Cookie c = new Cookie(Constants.LOCALE_RESOLVER_COOKIE_NAME, "es-ES");
		mockMvc.perform(get("/index.page").cookie(c))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Bienvenido")));
	}

	@Test
	public void testCookieLocale2() throws Exception {
		Cookie c = new Cookie(Constants.LOCALE_RESOLVER_COOKIE_NAME, "en-GB");
		mockMvc.perform(get("/index.page").cookie(c))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")));
	}

	@Test
	public void testCookieLocale3() throws Exception {
		Cookie c = new Cookie(Constants.LOCALE_RESOLVER_COOKIE_NAME, "pt-BR");
		mockMvc.perform(get("/index.page").cookie(c))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")));
	}

	@Test
	public void testCookieLocale4() throws Exception {
		Cookie c = new Cookie(Constants.LOCALE_RESOLVER_COOKIE_NAME, "arriquitaun");
		mockMvc.perform(get("/index.page").cookie(c))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")));
	}

}