<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/css.jsp"/>
<c:set var="includeJQuery" value="${renderRequest.preferences.map['includeJQuery'][0]}"/>
<fmt:setTimeZone value="${ model.timezone }"/>
<c:set var="n"><portlet:namespace/></c:set>

<c:if test="${includeJQuery}">
    <script type="text/javascript" src="<rs:resourceURL value="/rs/jquery/1.3.2/jquery-1.3.2.min.js"/>"></script>
    <script type="text/javascript" src="<rs:resourceURL value="/rs/jqueryui/1.7.2/jquery-ui-1.7.2.min.js"/>"></script>
    <script type="text/javascript" src="<rs:resourceURL value="/rs/fluid/1.1.2/js/fluid-all-1.1.2.min.js"/>"></script>
</c:if>
<script type="text/javascript" src="<c:url value="/scripts/CalendarView.js"/>"></script>

<script type="text/javascript">
    var cal = cal || {};
    cal.jQuery = jQuery.noConflict(${includeJQuery});
    <c:if test="${includeJQuery}">delete fluid; delete fluid_1_1;</c:if>
    cal.jQuery(function(){
        var $ = cal.jQuery;
        var eventsUrl = '<portlet:actionURL><portlet:param name="action" value="events"/></portlet:actionURL>';
        var days = ${ model.days };
        var calView;
        
        $(document).ready(function(){
            var startDate = '<fmt:formatDate value="${model.startDate}" type="date" pattern="MM/dd/yyyy"/>';
            calView = cal.CalendarView(".upcal-fullview", { eventsUrl: eventsUrl, startDate: startDate, days: days })
            var date = new Date();
            date.setFullYear(<fmt:formatDate value="${model.startDate}" pattern="yyyy"/>, Number(<fmt:formatDate value="${model.startDate}" pattern="M"/>)-1, <fmt:formatDate value="${model.startDate}" pattern="d"/>);
            $('#${n}inlineCalendar').datepicker(
                {
                    inline: true,
                    changeMonth: false,
                    changeYear: false,
                    defaultDate: date,
                    onSelect: function(date) {
                        calView.updateEventList(date, days);
                    } 
                }
            );

            $(".upcal-range-day a").click(function(){
                days = $(this).attr("days");
                calView.updateEventList(date, days);
                $(".upcal-range-day a").removeClass("selected-range");
                $(this).addClass("selected-range");
            });
        });
    });
</script>

<div class="upcal-fullview">

<c:if test="${ !model.guest && !(model.disablePreferences && (!sessionScope.isAdmin || model.disableAdministration)) }">
	<div class="upcal-edit-links">
        <c:if test="${ !model.disablePreferences }">
            <portlet:renderURL var="preferencesUrl" portletMode="edit"><portlet:param name="action" value="editPreferences"/></portlet:renderURL>
            <a href="${ preferencesUrl }" title="<spring:message code="preferences.link.title"/>">
                <spring:message code="preferences.link.text"/>
            </a>
        </c:if>
	    <c:if test="${ sessionScope.isAdmin && !model.disableAdministration }">
	        <span class="upcal-pipe">|</span>
	        <portlet:renderURL var="adminUrl" portletMode="edit"><portlet:param name="action" value="administration"/></portlet:renderURL>
	        <a href="${ adminUrl }" title="<spring:message code="administration.link.title"/>">
	            <spring:message code="administration.link.text"/>
	        </a>
	    </c:if>
	</div>
</c:if>

<div id="calendarPortletHeader" class="fl-col-mixed3">

	<div class="fl-col-side fl-force-right">
		<div class="upcal-showcals upcal-list">
    		<h3><spring:message code="view.main.calendarlist.header"/></h3>
    		<ul>
                <c:forEach items="${ model.calendars }" var="calendar">
                    <li class="color-${ model.colors[calendar.id] }">
                        <c:choose>
                            <c:when test="${ empty model.hiddenCalendars[calendar.id] }">
                                <portlet:renderURL var="url"><portlet:param name="hideCalendar" value="${ calendar.id }"/></portlet:renderURL>
                                <a class="upcal-active" href="${ url }">
                                    <span><spring:message code="calendar.active"/></span>
                                </a> 
                            </c:when>
                            <c:otherwise>
                                <portlet:renderURL var="url"><portlet:param name="showCalendar" value="${ calendar.id }"/></portlet:renderURL>
                                <a class="upcal-inactive" href="${ url }">
                                    <span><spring:message code="calendar.inactive"/></span>
                                </a>
                            </c:otherwise>
                        </c:choose>
                        <span><spring:escapeBody htmlEscape="true">${ calendar.calendarDefinition.name }</spring:escapeBody></span>
                    </li>
                </c:forEach>
   		</ul>
		</div>
	</div>
	
	<div class="fl-col-side fl-force-left">
        <div id="<portlet:namespace/>inlineCalendar" class="jqueryui"></div>
        <div style="clear: both;"></div>
	</div>
	
	<div class="fl-col-main">
	
		<div id="${n}calendarRangeSelector" class="upcal-range upcal-hide-on-event">
            <h3><spring:message code="view.main.range.header"/></h3>
                <span class="upcal-range-day" days="1">
                    <a days="1" href="javascript:;" class="${ model.days == 1 ? "selected-range" : "" }">
                        <spring:message code="calendar.range.day"/>
                    </a>
                </span>
                <span class="upcal-pipe">|</span>
                <span class="upcal-range-day" days="7">
                    <a days="7" href="javascript:;" class="${ model.days == 7 ? "selected-range" : "" }">
                        <spring:message code="calendar.range.week"/>
                    </a>
                </span>
                <span class="upcal-pipe">|</span>
                <span class="upcal-range-day" days="31">
                    <a days="31" href="javascript:;" class="${ model.days == 31 ? "selected-range" : "" }">
                        <spring:message code="calendar.range.month"/>
                    </a>
                </span>
		</div>
		
		<div class="upcal-event-list">
    		<p class="upcal-loading-message"><spring:message code="eventlist.loading"/></p>
    		
            <div class="upcal-events"></div>
            
            <!-- View Links -->
            <div id="${n}viewLinks" class="upcal-view-links upcal-hide-on-calendar">
                <a id="${n}returnToCalendarLink" class="upcal-view-return" href="javascript:;"
		                title="<spring:message code="return.to.event.list.link.title"/>">
		           <spring:message code="return.to.event.list.link.text"/>
                </a>
            </div>
            
		</div>
		
	</div>
</div>

</div>
