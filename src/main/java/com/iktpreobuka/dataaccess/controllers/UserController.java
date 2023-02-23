package com.iktpreobuka.dataaccess.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.dataaccess.entites.AdressEntity;
import com.iktpreobuka.dataaccess.entites.UserEntity;
import com.iktpreobuka.dataaccess.models.EmailObject;
import com.iktpreobuka.dataaccess.repositories.AdressEntityRepository;
import com.iktpreobuka.dataaccess.repositories.UserRepository;
import com.iktpreobuka.dataaccess.services.UsersDAO;



@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public UsersDAO usersDao;
	
	@Autowired
	private AdressEntityRepository adressRepository;
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/adress")
	public UserEntity addAdressToAUser(@PathVariable Integer id,
	@RequestParam Integer adressId) {
		
	UserEntity user = userRepository.findById(id).get();
	AdressEntity adress = adressRepository.findById(adressId).get();
	
	user.setAdress(adress);
	userRepository.save(user); // automatski ce biti sacuvana i adresa
	
	return userRepository.save(user);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public UserEntity addNewUser(@RequestParam String name, @RequestParam String email) {
	UserEntity user = new UserEntity();
	user.setName(name);
	user.setEmail(email);
	userRepository.save(user);
	return user;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<UserEntity> getAllUsers() {
	return userRepository.findAll();
	}
	
	@RequestMapping("/{id}")
	public UserEntity getById(@PathVariable int id) {
		return userRepository.findById(id).get();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/{id}")
	public UserEntity update(@PathVariable int id, @RequestBody UserEntity entity) {
		UserEntity user = userRepository.findById(id).get();
		user.setEmail(entity.getEmail());
		user.setName(entity.getEmail());
		return userRepository.save(entity);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/{id}")
	public UserEntity delete(@PathVariable int id) {
		userRepository.deleteById(id);
		return null;
	}
	@RequestMapping(method = RequestMethod.GET, value="/by-email")
	public UserEntity findByEmail(@RequestParam String email) {
		return userRepository.findByEmail(email);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/by-name")
	public Iterable<UserEntity>getByNameSortedByEmail(@RequestParam String name){
		return userRepository.findByNameOrderByEmailAsc(name);
		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/download")
	public ResponseEntity<ByteArrayResource>  downloadFile() throws IOException {
		String path = ""; 
		try {
			path = usersDao.writeAllUsersToFile();
			
			System.out.println(path);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		String filename = "fajl.txt"; 
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);

	    Path path1 = Paths.get(path);
	    
	    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path1));

	    return ResponseEntity.ok()
	    		.headers(header)
	            .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
	            .body(resource);

	}
	
	@RequestMapping(method=RequestMethod.POST, value="/sendUsers")	
	public String sendUsers(@RequestParam("to") String to, @RequestParam("text") String text, @RequestParam("subject") String subject, @RequestParam("file") MultipartFile file) throws Exception {
		if(text == null || to == null) {
			return "Mail is not valid";
		}
		EmailObject object = new EmailObject();
		object.setSubject(subject);
		object.setText(text);
		object.setTo(to);
		String ret = usersDao.sendFile(object, file);
		return ret;
	}
	
	
}
