package basearch.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;


public abstract class BaseDao {

	@PersistenceContext
	private EntityManager em;

	public void setEm(EntityManager em) {
		this.em = em;
	}

	/*
	 * persistence forwarded methods
	 */
	
	protected <T> List<T> query(Class<T> type) {
		CriteriaQuery<T> c = em.getCriteriaBuilder().createQuery(type);
		TypedQuery<T> q = em.createQuery(c);
		return q.getResultList();
	}

	protected <T> T first(Class<T> type) {
		CriteriaQuery<T> c = em.getCriteriaBuilder().createQuery(type);
		TypedQuery<T> q = em.createQuery(c);
		q.setMaxResults(1);
		return q.getSingleResult();
	}

}
