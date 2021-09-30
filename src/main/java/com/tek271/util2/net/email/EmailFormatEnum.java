package com.tek271.util2.net.email;

import org.apache.commons.mail.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Supplier;

/**
 * Type of email to be sent: text, text with attachments, html with attachments
 */
public enum EmailFormatEnum {
	text(false, SimpleEmail::new),
	withAttachments(true, MultiPartEmail::new),
	html(true, HtmlEmail::new);

	public final boolean isAttachable;
	private final Supplier<Email> supplier;

	private static final Logger LOGGER = LogManager.getLogger(EmailFormatEnum.class);

	public Email createEmail(List<EmailAttachment> attachments) {
		Email email = supplier.get();
		if (isAttachable) {
			attachments.forEach(a -> addAttachment((MultiPartEmail) email, a));
		}
		return email;
	}


	EmailFormatEnum(boolean isAttachable, Supplier<Email> supplier) {
		this.isAttachable = isAttachable;
		this.supplier = supplier;
	}

	private static void addAttachment(MultiPartEmail email, EmailAttachment attachment) {
		try {
			email.attach(attachment);
		} catch (EmailException e) {
			LOGGER.error("Cannot attach " + attachment.getName(), e);
			throw new RuntimeException(e);
		}
	}

}
