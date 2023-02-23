package com.iktpreobuka.dataaccess.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.iktpreobuka.dataaccess.entites.AdressEntity;

@Service
public class AdressDAOImpl implements AdressDao {

	@PersistenceContext
	private EntityManager em;

	
	@Override
	public List<AdressEntity> findAdressersByUserName(String name) {
		// TODO select * from users where id='1' and name = 'pera'
		
		String sql = "select a " + 
					"from AdressEntity a " + 
					"left join fetch a.users u " + 
					"where u.name = :name";
		
		Query query = em.createQuery(sql);
		query.setParameter("name",name);
		
		List<AdressEntity> result = new ArrayList<>();
		result = query.getResultList();
		return result;
	}


	
	
}
