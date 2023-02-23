package com.iktpreobuka.dataaccess.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.dataaccess.entites.UserEntity;
import com.iktpreobuka.dataaccess.models.EmailObject;
import com.iktpreobuka.dataaccess.repositories.UserRepository;
@Service
public class UsersDAOImpl implements UsersDAO {

	public static String UPLOADED_FOLDER = "C:\\temp\\";
	public static String UPLOADED_FOLDER1 = "C:\\temp1\\";

	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public EmailService emailService;
	
	@Override
	public String saveAllUsersFromFile(Path path) throws IOException {
		List<UserEntity> users = readFromCSV(path);
		int successCount = 0;
		for(UserEntity u : users) {
			UserEntity u1 = userRepository.findByEmail(u.getEmail());
			if(u1==null) {
				userRepository.save(u);	
				successCount++;
			}
		}
		
		
		if(successCount>0) {
			return "Successfully uploaded users!";
		}else {
			return "No uploaded users!";

		}
	}
	private String writeToCSV(List<UserEntity> users, String fileName) throws IOException{
		String userStrings = "";
		
		for(UserEntity u : users) {
			userStrings = userStrings + u.getName()+","+u.getEmail()+"\n";
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(UPLOADED_FOLDER+fileName));
			bw.write(userStrings);
			
			bw.close();
		}catch(IOException e) {
			throw e;
		}
		
		return "Success!";
	}  
	private List<UserEntity> readFromCSV(Path path) throws IOException{
		List<UserEntity> users = new ArrayList<>();
		
		try {
			BufferedReader br = Files.newBufferedReader(path);
			String line = br.readLine();
			while(line != null) {
				
				String[] lineElements = line.split(",");
				
				UserEntity u = new UserEntity();
				
				u.setName(lineElements[0]);
				u.setEmail(lineElements[1]);
				users.add(u);
				line = br.readLine();
			}
		}catch(IOException e) {
			throw e;
		}
		return users;
	}
	
	@Override
	public String writeAllUsersToFile() throws IOException {
		String path = "allUsers.txt";
		
		List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
		
		String a = writeToCSV(users, "allUsers.txt");
		
		return UPLOADED_FOLDER+path;
	}
	
	@Override
	public String sendFile(EmailObject object, MultipartFile file) throws Exception {

		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER1+file.getOriginalFilename());
			Files.write(path, bytes);
			emailService.sendMessageWithAttachment(object, UPLOADED_FOLDER1+file.getOriginalFilename());

		}catch(Exception e) {
			throw e;
		}
		
		
		return "Success";
	}
}
