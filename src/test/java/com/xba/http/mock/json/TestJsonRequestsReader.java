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

package com.xba.http.mock.json;

import com.xba.http.mock.bean.Request;
import com.xba.http.mock.server.HttpMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestJsonRequestsReader {

  private JsonRequestsReader jsonRequestsReader;

  @BeforeEach
  void setUp() {
    jsonRequestsReader = new JsonRequestsReader("src/main/resources/RequestsExample.json");
  }

  @AfterEach
  void tearDown() {
    jsonRequestsReader = null;
  }

  @Test
  void testReadRequestsJson() throws IOException {
    RequestsJson requestsJson = jsonRequestsReader.readRequestsJson();
    Assertions.assertNotNull(requestsJson);
    List<Request> requestList = requestsJson.getRequests();
    Assertions.assertNotNull(requestList);
    Assertions.assertFalse(requestList.isEmpty());
    Assertions.assertEquals(2, requestList.size());
    Request firstRequest = requestList.get(0);
    Assertions.assertEquals("GET", firstRequest.getMethod());
    Assertions.assertEquals("/test", firstRequest.getPath());
    Assertions.assertEquals("test request body", firstRequest.getBody());
  }
}