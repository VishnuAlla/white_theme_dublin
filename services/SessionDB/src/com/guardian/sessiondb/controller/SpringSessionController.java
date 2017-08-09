/*Copyright (c) 2016-2017 imaginea.com All Rights Reserved.
 This software is the confidential and proprietary information of imaginea.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with imaginea.com*/
package com.guardian.sessiondb.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.guardian.sessiondb.SpringSession;
import com.guardian.sessiondb.service.SpringSessionService;


/**
 * Controller object for domain model class SpringSession.
 * @see SpringSession
 */
@RestController("SessionDB.SpringSessionController")
@Api(value = "SpringSessionController", description = "Exposes APIs to work with SpringSession resource.")
@RequestMapping("/SessionDB/SpringSession")
public class SpringSessionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringSessionController.class);

    @Autowired
	@Qualifier("SessionDB.SpringSessionService")
	private SpringSessionService springSessionService;

	@ApiOperation(value = "Creates a new SpringSession instance.")
	@RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public SpringSession createSpringSession(@RequestBody SpringSession springSession) {
		LOGGER.debug("Create SpringSession with information: {}" , springSession);

		springSession = springSessionService.create(springSession);
		LOGGER.debug("Created SpringSession with information: {}" , springSession);

	    return springSession;
	}


    @ApiOperation(value = "Returns the SpringSession instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public SpringSession getSpringSession(@PathVariable("id") String id) throws EntityNotFoundException {
        LOGGER.debug("Getting SpringSession with id: {}" , id);

        SpringSession foundSpringSession = springSessionService.getById(id);
        LOGGER.debug("SpringSession details with id: {}" , foundSpringSession);

        return foundSpringSession;
    }

    @ApiOperation(value = "Updates the SpringSession instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public SpringSession editSpringSession(@PathVariable("id") String id, @RequestBody SpringSession springSession) throws EntityNotFoundException {
        LOGGER.debug("Editing SpringSession with id: {}" , springSession.getSessionId());

        springSession.setSessionId(id);
        springSession = springSessionService.update(springSession);
        LOGGER.debug("SpringSession details with id: {}" , springSession);

        return springSession;
    }

    @ApiOperation(value = "Deletes the SpringSession instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteSpringSession(@PathVariable("id") String id) throws EntityNotFoundException {
        LOGGER.debug("Deleting SpringSession with id: {}" , id);

        SpringSession deletedSpringSession = springSessionService.delete(id);

        return deletedSpringSession != null;
    }

    /**
     * @deprecated Use {@link #findSpringSessions(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of SpringSession instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<SpringSession> searchSpringSessionsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering SpringSessions list");
        return springSessionService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of SpringSession instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<SpringSession> findSpringSessions(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering SpringSessions list");
        return springSessionService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of SpringSession instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<SpringSession> filterSpringSessions(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering SpringSessions list");
        return springSessionService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportSpringSessions(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return springSessionService.export(exportType, query, pageable);
    }

	@ApiOperation(value = "Returns the total count of SpringSession instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countSpringSessions( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting SpringSessions");
		return springSessionService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getSpringSessionAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return springSessionService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service SpringSessionService instance
	 */
	protected void setSpringSessionService(SpringSessionService service) {
		this.springSessionService = service;
	}

}

