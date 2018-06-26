package com.mail.publisher.providers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.mail.publisher.beans.Email;
import com.mail.publisher.exceptions.MailPublishException;
import com.mail.publisher.util.ResponseCodes;

/**
 * Service class to publish emails through the available mail provider
 * @author MadanMeenu
 *
 */
@Service
public class MailPublisher implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("sendgrid")
	private MailProvider sendgrid;
	
	@Autowired
	@Qualifier("mailjet")
	private MailProvider mailjet;

	@Autowired
	private Environment env;
	
	private List<MailProvider> registeredMailProvidersInFallbackSequence;
	
	// Method invoked post instance creation and all properties are set
	@Override
	public void afterPropertiesSet() throws Exception {
		
		String mailProvidersFallbackSequence = env.getProperty("mail.providers.fallback.sequence");
		
		/*
		 * Split the comma separated string of provider alias and get the instance variables reference through ReflectionUtils.
		 * 
		 * This will hold the references of MailProvider's and ensure code will fail fast if the provider aliases are not defined correctly 
		 */
		registeredMailProvidersInFallbackSequence
			= Arrays.asList(mailProvidersFallbackSequence.split(","))
				.stream()
				.map(s -> (MailProvider) ReflectionUtils.getField(ReflectionUtils.findField(getClass(), s.trim()), this))
				.collect(Collectors.toList());
	}

	/**
	 * Method to strategically fallback to the next provider in sequence if there's a problem with the current provider
	 * 
	 * Throw MailPublishException if all providers could'nt publish the mail.
	 * 
	 * @param email
	 * @throws MailPublishException
	 */
	public void publishEmail(Email email) throws MailPublishException {
		
		for(MailProvider provider: registeredMailProvidersInFallbackSequence) {
			try {
				provider.sendEmail(email);
				return;
			}
			catch(Exception e) {
				logger.error("Error occurred while publishing email with provider : "+provider.getClass().getName(), e);
			}
		}
		
		throw new MailPublishException(ResponseCodes.FAILURE, "Email publish is not successful at this moment. Please try again later or contact us if issue persists");
	}

}
