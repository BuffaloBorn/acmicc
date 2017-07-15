package com.epm.acmi.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Mailer {
	//private static Logger log = Logger.getLogger(Mailer.class);
	private static Logger log = Logger.getLogger("Mailer.class");;
	
	private Mailer() {
    	PropertyConfigurator.configure("/fujitsu/ibpm/classes/log4j.properties");
	}

	private static Mailer mailer = null;

	public static Mailer getInstance() {
		if (mailer == null) {
			mailer = new Mailer();
		}
		return mailer;
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {

			String username = "";
			String password = "";
			return new PasswordAuthentication(username, password);
		}
	}

	protected Session getSession(String smtp_host)
	{
		Properties props = new Properties();
		props.put("mail.smtp.host", smtp_host);

		Session session = Session.getDefaultInstance(props, null);
		return session;
		
	}
	
	protected Message prepareHeader(Session session, String from, ArrayList tomailadd, String subject) throws IOException,
			AddressException, MessagingException {

		
		InternetAddress addr1[] = new InternetAddress[tomailadd.size()];
		Message msg = new MimeMessage(session);
		for (int i = 0; i < tomailadd.size(); i++) {
			String to = (String) tomailadd.get(i);
			InternetAddress addr = new InternetAddress(to);
			addr1[i] = addr;
		}

		msg.addRecipients(Message.RecipientType.TO, addr1);
		InternetAddress from_addr = new InternetAddress(from);
		msg.setFrom(from_addr);
		msg.setSubject(subject);

		return msg;
	}

	public void sendMail(String subject, String emailMessage, ArrayList to) throws IOException, AddressException,
			MessagingException {

		try {

			String from = LocalProperties.getProperty("smtpFrom");
			String smtp_host = LocalProperties.getProperty("smtpServer");
			Session session = getSession(smtp_host);
			
			Message msg = prepareHeader(session, from, to, subject);
			log.debug(emailMessage);
			msg.setContent(emailMessage, "text/html");

			Transport transport = session.getTransport("smtp");
			transport.connect(smtp_host, -1, "", "");
			transport.sendMessage(msg, msg.getAllRecipients());

		} catch (NoSuchProviderException e) {
			try {
				log.error("Email::EmailWithAttachment - NoSuchProviderException Caught - " + e);
				e.printStackTrace();
			} catch (Exception cex) {
			}

		} catch (SendFailedException e) {
			try {
				log.error("Email::EmailWithAttachment - SendFailedException Caught - " + e);
				e.printStackTrace();
			} catch (Exception cex) {
			}

		} catch (Exception e) {
			try {
				log.error("Email::EmailWithAttachment - Exception Caught - " + e);
				e.printStackTrace();
			} catch (Exception cex) {
			}
		}
	}

	public static void main(String[] args)
	{
		try {
			log.debug("Sending Mail to UA mailing list");
			String CatalinaHome = System.getProperty("catalina.home");	
			String fullFileName = "c:/Tomcat_5.0/webapps/acmicc/WEB-INF/classes/ApplicationResources_en.properties"; 
			Properties properties = new Properties();
			String emailMessage = null;
			String emailSubject = null;
			
			try {							
				properties.load(new FileInputStream(fullFileName));
				emailMessage = properties.getProperty("MailDetailMessage");
				emailSubject = properties.getProperty("emailSubject");
			} catch (IOException e) {
				
				String ErrorMsg = e.getMessage();
				Exception excp = new Exception("Failure  in Datapuller.runAppAddRules();"+ ErrorMsg);
				log.error(excp.getMessage());
				throw e;
			}
			
			ArrayList recipients = ACMICache.getEMailUARecipients();

			for (int i = 0; i < recipients.size(); i++)
			{
				log.debug("recipent # " + (i + 1) + ": " + recipients.get(i));
			}
			
			recipients = new ArrayList();
			recipients.add("nhiriyurkar@american-community.com");
			recipients.add("ccardenas@american-community.com");
			recipients.add("cesar.a.cardenas@gmail.com");
			
			Mailer ctp = Mailer.getInstance();
			ctp.sendMail(emailSubject, emailMessage, recipients);

			log.debug("Email was successful to UA users!");
		} catch (Exception e) {
			log.debug("Email failed in Datapuller.runAppAddRules()!");
		}
	}
}