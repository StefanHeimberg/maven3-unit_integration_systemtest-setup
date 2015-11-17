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
package com.github.stefanheimberg.example.web.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@Entity
@Table(name = "TBL_COMPANY")
@NamedQueries({
    @NamedQuery(name = Company.FIND_BY_NAME, query = "FROM Company c WHERE c.name = :name")
})
public class Company implements Serializable {
    
    public static final String FIND_BY_NAME = "Company.findByName";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_id")
    @SequenceGenerator(name = "seq_gen_id", sequenceName = "SEQ_COMPANY_ID", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NAME")
    @Size(min = 1, max = 255)
    @NotNull
    private String name;
    
    @Version
    @Column(name = "VERSION")
    private Integer version;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName())
                .append("[id=").append(id).append(",")
                .append("name=").append(name).append("]")
                .toString();
    }

}
