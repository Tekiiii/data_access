package com.iktpreobuka.dataaccess.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.dataaccess.entites.AdressEntity;
import com.iktpreobuka.dataaccess.repositories.AdressEntityRepository;
import com.iktpreobuka.dataaccess.services.AdressDao;


@RestController
@RequestMapping(path = "/api/v1/adresses")
public class AdressEntityController {

	@Autowired
	private AdressEntityRepository adressRepository;
	
	
	@Autowired
	private AdressDao adressDao;
	
	
	
	@RequestMapping(method=RequestMethod.POST)
	public AdressEntity save(@RequestParam String street,  @RequestParam String city ,@RequestParam String country) {
		AdressEntity a = new AdressEntity();
		a.setCity(city);
		a.setCountry(country);
		a.setStreet(street);
		adressRepository.save(a);
		return a;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Iterable<AdressEntity> getAllAdresses() {
		return adressRepository.findAll();
	}
	
	//http://localhost:8080/api/v1/adresses/user/pera
	@RequestMapping(method=RequestMethod.GET, value = "/user/{name}")
	public List<AdressEntity> getAdressByUserName(@PathVariable String name) {
		return adressDao.findAdressersByUserName(name);
	}
}