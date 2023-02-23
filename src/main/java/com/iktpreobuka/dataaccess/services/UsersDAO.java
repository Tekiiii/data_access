package com.iktpreobuka.dataaccess.services;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.dataaccess.models.EmailObject;

public interface UsersDAO {
	
	public String saveAllUsersFromFile(Path path) throws IOException;
	public String writeAllUsersToFile() throws IOException;
	public String sendFile(EmailObject object, MultipartFile file) throws Exception;
}

