/*
 * Created on Feb 5, 2008
 *
 * Copyright(c) Yale University, Feb 5, 2008.  All rights reserved.
 * (See licensing and redistribution disclosures at end of this file.)
 * 
 */
package edu.yale.its.tp.portlets.calendar;

import java.util.Calendar;

import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Wraps the VEvent calendar class to provide extra information.
 * 
 * @author Jen Bourey
 */
public class CalendarEvent extends VEvent {

	private static final long serialVersionUID = 1L;
	private Long calendarId;

	public CalendarEvent() {
		super();
	}

	public CalendarEvent(Date start, Date end, java.lang.String summary) {
		super(start, end, summary);
	}

	public CalendarEvent(Date start, Dur duration, java.lang.String summary) {
		super(start, duration, summary);
	}

	public CalendarEvent(Date start, java.lang.String summary) {
		super(start, summary);
	}

	public CalendarEvent(Long calendarId, PropertyList properties) {
		super(properties);
		this.calendarId = calendarId;
	}

	public CalendarEvent(PropertyList properties, ComponentList alarms) {
		super(properties, alarms);
	}
	
	public CalendarEvent(PropertyList properties) {
		super(properties);
	}

	public Long getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(Long calendarId) {
		this.calendarId = calendarId;
	}

	public boolean isAllDay() {
		Calendar start = Calendar.getInstance();
		start.setTimeInMillis(this.getStartDate().getDate().getTime());
		long oneday = 1000 * 60 * 60 * 24;
		if (start.get(Calendar.HOUR_OF_DAY) == 0 && start.get(Calendar.MINUTE) == 0 && this.getEndDate().getDate().getTime() - this.getStartDate().getDate().getTime() >= oneday)
			return true;
		else
			return false;
	}
	
}

/*
 * CalendarEvent.java
 * 
 * Copyright (c) Feb 5, 2008 Yale University. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE, ARE EXPRESSLY DISCLAIMED. IN NO EVENT SHALL
 * YALE UNIVERSITY OR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED, THE COSTS OF PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED IN ADVANCE OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Redistribution and use of this software in source or binary forms, with or
 * without modification, are permitted, provided that the following conditions
 * are met.
 * 
 * 1. Any redistribution must include the above copyright notice and disclaimer
 * and this list of conditions in any related documentation and, if feasible, in
 * the redistributed software.
 * 
 * 2. Any redistribution must include the acknowledgment, "This product includes
 * software developed by Yale University," in any related documentation and, if
 * feasible, in the redistributed software.
 * 
 * 3. The names "Yale" and "Yale University" must not be used to endorse or
 * promote products derived from this software.
 */