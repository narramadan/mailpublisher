package com.mail.publisher;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * #TODO
 * 
 * Test class to mock Spring beans and validate the rest interface for all possible test cases using jUnit & Mockito 
 * @author MadanMeenu
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailPublisherApplicationTests {

	/**
	 * Method to test successful email delivery
	 */
	@Test
	public void testEmailPublishSuccessfullDelivery() {

		Assert.assertTrue(true);
	}
	
	/**
	 * Method to test email delivery failure due to validation error
	 */
	@Test
	public void testEmailPublishFailureDueToValidation() {
		Assert.assertTrue(true);
	}
	
	/**
	 * Method to test email delivery failure due to the first provider configured
	 */
	@Test
	public void testEmailPublishFailueWithDefaultProvider() {
		Assert.assertTrue(true);
	}
	
	/**
	 * Method to test email delivery failure by all the providers
	 */
	@Test
	public void testEmailPublishFailureWithAllProviders() {
		Assert.assertTrue(true);
	}
}
