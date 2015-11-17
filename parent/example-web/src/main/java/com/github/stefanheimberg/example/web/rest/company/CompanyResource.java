/*
 * Copyright 2015 Stefan Heimberg <kontakt@stefanheimberg.ch>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.stefanheimberg.example.web.rest.company;

import com.github.stefanheimberg.example.web.model.Company;
import com.github.stefanheimberg.example.web.persistence.CompanyRepository;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@Path("companies")
@RequestScoped
public class CompanyResource {
    
    @Inject
    private CompanyRepository companyRepository;
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Company findById(@PathParam("id") final Long id) {
        final Company company = companyRepository.findById(id);
        if(null == company) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return company;
    }
    
}
