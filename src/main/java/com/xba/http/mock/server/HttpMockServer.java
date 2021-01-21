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

package com.xba.http.mock.server;

import com.google.common.annotations.VisibleForTesting;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.util.Map;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class HttpMockServer {

  private ClientAndServer mockServer;

  public HttpMockServer() {
    this.mockServer = null;
  }

  public void startServer() {
    if (mockServer == null || !this.mockServer.isRunning()) {
      this.mockServer = startClientAndServer(1080);
    }
  }

  @VisibleForTesting
  ClientAndServer getMockServer() {
    return mockServer;
  }

  /**
   * Mocks requests the http mock server will respond to.
   * More info on mocking requests at: https://www.mock-server.com/mock_server/getting_started.html
   * @param requestsToMock
   */
  public void mockRequests(Map<HttpRequest, HttpResponse> requestsToMock) {
    if (isRunning()) {
      requestsToMock.forEach((httpRequest, httpResponse) -> this.mockServer.when(httpRequest).respond(httpResponse));
    }
  }

  public void stopServer() {
    if (this.mockServer != null && this.mockServer.isRunning()) {
      this.mockServer.stop();
    }
  }

  public boolean isRunning() {
    if (mockServer != null) {
      return mockServer.isRunning();
    }
    return false;
  }

  public boolean isStopped() {
    return !isRunning();
  }

}
