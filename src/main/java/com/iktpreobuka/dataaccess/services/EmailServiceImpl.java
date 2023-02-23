package com.iktpreobuka.dataaccess.services;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.dataaccess.models.EmailObject;
@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	public JavaMailSender emailSender;

	@Override
	public void sendMessageWithAttachment(EmailObject object, String pathToAttachment) throws Exception {
		// TODO Auto-generated method stub
		MimeMessage mail = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setTo(object.getTo());
		helper.setSubject(object.getSubject());
		helper.setText(object.getText(),false);
		
		
		FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
		
		helper.addAttachment(file.getFilename(), file);
		
		emailSender.send(mail);
	}

}
