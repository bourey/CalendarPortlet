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

package org.jasig.portlet.calendar.adapter;

import java.util.Set;

import javax.portlet.PortletRequest;

import net.fortuna.ical4j.model.Period;

import org.jasig.portlet.calendar.CalendarConfiguration;
import org.jasig.portlet.calendar.CalendarEvent;

/**
 * ICalendarAdapter defines an interface for retrieving calendar event data.
 * All new calendar types must define an adapter using this interface, then be 
 * registered in the spring context files.
 *
 * @author Jen Bourey
 */
public interface ICalendarAdapter {

	/**
	 * Get events for the defined calendar and time period.  The user's 
	 * PortletRequest is made available to give the calendar adapter access
	 * to useful information such as the UserInfo map, session data, etc.
	 * These items can be used to identify the user, provide access to 
	 * authentication resources, or other useful operations.
	 * 
	 * @param calendar calendar configuration for which to retrieve events
	 * @param period time period for which to retrieve events
	 * @param request user's portlet request
	 * @return Set of events for this calendar and time period
	 * @throws CalendarException
	 */
	public Set<CalendarEvent> getEvents(CalendarConfiguration calendar,
			Period period, PortletRequest request) throws CalendarException;

	/**
	 * Get hyper link for the defined calendar. In cases where the calendar resource
	 * has a web interface, this method allows provides access to the url.
	 *  
	 * @param calendar calendar configuration for which to retrieve events
	 * @param period time period for which to retrieve events
	 * @param request user's servlet request
	 * @return Set of events for this calendar and time period
	 * @throws CalendarException
	 */
	public String getLink(CalendarConfiguration calendar,
			Period period, PortletRequest request) throws CalendarLinkException;
	
}
