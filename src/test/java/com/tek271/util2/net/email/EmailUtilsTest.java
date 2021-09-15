package com.tek271.util2.net.email;

import org.junit.jupiter.api.Test;

import static com.tek271.util2.net.email.EmailUtils.validateEmailAddress;
import static org.junit.jupiter.api.Assertions.*;

class EmailUtilsTest {

	@Test
	void testValidateEmailAddress() {
		assertEquals("has no value.", validateEmailAddress(null));
		assertEquals("has no value.", validateEmailAddress(""));
		assertEquals("has no value.", validateEmailAddress(" "));
		assertEquals("must be at least 5 characters.", validateEmailAddress("a"));

		assertEquals("must contain the @ sign.", validateEmailAddress("12345"));
		assertEquals("must contain dot.", validateEmailAddress("1234@"));
		assertEquals("has no domain.", validateEmailAddress("a12.3@"));
		assertEquals("has invalid domain (Cannot start with a dot).", validateEmailAddress("a12@."));
		assertEquals("domain must contain a dot.", validateEmailAddress("a1.2@d"));
		assertEquals("domain cannot contain the @ sign.", validateEmailAddress("a1.2@d.@"));
		assertEquals("domain cannot end with a dot.", validateEmailAddress("a1.2@d."));
	}

}