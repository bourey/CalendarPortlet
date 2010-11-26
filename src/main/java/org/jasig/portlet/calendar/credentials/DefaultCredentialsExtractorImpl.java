/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.portlet.calendar.credentials;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Credentials;


/**
 * Default implementation returns null for both methods.
 * 
 * @author Nicholas Blair, nblair@doit.wisc.edu
 * @version $Header: DefaultCredentialsExtractorImpl.java Exp $
 */
public class DefaultCredentialsExtractorImpl implements ICredentialsExtractor {

	/* (non-Javadoc)
	 * @see org.jasig.portlet.calendar.adapter.CredentialsExtractor#getCredentials(javax.servlet.http.HttpServletRequest)
	 */
	public Credentials getCredentials(HttpServletRequest request) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jasig.portlet.calendar.adapter.CredentialsExtractor#getCredentials(javax.portlet.PortletRequest)
	 */
	public Credentials getCredentials(PortletRequest request) {
		return null;
	}

}
