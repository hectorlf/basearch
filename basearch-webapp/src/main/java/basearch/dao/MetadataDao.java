package basearch.dao;

import java.util.List;

import basearch.model.Language;

public interface MetadataDao {

	/*
	 *  Language
	 */

	List<Language> findAllLanguages();
	
	Language getDefaultLanguage();
	
}
