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
package org.jasig.portlet.calendar.mvc.controller;

import javax.annotation.Resource;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.jasig.portlet.calendar.PredefinedCalendarDefinition;
import org.jasig.portlet.calendar.dao.CalendarStore;
import org.jasig.portlet.calendar.mvc.CalendarDefinitionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

/**
 * EditCalendarDefinitionController provides a GUI for adding and editing 
 * predefined calendars.
 *
 * @author Jen Bourey
 */
@Controller
@RequestMapping("EDIT")
public class EditCalendarDefinitionController {

	private static final String FORM_NAME = "calendarDefinitionForm";

    private Validator validator;
    
    @Autowired(required = true)
    public void setValidator(Validator validator) {
    	this.validator = validator;
    }

    private CalendarStore calendarStore;

	@Required
	@Resource(name="calendarStore")
	public void setCalendarStore(CalendarStore calendarStore) {
		this.calendarStore = calendarStore;
	}

	@RequestMapping(params = "action=editCalendarDefinition")
	public String showCalendarDefinitionForm(PortletRequest request, Model model){
		if (!model.containsAttribute(FORM_NAME)) {
			model.addAttribute(FORM_NAME, getCalendarDefinitionForm(request));
		}
		return "/editCalendarDefinition";
	}

	@RequestMapping(params = "action=editCalendarDefinition")
	public void updateCalendarDefinition(ActionRequest request, 
			ActionResponse response, @ModelAttribute(FORM_NAME) CalendarDefinitionForm form,
			BindingResult result, SessionStatus status) {
		
		validator.validate(form, result);
		if (result.hasErrors()) {
			response.setRenderParameter("action", "editCalendarDefinition");
	    	return;
		}
		
		// construct a calendar definition from the form data
		PredefinedCalendarDefinition definition = null;
		
		// If an id was submitted, retrieve the calendar definition we're
		// trying to edit.  Otherwise, create a new definition. 
		if (form.getId() > -1)
			definition = calendarStore.getPredefinedCalendarDefinition(form.getId());
		else
			definition = new PredefinedCalendarDefinition();
	
		// set the calendar definition properties based on the 
		// submitted form
        definition.setClassName(form.getClassName());
        definition.setDefaultRoles(form.getRole());
        definition.setName(form.getName());
        definition.setFname(form.getFname());
        definition.setParameters(form.getParameters());
	
		// save the calendar definition
		calendarStore.storeCalendarDefinition(definition);
		
		// send the user back to the main administration page
		response.setRenderParameter("action", "administration");
	
	}

	protected CalendarDefinitionForm getCalendarDefinitionForm(PortletRequest request) {
		// if we're editing a calendar, retrieve the calendar definition from
		// the database and add the information to the form
		String id = request.getParameter("id");
		if (id != null && !id.equals("")) {
			Long definitionId = Long.parseLong(id);
			if (definitionId > -1) {
				PredefinedCalendarDefinition definition = calendarStore.getPredefinedCalendarDefinition(definitionId);
				CalendarDefinitionForm command = new CalendarDefinitionForm();
				command.setId(definition.getId());
                command.setName(definition.getName());
                command.setFname(definition.getFname());
				command.setClassName(definition.getClassName());
				command.setRole(definition.getDefaultRoles());
				command.addParameters(definition.getParameters());
				return command;
			} else {
				// otherwise, construct a brand new form
				// create the form
				return new CalendarDefinitionForm();
			}

		} else {
			// otherwise, construct a brand new form
			// create the form
			return new CalendarDefinitionForm();
		}
	}

}
