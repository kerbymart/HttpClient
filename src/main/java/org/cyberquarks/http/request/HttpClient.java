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

import org.cyberquarks.http.exception.AccessDeniedException;
import org.cyberquarks.http.response.HttpResponse;
import java.util.concurrent.Future;
import org.cyberquarks.http.exception.TimeoutException;

public class HttpClient {

  public static HttpResponse get(GetRequest request) throws AccessDeniedException,
      TimeoutException {
    throw new IllegalArgumentException("Not yet implemented");
  }

  public static HttpResponse put(PutRequest request)
      throws AccessDeniedException, TimeoutException {
    throw new IllegalArgumentException("Not yet implemented");
  }

  public static HttpResponse post(PostRequest request)
      throws AccessDeniedException, TimeoutException {
    throw new IllegalArgumentException("Not yet implemented");
  }

  public static HttpResponse delete(DeleteRequest request)
      throws AccessDeniedException, TimeoutException {
    throw new IllegalArgumentException("Not yet implemented");
  }

  public static Future<HttpResponse> getAsync(GetRequest request)
      throws AccessDeniedException, TimeoutException {
    throw new IllegalArgumentException("Not yet implemented");
  }

  public static Future<HttpResponse> putAsync(PutRequest request)
      throws AccessDeniedException, TimeoutException {
    throw new IllegalArgumentException("Not yet implemented");
  }

  public static Future<HttpResponse> postAsync(PostRequest request)
      throws AccessDeniedException, TimeoutException {
    throw new IllegalArgumentException("Not yet implemented");
  }

  public static Future<HttpResponse> deleteAsync(DeleteRequest request)
      throws AccessDeniedException, TimeoutException {
    throw new IllegalArgumentException("Not yet implemented");
  }
}
