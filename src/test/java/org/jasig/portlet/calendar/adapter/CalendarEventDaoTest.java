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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Set;

import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryImpl;
import net.fortuna.ical4j.model.component.VEvent;

import org.jasig.portlet.calendar.mvc.CalendarDisplayEvent;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

public class CalendarEventDaoTest {
    
    CalendarEventsDao eventDao = new CalendarEventsDao();
    TimeZoneRegistry tzRegistry = new TimeZoneRegistryImpl();
    
    @Test
    public void testGetTimeFormatter() {
        DateTimeZone tz1 = DateTimeZone.forID("America/Los_Angeles");
        DateTimeZone tz2 = DateTimeZone.forID("America/Chicago");

        DateTimeFormatter timeFormatter1 = eventDao.getTimeFormatter(tz1);
        DateTimeFormatter timeFormatter2 = eventDao.getTimeFormatter(tz2);
        
        assertNotNull(timeFormatter1);
        assertNotNull(timeFormatter2);
        
        assertSame(timeFormatter1, eventDao.getTimeFormatter(tz1));
        assertSame(timeFormatter2, eventDao.getTimeFormatter(tz2));
    }

    @Test
    public void testGetDateFormatter() {
        DateTimeZone tz1 = DateTimeZone.forID("America/Los_Angeles");
        DateTimeZone tz2 = DateTimeZone.forID("America/Chicago");

        DateTimeFormatter dateFormatter1 = eventDao.getDateFormatter(tz1);
        DateTimeFormatter dateFormatter2 = eventDao.getDateFormatter(tz2);
        
        assertNotNull(dateFormatter1);
        assertNotNull(dateFormatter2);
        
        assertSame(dateFormatter1, eventDao.getDateFormatter(tz1));
        assertSame(dateFormatter2, eventDao.getDateFormatter(tz2));
    }

    @Test
    public void testGetDisplayEvents() throws IOException, URISyntaxException, ParseException {

        DateTimeZone tz = DateTimeZone.forID("America/Los_Angeles");

        DateTime eventStart = new DateTime(2012, 1, 4, 17, 0, tz);
        DateTime eventEnd = new DateTime(2012, 1, 4, 18, 0, tz);
        
        VEvent event = new VEvent(getICal4jDate(eventStart, tz), getICal4jDate(
                eventEnd, tz), "Test Event");
        
        DateMidnight intervalStart = new DateMidnight(2012, 1, 3, tz);
        DateMidnight intervalStop = new DateMidnight(2012, 1, 5, tz);
        Interval interval = new Interval(intervalStart, intervalStop);
       
        Set<CalendarDisplayEvent> events = eventDao.getDisplayEvents(event, interval, tz);
        
        assertEquals(1, events.size());
        
    }
    
    @Test
    public void testGetDisplayEventsForLongEvent() throws IOException, URISyntaxException, ParseException {

        DateTimeZone tz = DateTimeZone.forID("America/Los_Angeles");

        DateTime eventStart = new DateTime(2012, 1, 2, 17, 0, tz);
        DateTime eventEnd = new DateTime(2012, 1, 6, 2, 0, tz);
        
        VEvent event = new VEvent(getICal4jDate(eventStart, tz), getICal4jDate(
                eventEnd, tz), "Test Event");
        
        DateMidnight intervalStart = new DateMidnight(2012, 1, 3, tz);
        DateMidnight intervalStop = new DateMidnight(2012, 1, 5, tz);
        Interval interval = new Interval(intervalStart, intervalStop);
       
        Set<CalendarDisplayEvent> events = eventDao.getDisplayEvents(event, interval, tz);
        
        assertEquals(3, events.size());
        
    }
    
    @Test
    public void testGetDisplayEventsForNoEndDate() throws IOException, URISyntaxException, ParseException {

        DateTimeZone tz = DateTimeZone.forID("America/Los_Angeles");

        DateTime eventStart = new DateTime(2012, 1, 4, 17, 0, tz);
        
        VEvent event = new VEvent(getICal4jDate(eventStart, tz), "Test Event");

        DateMidnight intervalStart = new DateMidnight(2012, 1, 3, tz);
        DateMidnight intervalStop = new DateMidnight(2012, 1, 5, tz);
        Interval interval = new Interval(intervalStart, intervalStop);
       
        Set<CalendarDisplayEvent> events = eventDao.getDisplayEvents(event, interval, tz);
        
        assertEquals(1, events.size());
        
    }

    @Test
    public void testGetDisplayEventsForNoEndDateStartAbutment() throws IOException, URISyntaxException, ParseException {

        DateTimeZone tz = DateTimeZone.forID("America/Los_Angeles");

        DateTime eventStart = new DateTime(2012, 1, 3, 0, 0, tz);
        
        VEvent event = new VEvent(getICal4jDate(eventStart, tz), "Test Event");

        DateMidnight intervalStart = new DateMidnight(2012, 1, 3, tz);
        DateMidnight intervalStop = new DateMidnight(2012, 1, 5, tz);
        Interval interval = new Interval(intervalStart, intervalStop);
       
        Set<CalendarDisplayEvent> events = eventDao.getDisplayEvents(event, interval, tz);
        
        assertEquals(1, events.size());
        
    }
    
    public net.fortuna.ical4j.model.DateTime getICal4jDate(DateTime date, DateTimeZone timezone) {
        net.fortuna.ical4j.model.DateTime ical = new net.fortuna.ical4j.model.DateTime(date.toDate());
        ical.setTimeZone(tzRegistry.getTimeZone(timezone.getID()));
        return ical;
    }

}
