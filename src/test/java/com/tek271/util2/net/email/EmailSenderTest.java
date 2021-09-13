package com.tek271.util2.net.email;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;



public class EmailSenderTest {
	
	@Test @Ignore
  public void testSendTek271() throws Exception {
    EmailSender es= new EmailSender();
    es.host= "tek271.com";
    es.user= "validEmailAccount@tek271.com";
    es.password= "PASSWORD";
    es.isAuthentication= true;
    es.isSSL= false;
    
    es.from= "validEmailAccount@tek271.com";
    es.to= "somebody@yahoo.com";
    es.subject= "testSendTek271() - " + new Date();
    es.text= "This is a test mail";
    es.attachedFileNames= new String[] {"c:/temp/junk.txt"};
    es.send();
  }

	@Test @Ignore
  public void testSendGoogle() throws Exception {
    EmailSender es= new EmailSender();
    es.host= "smtp.gmail.com";
    es.user= "abdul";   // without @gmail.com
    es.password= "?????";   
    es.isAuthentication= true;
    es.isSSL= true;

    es.from= "abdul@gmail.com";
    es.to= "somebody@yahoo.com";
    es.subject= "testSendGoogle() - " + new Date();
    es.text= "This is a test mail";
    //es.attachedFileNames= new String[] {"/temp/test.pdf"};
    try {
      es.send();
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

}
