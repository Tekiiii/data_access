package com.iktpreobuka.dataaccess.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.dataaccess.entites.AdressEntity;
import com.iktpreobuka.dataaccess.entites.UserEntity;

 public interface AdressEntityRepository  extends CrudRepository<AdressEntity, Integer> {

}
