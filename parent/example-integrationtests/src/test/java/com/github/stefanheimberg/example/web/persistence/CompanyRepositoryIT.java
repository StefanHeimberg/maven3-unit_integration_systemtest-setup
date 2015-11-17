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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@RunWith(Arquillian.class)
public class CompanyRepositoryIT {

    @PersistenceContext
    private EntityManager em;

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

    @Before
    @Transactional
    public void setupTestData() {
        Company company = new Company();
        company.setName("Swisscom");
        em.persist(company);

        company = new Company();
        company.setName("Cablecom");
        em.persist(company);

        em.clear();
        em.getEntityManagerFactory().getCache().evictAll();
    }

    @Test
    public void assert_company_resource_can_be_found() {
        final Company company = companyRepository.findById(2l);
        assertThat(company, is(notNullValue()));
        assertThat(company.getName(), is(equalTo("Cablecom")));
    }

}
