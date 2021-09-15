package com.tek271.util2.net.email;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.substring;

public class EmailUtils {

  private final static String INVALID_EMAIL_CHARS = "()<>,;:\\\"[] \t\r\n";

/**
 * Validate that the given address is a syntactically valid email address.
 * It does not check if the address actually exists.
 * @param address String
 * @return String null/empty if the address is valid, else returns the text of the
 * error message.
 */
  public static String validateEmailAddress(String address) {
    if (isBlank(address)) return "has no value.";
    address= address.trim();
    //a@b.c
    if (address.length()<5) return "must be at least 5 characters.";
    
    if (StringUtils.containsAny(address, INVALID_EMAIL_CHARS)) {
    	return "Email address contains invalid characters.";
    }
    
    int at= address.indexOf('@');
    if (at<0) return "must contain the @ sign.";
    if (address.indexOf('.') <0) return "must contain dot.";

    String domain= substring(address, at+1);
    if (domain.length()==0) return "has no domain.";

    if (domain.charAt(0)=='.') return "has invalid domain (Cannot start with a dot).";
    if (domain.indexOf('.') <0) return "domain must contain a dot.";
    if (domain.indexOf('@') >=0) return "domain cannot contain the @ sign.";
    if (domain.endsWith(".")) return "domain cannot end with a dot.";

    return null;
  }
  
}
