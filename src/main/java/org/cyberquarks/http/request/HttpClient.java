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

import org.cyberquarks.http.HttpStringBody;
import org.cyberquarks.http.exception.HttpException;
import org.cyberquarks.http.response.HttpResponse;
import org.cyberquarks.http.HttpBody;
import org.cyberquarks.http.response.StringHttpResponse;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.net.URL;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.io.IOException;

import org.teavm.interop.AsyncCallback;
import org.teavm.jso.ajax.XMLHttpRequest;
import org.teavm.jso.ajax.ReadyStateChangeHandler;
import org.teavm.flavour.widgets.BackgroundWorker;
import org.teavm.interop.Async;

public class HttpClient {

  private static final int DEFAULT_TIMEOUT = 30000; // 30 seconds

  @Async
  public static native HttpResponse get(GetRequest request) throws HttpException, IOException;

  private static void get(GetRequest request, AsyncCallback<HttpResponse> callback) {
    executeAsync(request, "GET", null, callback);
  }

  @Async
  public static native HttpResponse put(PutRequest request) throws HttpException, IOException;

  private static void put(PutRequest request, AsyncCallback<HttpResponse> callback) {
    executeAsync(request, "PUT", request.getBody(), callback);
  }

  @Async
  public static native HttpResponse post(PostRequest request) throws HttpException, IOException;

  private static void post(PostRequest request, AsyncCallback<HttpResponse> callback) {
    executeAsync(request, "POST", request.getBody(), callback);
  }

  @Async
  public static native HttpResponse delete(DeleteRequest request) throws HttpException, IOException;

  private static void delete(DeleteRequest request, AsyncCallback<HttpResponse> callback) {
    executeAsync(request, "DELETE", null, callback);
  }

  private static void executeAsync(HttpRequest request, String method, HttpBody body, AsyncCallback<HttpResponse> callback) {
    BackgroundWorker background = new BackgroundWorker();

    background.run(new Runnable() {
      @Override
      public void run() {
        try {
          XMLHttpRequest xhr = XMLHttpRequest.create();

          // Build URL with query parameters
          String url = buildUrlWithQueryParams(request.getUrl(), request.getQueryParameters());

          // Setup request
          xhr.open(method, url, true);

          // Set headers
          for (Header header : request.getHeaders()) {
            xhr.setRequestHeader(header.getName(), header.getValue());
          }

          // Setup timeout using a timer
          final boolean[] completed = {false};

          // Setup response handler
          xhr.setOnReadyStateChange(new ReadyStateChangeHandler() {
            @Override
            public void stateChanged() {
              if (xhr.getReadyState() == XMLHttpRequest.DONE) {
                if (!completed[0]) {
                  completed[0] = true;
                  try {
                    int status = xhr.getStatus();

                    if (status == 0) {
                      callback.error(new IOException("Request failed - network error or timeout"));
                      return;
                    }

                    Set<Header> responseHeaders = parseResponseHeaders(xhr.getAllResponseHeaders());
                    String responseBody = xhr.getResponseText();
                    HttpResponse response = createStringHttpResponse(status, responseHeaders, responseBody);
                    callback.complete(response);

                  } catch (Exception e) {
                    callback.error(e);
                  }
                }
              }
            }
          });

          BackgroundWorker timeoutWorker = new BackgroundWorker();
          timeoutWorker.run(new Runnable() {
            @Override
            public void run() {
              try {
                Thread.sleep(DEFAULT_TIMEOUT);
                if (!completed[0]) {
                  completed[0] = true;
                  xhr.abort();
                  callback.error(new IOException("Request timed out after " + DEFAULT_TIMEOUT + "ms"));
                }
              } catch (InterruptedException e) {
                // Ignore interruption
              }
            }
          });

          // Send request
          if (body != null) {
            String bodyContent = getBodyContent(body);
            xhr.send(bodyContent);
          } else {
            xhr.send();
          }

        } catch (Exception e) {
          callback.error(e);
        }
      }
    });
  }

  private static String buildUrlWithQueryParams(URL baseUrl, Map<String, String> queryParams) {
    if (queryParams == null || queryParams.isEmpty()) {
      return baseUrl.toString();
    }

    StringBuilder urlBuilder = new StringBuilder(baseUrl.toString());

    if (baseUrl.getQuery() == null || baseUrl.getQuery().isEmpty()) {
      urlBuilder.append('?');
    } else {
      urlBuilder.append('&');
    }

    boolean first = true;
    for (Map.Entry<String, String> entry : queryParams.entrySet()) {
      if (!first) {
        urlBuilder.append('&');
      }
      first = false;

      try {
        urlBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
        urlBuilder.append('=');
        urlBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException("UTF-8 encoding not supported", e);
      }
    }

    return urlBuilder.toString();
  }

  private static Set<Header> parseResponseHeaders(String headerString) {
    Set<Header> headers = new HashSet<>();

    if (headerString == null || headerString.trim().isEmpty()) {
      return headers;
    }

    String[] headerLines = headerString.split("\r?\n");
    for (String line : headerLines) {
      if (line.trim().isEmpty()) {
        continue;
      }

      int colonIndex = line.indexOf(':');
      if (colonIndex > 0) {
        String name = line.substring(0, colonIndex).trim();
        String value = line.substring(colonIndex + 1).trim();
        headers.add(new Header(name, value));
      }
    }

    return headers;
  }

  private static HttpResponse createStringHttpResponse(int status, Set<Header> headers, String body) {
    return new StringHttpResponse(status, headers, body);
  }

  private static String getBodyContent(HttpBody body) {
    if (body != null && body instanceof HttpStringBody) {
      return ((HttpStringBody) body).getValue();
    }
    return null;
  }
}