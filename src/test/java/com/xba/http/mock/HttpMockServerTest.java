/*
 * Copyright 2021 Xavier Baques.
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

package com.xba.http.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class HttpMockServerTest {

  private HttpMockServer httpMockServer;

  @BeforeEach
  void setUp() {
    httpMockServer = new HttpMockServer();
  }

  @AfterEach
  void tearDown() {
    if (httpMockServer != null && !httpMockServer.isStopped()) {
      httpMockServer.stopServer();
    }
  }

  @Test
  void testStartServer() {
    httpMockServer.startServer();
    Assertions.assertEquals(true, httpMockServer.isRunning());
  }

  @Test
  void testMockRequests() {
    httpMockServer.startServer();
    Assertions.assertEquals(true, httpMockServer.isRunning());
    httpMockServer.mockRequests(getRequestsToMock());
    ClientAndServer mockServer = httpMockServer.getMockServer();
    Expectation[] expectations = mockServer.retrieveActiveExpectations(request().withMethod("GET").withPath("/test2"));
    Assertions.assertEquals(1, expectations.length);
    Assertions.assertEquals(1,
        Arrays.stream(expectations)
              .filter(expectation -> {
                return expectation.getHttpResponse().getStatusCode() == 200 &&
                    "Successful test with java mock server app".equals(expectation.getHttpResponse().getBodyAsString());
              })
              .count()
    );
  }

  @Test
  void testStopServer() {
    httpMockServer.startServer();
    Assertions.assertEquals(true, httpMockServer.isRunning());
    httpMockServer.stopServer();
    Assertions.assertEquals(true, httpMockServer.isStopped());
  }

  private Map<HttpRequest, HttpResponse> getRequestsToMock() {
    Map<HttpRequest, HttpResponse> requestsToMock = new HashMap<>();

    requestsToMock.put(
        request().withMethod("GET").withPath("/test2"),
        response().withStatusCode(200).withBody("Successful test with java mock server app")
    );
    requestsToMock.put(request().withMethod("POST").withPath("/login").withBody("{username: 'foo', password: 'bar'}"),
        response().withStatusCode(302)
                  .withCookie("sessionId", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
                  .withHeader("Location", "https://www.mock-server.com")
    );

    return requestsToMock;
  }
}