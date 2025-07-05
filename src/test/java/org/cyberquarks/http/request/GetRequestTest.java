/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.cyberquarks.http.request;

import org.cyberquarks.http.response.StringHttpResponse;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class GetRequestTest {

    @Test
    public void GetRequest_with_url_and_headers_should_be_created() throws MalformedURLException {
        String url = "http://example.com";
        Set<Header> headers = new HashSet<>();
        headers.add(new Header("Content-Type", "application/json"));
        GetRequest request = new GetRequest(url, headers);
        assertEquals(url, request.getUrl().toString());
        assertEquals(headers, request.getHeaders());
        assertEquals(new HashMap<>(), request.getQueryParameters());
    }

    @Test
    public void GetRequest_with_url_headers_and_query_parameters_should_be_created() throws MalformedURLException {
        String url = "http://example.com";
        Set<Header> headers = new HashSet<>();
        headers.add(new Header("Content-Type", "application/json"));
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("param1", "value1");
        GetRequest<StringHttpResponse> request = new GetRequest(url, headers, queryParameters, StringHttpResponse.class);
        assertEquals(url, request.getUrl().toString());
        assertEquals(headers, request.getHeaders());
        assertEquals(queryParameters, request.getQueryParameters());
    }
}
