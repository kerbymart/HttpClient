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
package org.cyberquarks.http;

import org.cyberquarks.http.request.HttpClient;
import org.cyberquarks.http.response.HttpResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.cyberquarks.http.request.DeleteRequest;
import org.cyberquarks.http.request.GetRequest;
import org.cyberquarks.http.request.Header;
import org.cyberquarks.http.request.PostRequest;
import org.cyberquarks.http.request.PutRequest;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.teavm.junit.SkipJVM;
import org.teavm.junit.TeaVMTestRunner;

import static junit.framework.TestCase.assertNotNull;

/**
 * Unit tests of {@link HttpClientTest}
 */
@RunWith(TeaVMTestRunner.class)
@SkipJVM
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HttpClientTest {

  static final Logger logger = Logger.getLogger(HttpClientTest.class.getName());
  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void test() throws Exception {
    System.out.println("Test done");
  }

  @Test
  public void Post_request_with_string_body_should_return_non_null_response() throws Exception {
    Set<Header> headers = new HashSet<>();
    headers.add(new Header("Content-Type", "application/json"));
    headers.add(new Header("Accept", "application/json"));

    PostRequest request =
        new PostRequest("http://httpbin.org/post", headers, new HttpStringBody("hello world"));
    HttpResponse<InputStream> response = HttpClient.post(request);
    InputStream body = response.getBody();
    assertNotNull(body);
  }

  @Test
  public void Post_request_with_input_stream_body_should_return_non_null_response() throws Exception {
    Set<Header> headers = new HashSet<>();
    headers.add(new Header("Content-Type", "application/json"));
    headers.add(new Header("Accept", "application/json"));

    InputStream inputStream = null;
    PostRequest request =
        new PostRequest("http://httpbin.org/post", headers, new HttpInputStreamBody(inputStream));
    HttpResponse<String> response = HttpClient.post(request);
    String body = response.getBody();
    assertNotNull(body);
  }

  @Test
  public void Put_request_with_string_body_should_return_non_null_response() throws Exception {
    Set<Header> headers = new HashSet<>();
    headers.add(new Header("Content-Type", "application/json"));
    headers.add(new Header("Accept", "application/json"));

    PutRequest request = new PutRequest("http://httpbin.org/put", headers, new HashMap<>(), new HttpStringBody("hello world"));
    HttpResponse<InputStream> response = HttpClient.put(request);
    InputStream body = response.getBody();
    assertNotNull(body);
  }

  @Test
  public void Get_request_should_return_non_null_response() throws Exception {
    Set<Header> headers = new HashSet<>();
    headers.add(new Header("Content-Type", "application/json"));
    headers.add(new Header("Accept", "application/json"));

    GetRequest request = new GetRequest("http://httpbin.org/get", headers, new HashMap<>());
    HttpResponse<InputStream> response = HttpClient.get(request);
    InputStream body = response.getBody();
    assertNotNull(body);
  }

  @Test
  public void Delete_request_should_return_non_null_response() throws Exception {
    Set<Header> headers = new HashSet<>();
    headers.add(new Header("Content-Type", "application/json"));
    headers.add(new Header("Accept", "application/json"));

    DeleteRequest request = new DeleteRequest("http://httpbin.org/delete", headers, new HashMap<>());
    HttpResponse<InputStream> response = HttpClient.delete(request);
    InputStream body = response.getBody();
    assertNotNull(body);
  }
}
