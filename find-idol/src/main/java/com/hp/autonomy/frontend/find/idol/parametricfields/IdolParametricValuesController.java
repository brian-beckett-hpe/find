/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.frontend.find.idol.parametricfields;

import com.autonomy.aci.client.services.AciErrorException;
import com.hp.autonomy.frontend.find.core.parametricfields.ParametricValues;
import com.hp.autonomy.frontend.find.core.parametricfields.ParametricValuesController;
import com.hp.autonomy.frontend.find.core.search.QueryRestrictionsBuilder;
import com.hp.autonomy.searchcomponents.core.parametricvalues.ParametricValuesService;
import com.hp.autonomy.searchcomponents.core.search.QueryRestrictions;
import com.hp.autonomy.searchcomponents.idol.parametricvalues.IdolParametricRequest;
import com.hp.autonomy.types.requests.idol.actions.tags.QueryTagInfo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(ParametricValuesController.PARAMETRIC_VALUES_PATH)
public class IdolParametricValuesController extends ParametricValuesController<IdolParametricRequest, String, AciErrorException> {
    @Autowired
    public IdolParametricValuesController(final ParametricValuesService<IdolParametricRequest, String, AciErrorException> parametricValuesService, final QueryRestrictionsBuilder<String> queryRestrictionsBuilder) {
        super(parametricValuesService, queryRestrictionsBuilder);
    }

    @Override
    public ParametricValues getParametricValues(@RequestParam(QUERY_TEXT_PARAM) String queryText,
                                                @RequestParam(value = FIELD_TEXT_PARAM, defaultValue = "") String fieldText,
                                                @RequestParam(DATABASES_PARAM) List<String> databases,
                                                @RequestParam(value = MIN_DATE_PARAM, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime minDate,
                                                @RequestParam(value = MAX_DATE_PARAM, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime maxDate) throws AciErrorException {
        final QueryRestrictions<String> queryRestrictions = queryRestrictionsBuilder.build(queryText, fieldText, databases, minDate, maxDate);
        final IdolParametricRequest parametricRequest = buildParametricRequest(Collections.<String>emptyList(), queryRestrictions);
        final Set<QueryTagInfo> allParametricValues = parametricValuesService.getAllParametricValues(parametricRequest);
        return new ParametricValues(allParametricValues, null);
    }

    @Override
    protected IdolParametricRequest buildParametricRequest(final List<String> fieldNames, final QueryRestrictions<String> queryRestrictions) {
        return new IdolParametricRequest.Builder()
                .setFieldNames(fieldNames)
                .setQueryRestrictions(queryRestrictions)
                .build();
    }
}
