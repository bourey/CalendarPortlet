package edu.yale.its.tp.portlets.calendar;

import java.util.Comparator;

import net.fortuna.ical4j.model.component.VEvent;

/**
 * VEventStartComparator compares to VEvents and orders them
 * by starting date.  For events that start at the time, whichever
 * event ends first will be considered "first".
 * 
 * @author Jen Bourey
 */
public class VEventStartComparator implements Comparator<VEvent> {

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(VEvent event1, VEvent event2) {
		
		if (event1.getStartDate().getDate().before(event2.getStartDate().getDate()))
			return -1;
		else if (event1.getStartDate().getDate().after(event2.getStartDate().getDate()))
			return 1;
		else if (event1.getStartDate().getDate().equals(event2.getStartDate().getDate())) {
			if (event1.getEndDate() == null && event2.getEndDate() == null)
				return 0;
			else if (event1.getEndDate() == null)
				return -1;
			else if (event2.getEndDate() == null)
				return 1;
			if (event1.getEndDate().getDate().before(event2.getEndDate().getDate()))
				return -1;
			else if (event1.getEndDate().getDate().before(event2.getEndDate().getDate()))
				return 1;
		}

		int comp = 0;
		
		if (event1.getSummary() != null && event2.getSummary() != null) {
			comp = event1.getSummary().getValue().compareTo(event2.getSummary().getValue());
			if (comp != 0)
				return comp;
		}
		if (event1.getName() != null && event2.getName() != null) {
			comp = event1.getName().compareTo(event2.getName());
			if (comp != 0)
				return comp;
		}
		if (event1.getDescription() != null && event2.getDescription() != null) {
			comp = event1.getDescription().getValue().compareTo(event2.getDescription().getValue());
			if (comp != 0)
				return comp;
		}
		return 0;
	}

}
