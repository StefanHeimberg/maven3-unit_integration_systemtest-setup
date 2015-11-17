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
package com.github.stefanheimberg.example.database;

import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
// https://github.com/dobermai/Hibernate-Flyway-Integration
// http://blog.essential-bytes.de/flyway-hibernate-und-jpa-integrieren/
public class FlywayIntegrator implements Integrator {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void integrate(final Configuration c, final SessionFactoryImplementor sfi, final SessionFactoryServiceRegistry sfsr) {
        LOG.info("Starting DB migration");

        try {
            final DataSource dataSource = (DataSource) sfi.getProperties().get("javax.persistence.jtaDataSource");

            final Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource);

            final MigrationInfo current = flyway.info().current();
            if (current == null) {
                LOG.info("No existing DB found");
            } else {
                LOG.info("Current DB version is {}", current.getVersion());
            }

            flyway.migrate();

            LOG.info("Completed DB migration to version {}", flyway.info().current().getVersion());
        } catch (final RuntimeException ex) {
            LOG.error(ex);
            throw ex;
        }
    }

    @Override
    public void integrate(final MetadataImplementor mi, final SessionFactoryImplementor sfi, final SessionFactoryServiceRegistry sfsr) {
        // do nothing
    }

    @Override
    public void disintegrate(final SessionFactoryImplementor sfi, final SessionFactoryServiceRegistry sfsr) {
        // do nothing
    }

}
