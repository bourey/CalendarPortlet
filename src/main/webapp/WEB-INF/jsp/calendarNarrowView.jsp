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

<c:set var="n"><portlet:namespace/></c:set>

<rs:aggregatedResources path="${ usePortalJsLibs ? '/skin-shared.xml' : '/skin.xml' }"/>

<script type="text/javascript"><rs:compressJs>
    var ${n} = ${n} || {};
    <c:choose>
        <c:when test="${!usePortalJsLibs}">
            ${n}.jQuery = jQuery.noConflict(true);
            ${n}.fluid = fluid;
            fluid = null; 
            fluid_1_4 = null;
        </c:when>
        <c:otherwise>
            <c:set var="ns"><c:if test="${ not empty portalJsNamespace }">${ portalJsNamespace }.</c:if></c:set>
            ${n}.jQuery = ${ ns }jQuery;
            ${n}.fluid = ${ ns }fluid;
        </c:otherwise>
    </c:choose>
    if (!cal.initialized) cal.init(${n}.jQuery, ${n}.fluid);
    ${n}.cal = cal;
    ${n}.jQuery(function() {
        var $ = ${n}.jQuery;
        var cal = ${n}.cal;
        
        // The 'days' variable is used in functions beyond the CalendarView
        var days = ${model.days};

        var options = {
            eventsUrl: '<portlet:resourceURL/>', 
            startDate: '<fmt:formatDate value="${model.startDate}" type="date" pattern="MM/dd/yyyy" timeZone="${model.timezone}"/>', 
            days: days,
            messages: {
                allDay: '<spring:message code="all.day"/>'
            }
        };
        var calView = cal.CalendarView("#${n}container", options);

        var date = new Date();
        date.setFullYear(<fmt:formatDate timeZone="${model.timezone}" value="${model.startDate}" pattern="yyyy"/>, Number(<fmt:formatDate value="${model.startDate}" pattern="M" timeZone="${model.timezone}"/>)-1, <fmt:formatDate value="${model.startDate}" pattern="d" timeZone="${model.timezone}"/>);
        $('#${n}inlineCalendar').datepicker(
            {
                inline: true,
                changeMonth: false,
                changeYear: false,
                defaultDate: date,
                onSelect: function(date) {
                    calView.updateEventList(date, calView.options.days);
                } 
            }
        );

        $("#${n}container .upcal-range-day a").click(function(){
            days = $(this).attr("days");
            calView.updateEventList(calView.options.startDate, days);
            $(".upcal-range-day a").removeClass("selected-range");
            $(this).addClass("selected-range");
        });

        var datepickerShowHide = function( showFlag ) {
            if ( showFlag == "true" ) {
                $('#${n}inlineCalendar').show();
            } else {
                $('#${n}inlineCalendar').hide();
            }
        }

        datepickerShowHide( "${model.showDatePicker}" );

        $("#${n}container .upcal-range-datepicker a").click(function(){
            datepickerShowHide( $(this).attr("show") );
            $(".upcal-range-datepicker a").removeClass("selected-range");
            $(this).addClass("selected-range");
        });

    });
</rs:compressJs></script>

<div id="${n}container" class="${n}upcal-miniview">

    <!-- Range Selector -->
    <div id="${n}calendarRangeSelector" class="upcal-range">
        <h3><spring:message code="view"/></h3>
        <span class="upcal-range-day" days="1">
            <a days="1" href="javascript:;" class="${ model.days == 1 ? "selected-range" : "" }">
                <spring:message code="day"/>
            </a>
        </span>
        <span class="upcal-pipe">|</span>
        <span class="upcal-range-day" days="7">
            <a days="7" href="javascript:;" class="${ model.days == 7 ? "selected-range" : "" }">
                <spring:message code="week"/>
            </a>
        </span>
        <span class="upcal-pipe">|</span>
        <span class="upcal-range-day" days="31">
            <a days="31" href="javascript:;" class="${ model.days == 31 ? "selected-range" : "" }">
                <spring:message code="month"/>
            </a>
        </span>
        <span class="upcal-pipe">&nbsp;&nbsp;&nbsp;</span>
        <h3><spring:message code="date.picker"/></h3>
        <span class="upcal-range-datepicker" show="true">
            <a show="true" href="javascript:;" class="${ model.showDatePicker == true ? "selected-range" : "" }">
                <spring:message code="show"/>
            </a>
        </span>
        <span class="upcal-pipe">|</span>
        <span class="upcal-range-datepicker" show="false">
            <a show="false" href="javascript:;" class="${ model.showDatePicker == false ? "selected-range" : "" }">
                <spring:message code="hide"/>
            </a>
        </span>
    </div>
    
    <!-- Mini-Calendar (jQuery) -->
    <div id="${n}inlineCalendar" class="jqueryui"></div>
    
    <!-- Calendar Events List -->
    <p class="upcal-loading-message"><spring:message code="loading"/></p>
    <div class="upcal-events">
        <div class="upcal-events upcal-event-list upcal-hide-on-event" style="display:none">
            <div class="portlet-msg-error upcal-errors">
                <div class="upcal-error"></div>
            </div>
            <div class="portlet-msg-info upcal-noevents">
                <p>No events</p>
            </div>
            <div class="day">
                <h2 class="dayName">Today</h2>
                    <div class="upcal-event-wrapper">
                        <div class="upcal-event">
                            <div class="upcal-event-cal">
                                <span></span>
                            </div>
                            <span class="upcal-event-time">All Day</span>
                            <h3 class="upcal-event-title">
                                <a class="upcal-event-link" href="javascript:;">Event Summary</a>
                            </h3>
                        </div>
                    </div>
            </div>
        </div>
        
        <div class="upcal-event-details upcal-hide-on-calendar">

            <div class="upcal-event-detail">
                <!-- Event title -->
                <h2 class="upcal-event-detail-summary">Event Summary</h2>
          
                <!-- Calendar event is from -->
                <div class="upcal-event-detail-cal">
                    <span> <!-- Calendar name to go here. --> </span>
                </div>
          
                <!-- Event time -->
                <div class="event-detail-date">
                    <h3><spring:message code="date"/>:</h3>
                    <p>
                        <span class="upcal-event-detail-day">Today</span>
                        <span class="upcal-event-detail-starttime">2:00 PM - 3:00 PM</span>
                   </p>
                </div>

                <div class="upcal-event-detail-loc-div">
                    <h3><spring:message code="location"/>:</h3>
                    <p class="upcal-event-detail-loc"></p>
                </div>          
          
                <div class="upcal-event-detail-desc-div">
                    <h3><spring:message code="description"/>:</h3>
                    <p class="upcal-event-detail-desc">Event description</p>
                </div>
          
                <div class="upcal-event-detail-link-div">
                    <h3><spring:message code="link"/>:</h3>
                    <p>
                        <a class="upcal-event-detail-link" href="http://www.event.com" target="_blank">http://www.event.com</a>
                    </p>
                </div>
            </div>

        </div>
    </div><!-- // end:upcal-events -->

    <!-- View Links -->
    <div class="upcal-view-links">
        <a id="${n}viewMoreEventsLink" class="upcal-view-more upcal-hide-on-event" 
                href="<portlet:renderURL windowState="maximized"/>"
                title="<spring:message code="view.more.events"/>">
            <spring:message code="view.more.events"/>
        </a>
        
        <a id="${n}returnToCalendarLink" class="upcal-view-return upcal-hide-on-calendar" href="javascript:;" 
                style="display:none" title="<spring:message code="return.to.calendar"/>">
            <spring:message code="return.to.calendar"/>
        </a>
    </div>
  
</div>
