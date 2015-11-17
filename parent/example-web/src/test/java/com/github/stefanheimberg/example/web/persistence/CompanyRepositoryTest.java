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
import javax.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.same;
import org.mockito.Mock;
import static org.mockito.Mockito.inOrder;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyRepositoryTest {
    
    @InjectMocks
    private CompanyRepository companyRepository;
    
    @Mock
    private EntityManager em;
    
    @Test
    public void assert_that_flush_and_refresh_are_called() {
        final Company company = new Company();
        company.setName("Test");
        
        companyRepository.persist(company);
        
        final InOrder inOrder = inOrder(em);
        
        inOrder.verify(em).persist(same(company));
        inOrder.verify(em).flush();
        inOrder.verify(em).refresh(same(company));
    }
    
}
