package com.tek271.util2.net.email;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.tek271.util2.net.http.HttpTools.isHttp;
import static com.tek271.util2.net.http.HttpTools.url;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Emailer {
	private static final Logger LOGGER = LogManager.getLogger(Emailer.class);
	public String host;
	public int smtpPort = 465;
	public String userName;
	public String password;
	public boolean ssl;
	public boolean tls;
	public String from;
	public String fromName;
	public String subject;
	public EmailFormatEnum format = EmailFormatEnum.text;

	private String text;
	private final List<String> to = new ArrayList<>();
	private final List<EmailAttachment> attachments = new ArrayList<>();

	public void setText(String text, boolean isReplaceLineBreakForHtml) {
		if (isReplaceLineBreakForHtml) {
			text = StringUtils.replace(text, "\n", "<br>");
		}
		this.text = text;
	}

	public void setText(String text) {
		setText(text, false);
	}

	public String getText() {
		return text;
	}

	public void to(List<String> list) {
		list.forEach(this::to);
	}

	public void to(String to) {
		if (isNotBlank(to)) {
			this.to.add(to);
		}
	}

	public List<String> getTo() {
		return to;
	}


	public void attach(String path, String name, String description) {
		EmailAttachment attachment = createAttachment(name, description);
		if (isHttp(path)) {
			attachment.setURL(url(path));
		} else {
			attachment.setPath(path);
		}

		attachments.add(attachment);
	}

	public void attach(byte[] bytes, String name, String description) {
		File tempFile;
		try {
			tempFile = File.createTempFile("bytes-" + name, ".tecuj");
			FileUtils.writeByteArrayToFile(tempFile, bytes);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		attach(tempFile.getAbsolutePath(), name, description);
	}

	private EmailAttachment createAttachment(String name, String description) {
		EmailAttachment attachment = new EmailAttachment();
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setName(name);
		attachment.setDescription(description);

		return attachment;
	}


	public void send() {
		try {
			Email email = createEmail();
			email.send();
		} catch (EmailException e) {
			LOGGER.error("Cannot send email", e);
			throw new RuntimeException(e);
		}
	}

	Email createEmail() throws EmailException {
		adjustEmailFormat();
		Email email = format.createEmail(attachments);
		setEmailProperties(email);
		return email;
	}

	private void setEmailProperties(Email email) throws EmailException {
		email.setHostName(host);
		email.setSmtpPort(smtpPort);
		email.setAuthenticator(new DefaultAuthenticator(userName, password));
		email.setSSLOnConnect(ssl);
		email.setStartTLSEnabled(tls);
		email.setFrom(from, fromName);

		email.setSubject(subject);
		email.setMsg(text);

		email.addTo(to.toArray(new String[0]));
	}

	private void adjustEmailFormat() {
		if (attachments.isEmpty() || format.isAttachable) return;
		format = EmailFormatEnum.withAttachments;
	}


}
