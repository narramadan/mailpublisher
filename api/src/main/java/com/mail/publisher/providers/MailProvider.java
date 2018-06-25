package com.mail.publisher.providers;

import com.mail.publisher.beans.Email;
import com.mail.publisher.exceptions.MailPublishException;

public interface MailProvider {

	public void sendEmail(Email email) throws MailPublishException;
}
