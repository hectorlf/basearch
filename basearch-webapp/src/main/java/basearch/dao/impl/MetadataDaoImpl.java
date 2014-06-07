package basearch.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import basearch.dao.MetadataDao;
import basearch.model.Language;

@Repository
public class MetadataDaoImpl extends BaseDao implements MetadataDao {

	@Override
	public List<Language> findAllLanguages() {
		List<Language> results = allOf(Language.class);
		if (results == null || results.size() == 0) return Collections.emptyList();
		return results;
	}

	@Override
	public Language getDefaultLanguage() {
		Language l = first(Language.class);
		return l;
	}

}
