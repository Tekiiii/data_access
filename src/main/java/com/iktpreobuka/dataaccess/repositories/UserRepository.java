package com.iktpreobuka.dataaccess.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.dataaccess.entites.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	
		UserEntity findByIdAndName(Integer id, String name);
		UserEntity findByEmail(String email);
		Iterable<UserEntity> findByNameOrderByEmailAsc(String name);
}
