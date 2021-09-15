package com.tek271.util2.net.email;

import org.apache.commons.mail.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Supplier;


@SuppressWarnings("unchecked")
public enum EmailFormatEnum {
	text(false, SimpleEmail::new),
	withAttachments(true, MultiPartEmail::new),
	html(true, HtmlEmail::new);

	public final boolean isAttachable;
	private final Supplier<? extends Email> supplier;

	private static final Logger LOGGER = LogManager.getLogger(EmailFormatEnum.class);

	public <T extends Email> T createEmail(List<EmailAttachment> attachments) {
		Email email = supplier.get();
		if (isAttachable) {
			attachments.forEach(a -> addAttachment((MultiPartEmail) email, a));
		}
		return (T) email;
	}


	EmailFormatEnum(boolean isAttachable, Supplier<? extends Email> supplier) {
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
