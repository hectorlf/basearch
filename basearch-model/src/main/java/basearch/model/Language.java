package basearch.model;

import java.util.Locale;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheIndex;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name="languages")
@Access(AccessType.FIELD)
@ReadOnly
@Cache(size=5)
@CacheIndex(columnNames={"language","region","variant"}, updateable=false)
public class Language extends PersistentObject {

	public static final Long DEFAULT_LANGUAGE_ID = Long.valueOf(1);

	@Basic(optional=false)
	@Column(name="language",length=3)
	private String langCode;
	
	@Basic(optional=true)
	@Column(name="region",length=3)
	private String regionCode;

	@Basic(optional=true)
	@Column(name="variant",length=8)
	private String variantCode;

	// getters sinteticos
	
	public Locale toLocale() {
		if (langCode == null || langCode.isEmpty()) return null;
		if (regionCode == null || regionCode.isEmpty()) return new Locale(langCode);
		if (variantCode == null || variantCode.isEmpty()) return new Locale(langCode, regionCode);
		return new Locale(langCode, regionCode, variantCode);
	}
	
	// getters & setters
	
	public String getLangCode() {
		return langCode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public String getVariantCode() {
		return variantCode;
	}

}
