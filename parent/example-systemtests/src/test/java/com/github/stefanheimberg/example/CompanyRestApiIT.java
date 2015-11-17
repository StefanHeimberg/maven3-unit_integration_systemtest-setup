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
package com.github.stefanheimberg.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public class CompanyRestApiIT {

    @Test
    public void assert_company_resource_can_be_found() throws IOException {
        // do some tests from outside to the application. e.g. call the Restful API or do some selenium tests..
        // JAX-WS Client / JAX-RS Client etc...

        final URL url = new URL("http://localhost:8080/example/rest/companies/2");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Accept-Charset", "UTF-8");

        final int responseCode = con.getResponseCode();
        final StringBuffer response = new StringBuffer();
        
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        assertThat(responseCode, is(200));
        assertThat(response.toString(), is(equalTo("{\"id\":2,\"name\":\"Apple\"}")));
    }

}
