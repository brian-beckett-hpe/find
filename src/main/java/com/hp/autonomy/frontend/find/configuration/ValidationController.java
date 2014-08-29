package com.hp.autonomy.frontend.find.configuration;

import com.autonomy.frontend.configuration.ValidationResults;
import com.autonomy.frontend.configuration.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * $Id:$
 *
 * Copyright (c) 2014, Autonomy Systems Ltd.
 *
 * Last modified by $Author:$ on $Date:$
 */
@Controller
@RequestMapping({"/api/useradmin/config", "/api/config/config"})
public class ValidationController {

    @Autowired
    private ValidationService<FindConfig> validationService;

    @RequestMapping(value = "/config-validation", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public ValidationResults validConfig(@RequestBody final FindConfig config){
        return validationService.validateConfig(config);
    }

}
