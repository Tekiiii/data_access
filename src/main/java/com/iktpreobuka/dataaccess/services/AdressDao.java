package com.iktpreobuka.dataaccess.services;

import java.util.List;

import com.iktpreobuka.dataaccess.entites.AdressEntity;

public interface AdressDao{

	public List<AdressEntity> findAdressersByUserName(String name);
	
}
