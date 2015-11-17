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

import com.github.stefanheimberg.example.web.model.Company;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@Stateless
public class CompanyRepository {

    @PersistenceContext
    private EntityManager em;

    public Company findById(@NotNull final Long id) {
        return em.find(Company.class, id);
    }

    public List<Company> findByName(@NotNull @Size(min = 1) final String name) {
        final TypedQuery q = em.createNamedQuery(Company.FIND_BY_NAME, Company.class);
        q.setParameter("name", name);
        return q.getResultList();
    }

    public Long persist(@NotNull final Company company) {
        if (null != company.getId()) {
            throw new IllegalArgumentException("company already persisted! persist schould not be called?");
        }

        em.persist(company);

        // must be flused and refreshed to ensure that the generated ids are available in application
        em.flush();
        em.refresh(company);

        return company.getId();
    }
    
    public void deleteById(@NotNull final Long id) {
        em.remove(em.getReference(Company.class, id));
    }

}
