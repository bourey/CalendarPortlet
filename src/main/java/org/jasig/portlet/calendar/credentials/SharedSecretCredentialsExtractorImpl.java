/*******************************************************************************
* Copyright 2008, The Board of Regents of the University of Wisconsin System.
* All rights reserved.
*
* A non-exclusive worldwide royalty-free license is granted for this Software.
* Permission to use, copy, modify, and distribute this Software and its
* documentation, with or without modification, for any purpose is granted
* provided that such redistribution and use in source and binary forms, with or
* without modification meets the following conditions:
*
* 1. Redistributions of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation
* and/or other materials provided with the distribution.
*
* 3. Redistributions of any form whatsoever must retain the following
* acknowledgement:
*
* "This product includes software developed by The Board of Regents of
* the University of Wisconsin System.
*
*THIS SOFTWARE IS PROVIDED BY THE BOARD OF REGENTS OF THE UNIVERSITY OF
*WISCONSIN SYSTEM "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING,
*BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
*PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE BOARD OF REGENTS OF
*THE UNIVERSITY OF WISCONSIN SYSTEM BE LIABLE FOR ANY DIRECT, INDIRECT,
*INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
*LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
*PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
*LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
*OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
*ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*******************************************************************************/
package org.jasig.portlet.calendar.credentials;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;


/**
 * This {@link ICredentialsExtractor} implementation allows deployers to
 * specify a "shared" set of credentials that are used for ALL requests.
 * 
 * @author Nicholas Blair, nblair@doit.wisc.edu
 * @version $Header: SharedSecretCredentialsExtractorImpl.java Exp $
 */
public class SharedSecretCredentialsExtractorImpl implements
		ICredentialsExtractor {

	private final String username;
	private final String password;
	
	/**
	 * @param password
	 * @param username
	 */
	public SharedSecretCredentialsExtractorImpl(String password, String username) {
		this.password = password;
		this.username = username;
	}

	/* (non-Javadoc)
	 * @see org.jasig.portlet.calendar.adapter.CredentialsExtractor#getCredentials(javax.servlet.http.HttpServletRequest)
	 */
	public Credentials getCredentials(HttpServletRequest request) {
		return new UsernamePasswordCredentials(username, password);
	}

	/* (non-Javadoc)
	 * @see org.jasig.portlet.calendar.adapter.CredentialsExtractor#getCredentials(javax.portlet.PortletRequest)
	 */
	public Credentials getCredentials(PortletRequest request) {
		return new UsernamePasswordCredentials(username, password);
	}

}
