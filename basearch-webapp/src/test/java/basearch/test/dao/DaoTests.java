package basearch.test.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import basearch.dao.MetadataDao;
import basearch.test.BaseSpringDbTest;

public class DaoTests extends BaseSpringDbTest {

	@Autowired
	private MetadataDao metadataDao;

	@Test
	public void testFindLanguages() {
		Assert.notEmpty(metadataDao.findAllLanguages());
	}

}
