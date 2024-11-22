package com.onsemi.cim.tarlac.triggers.services;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;


public class EmailManager {
	private static final Logger logger = Logger.getLogger(EmailManager.class);
	public static List<String> emails;

	public EmailManager() {
	}

	public static void sendMailReport(String subject, StringBuilder rpt) {
		InternetAddress[] to = new InternetAddress[emails.size()];
		try {
			for (int i = 0; i < emails.size(); i++) {
				to[i] = new InternetAddress((String) emails.get(0));
			}
			
			Properties props = System.getProperties();
			String smptHost = AppUtils.getProperty("smpt_host");
			props.put("mail.smtp.host", smptHost);
	
			Session session = Session.getDefaultInstance(props);
			Message msg = new MimeMessage(session);
			String senderAddress = AppUtils.getProperty("sender_address");
			msg.setFrom(new InternetAddress(senderAddress));		
			msg.setRecipients(Message.RecipientType.TO, to);
			msg.setSubject(subject);
			msg.setDataHandler(new DataHandler(new HTMLDataSource(rpt.toString())));
			//msg.setText(rpt.toString());
			msg.setSentDate(new Date());
			Transport.send(msg);
			logger.info("TOLD Lots successfully reported.");
		} catch (Exception e2) {
			logger.error(null, e2);
		}
	}
	

	public static void loadEmailConfig() throws Exception {
		FileInputStream fis = null;
		XMLStreamReader xsr = null;
		try {
			fis = new FileInputStream("config/email.xml");
			xsr = XMLInputFactory.newInstance().createXMLStreamReader(fis);
			emails = new ArrayList();
			while (xsr.hasNext()) {
				xsr.next();
				if ((xsr.isStartElement()) && (xsr.getLocalName().equals("EMAIL"))) {
					emails.add(xsr.getAttributeValue(0));
				}
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (xsr != null) {
				xsr.close();
			}
		}
	}
	
    static class HTMLDataSource implements DataSource {

        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("html message is null!");
            return new ByteArrayInputStream(html.getBytes());
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public String getName() {
            return "HTMLDataSource";
        }
    }
}
