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

import org.mockserver.integration.ClientAndServer;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class HttpMockServer {
  private ClientAndServer mockServer;

  public HttpMockServer() {
    this.mockServer = null;
  }

  public ClientAndServer getMockServer() {
    return mockServer;
  }

  public void startServer() {
    if (mockServer == null || !this.mockServer.isRunning()) {
      this.mockServer = startClientAndServer(1080);
    }
  }

  /**
   * Mocks requests the http mock server will respond to.
   * More info on mocking requests at: https://www.mock-server.com/mock_server/getting_started.html
   */
  public void mockRequests() {
    this.mockServer.when(request().withMethod("GET").withPath("/test2"))
                   .respond(response().withStatusCode(200).withBody("Successful test with java mock server app"));
    this.mockServer.when(request().withMethod("POST").withPath("/login").withBody("{username: 'foo', password: 'bar'}"))
                   .respond(response().withStatusCode(302)
                                      .withCookie("sessionId", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
                                      .withHeader("Location", "https://www.mock-server.com"));
  }

  public void stopServer() {
    if (this.mockServer != null && this.mockServer.isRunning()) {
      this.mockServer.stop();
    }
  }

}
