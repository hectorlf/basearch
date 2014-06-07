package basearch.test.dao;

import javax.persistence.NonUniqueResultException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import basearch.dao.MetadataDao;
import basearch.test.BaseSpringDbTest;

public class MetadataDaoTests extends BaseSpringDbTest {

	@Autowired
	private MetadataDao metadataDao;

	@Test
	public void testFindAllLanguages() {
		Assert.notEmpty(metadataDao.findAllLanguages());
	}

	@Test
	public void testFindDefaultLanguage() {
		Assert.notNull(metadataDao.getDefaultLanguage());
	}

	@Test
	public void testGetLanguageBy() {
		Assert.notNull(metadataDao.getLanguageBy("es", "ES", null));
		Assert.isNull(metadataDao.getLanguageBy("bla", null, null));
		Assert.isNull(metadataDao.getLanguageBy("bla", "bla", "bla"));
	}
	
	@Test(expected=NonUniqueResultException.class)
	public void testGetLanguageByException() {
		metadataDao.getLanguageBy("en", null, null);
	}

}
