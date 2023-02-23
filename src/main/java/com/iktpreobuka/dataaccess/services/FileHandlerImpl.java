package com.iktpreobuka.dataaccess.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Service
public class FileHandlerImpl implements FileHandler{

	public static String UPLOADED_FOLDER = "C:\\temp\\";
	
	@Override
	public String singleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
		if(file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file");
			return "redirect:/uploadStatus";
		}
		try {
			byte[] bytes = file.getBytes();
			
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			redirectAttributes.addFlashAttribute("message", "Success!");
			
		}catch(IOException e) {
			throw e;
		}
		// TODO Auto-generated method stub
		return "redirect:/uploadStatus";
	}
	}
