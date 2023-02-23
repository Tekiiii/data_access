package com.iktpreobuka.dataaccess.services;

import com.iktpreobuka.dataaccess.models.EmailObject;

public interface EmailService {
	public void sendMessageWithAttachment(EmailObject object, String pathToAttachment) throws Exception;
	}
