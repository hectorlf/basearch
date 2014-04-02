package basearch.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

@MappedSuperclass
@TableGenerator(name="jpa_seq",table="sequence_table",pkColumnName="seq",pkColumnValue="global_seq",valueColumnName="val",initialValue=100,allocationSize=5)
public abstract class PersistentObject {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="jpa_seq")
	private Integer id;

	// getters & setters

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}