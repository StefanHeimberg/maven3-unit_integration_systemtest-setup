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
package com.github.stefanheimberg.example.web.persistence;

import com.github.stefanheimberg.example.test.DeploymentBuilder;
import com.github.stefanheimberg.example.web.model.Company;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@RunWith(Arquillian.class)
public class CompanyRepositoryIT {

    @EJB
    private CompanyRepository companyRepository;

    @Deployment
    public static WebArchive createDeployment() {
        final WebArchive war = new DeploymentBuilder()
                .withPersistence(true)
                .withRuntimeDependencies(true)
                .build(CompanyRepositoryIT.class);
        
        war.addClass(Company.class);
        war.addClass(CompanyRepository.class);

        System.out.println(war.toString(true));
        return war;
    }

    @Test
    public void assert_company_resource_can_be_found() {
        assertNotNull(companyRepository);
    }

}
