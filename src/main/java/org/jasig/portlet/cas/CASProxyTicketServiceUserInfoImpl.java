/*
 * Created on Feb 5, 2008
 *
 * Copyright(c) Yale University, Feb 5, 2008.  All rights reserved.
 * (See licensing and redistribution disclosures at end of this file.)
 * 
 */
package org.jasig.portlet.cas;

import java.util.Map;

import javax.portlet.PortletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;

public class CASProxyTicketServiceUserInfoImpl implements ICASProxyTicketService {
	
	private static Log log = LogFactory.getLog(CASProxyTicketServiceUserInfoImpl.class);

	private String serviceUrl;
	
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	private TicketValidator ticketValidator;
	
	public void setTicketValidator(TicketValidator ticketValidator) {
		this.ticketValidator = ticketValidator;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.jasig.portlet.cas.ICASProxyTicketService#getProxyTicket(javax.portlet.PortletRequest)
	 */
	public Assertion getProxyTicket(PortletRequest request) {

		// retrieve the CAS ticket from the UserInfo map
		@SuppressWarnings("unchecked")
		Map<String,String> userinfo = (Map<String,String>) request.getAttribute(PortletRequest.USER_INFO);
		String ticket = (String) userinfo.get("casProxyTicket");
		
		if (ticket == null) {
			log.debug("No CAS ticket found in the UserInfo map");
			return null;
		}
		
		log.debug("serviceURL: " + this.serviceUrl + ", ticket: " + ticket);
		
		/* contact CAS and validate */
		
		try {
			Assertion assertion = ticketValidator.validate(ticket, this.serviceUrl);
			return assertion;
		} catch (TicketValidationException e) {
			log.warn("Failed to validate proxy ticket", e);
			return null;
		}

	}
	
	/*
	 * (non-Javadoc)
	 * @see org.jasig.portlet.cas.ICASProxyTicketService#getCasServiceToken(edu.yale.its.tp.cas.client.CASReceipt, java.lang.String)
	 */
	public String getCasServiceToken(Assertion assertion, String target) {
        final String proxyTicket = assertion.getPrincipal().getProxyTicketFor(target);
        if (proxyTicket == null){
            log.error("Failed to retrieve proxy ticket for assertion [" + assertion.toString() + "].  Is the PGT still valid?");
            return null;
        }
        if (log.isTraceEnabled()) {
            log.trace("returning from getCasServiceToken(), returning proxy ticket ["
                    + proxyTicket + "]");
        }
        return proxyTicket;
	}

}
