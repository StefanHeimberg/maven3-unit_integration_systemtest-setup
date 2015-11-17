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
package com.github.stefanheimberg.example.test;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.strategy.AcceptAllStrategy;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public final class DeploymentBuilder {

    private boolean runtimeDependencies;
    private boolean persistence;

    public DeploymentBuilder() {
        withDefaults();
    }

    public DeploymentBuilder withDefaults() {
        runtimeDependencies = true;
        persistence = true;
        return this;
    }

    public DeploymentBuilder withRuntimeDependencies(final boolean runtimeDependencies) {
        this.runtimeDependencies = runtimeDependencies;
        return this;
    }

    public DeploymentBuilder withPersistence(final boolean persistence) {
        this.persistence = persistence;
        return this;
    }

    public WebArchive build(final Class<?> testClass) {
        final WebArchive war = ShrinkWrap
                .create(WebArchive.class, testClass.getSimpleName() + ".war")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        // INFO: wildfly wirft eine exception wenn die api nicht im deployment drin ist:
        //       Caused by: java.lang.ClassNotFoundException: org.jboss.shrinkwrap.resolver.api.ResolutionStrategy from [Module "deployment.JpaMappingIT.war:main" from Service Module Loader]
        war.addAsLibraries(Maven.resolver()
                .resolve("org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-api:2.2.0")
                .using(AcceptAllStrategy.INSTANCE)
                .asFile());

        if (runtimeDependencies) {
            war.addAsLibraries(Maven.resolver()
                    .loadPomFromFile("pom.xml")
                    .importRuntimeDependencies()
                    .resolve()
                    .using(AcceptAllStrategy.INSTANCE)
                    .asFile());
        }

        if (persistence) {
            war.addAsWebInfResource("example-ds.xml", "example-ds.xml");
            war.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
        }

        return war;
    }

}
