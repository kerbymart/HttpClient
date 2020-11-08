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
  public void testPostRequest() throws Exception {
    PostRequest request =
        new PostRequest("http://httpbin.org/post", new HttpStringBody("hello world"));
    HttpResponse<InputStream> response = HttpClient.post(request);
    InputStream body = response.getBody();
  }

  @Test
  public void testPostInputStreamRequest() throws Exception {
    InputStream inputStream = null;
    PostRequest request =
        new PostRequest("http://httpbin.org/post", new HttpInputStreamBody(inputStream));
    HttpResponse<String> response = HttpClient.post(request);
    String body = response.getBody();
  }


  @Test
  public void testPutRequest() throws Exception {
    PutRequest request = new PutRequest("", new HashSet<Header>(), new HashMap<>(), null);
  }

  @Test
  public void testGetRequest() throws Exception {
    GetRequest request = new GetRequest("", new HashSet<Header>(), new HashMap<>());
  }

  @Test
  public void testDeleteRequest() throws Exception {
    DeleteRequest request = new DeleteRequest("", new HashSet<Header>(), new HashMap<>());
  }
}
