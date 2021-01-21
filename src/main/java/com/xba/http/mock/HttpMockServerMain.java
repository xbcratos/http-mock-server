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

import java.util.Scanner;
import java.util.logging.Logger;

public class HttpMockServerMain {

  private static final Logger logger = Logger.getLogger(HttpMockServerMain.class.getName());

  private static HttpMockServer httpMockServer;

  public static void main(String[] args) {
    httpMockServer = new HttpMockServer();

    try {
      runMainLoop();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.exit(0);
  }

  private static void runMainLoop() {
    int option = printOptions();
    while (option != 3) {
      switch (option) {
        case 1:
          // start server
          logger.info("Starting mock server");
          httpMockServer.startServer();
          logger.info("Mocking requests");
          httpMockServer.mockRequests();
          logger.info("mock server started");
          break;
        case 2:
          logger.info("Stopping mock server");
          httpMockServer.stopServer();
          logger.info("mock server stopped");
          break;
        default:
          break;
      }
      option = printOptions();
    }
  }

  private static int printOptions() {
    while (true) {
      System.out.println("Choose an option:");
      System.out.println("1: Start Mock Server if not yet started");
      System.out.println("2: Stop Mock Server if not yet stopped");
      System.out.println("3: End the program");

      int input = getNextInt();
      if (input <= 0 || input > 3) {
        System.out.println("Incorrect option!");
      } else {
        return input;
      }
    }
  }

  private static int getNextInt() {
    Scanner sc = new Scanner(System.in);
    if (sc.hasNextInt()) {
      return sc.nextInt();
    }

    return -1;
  }

}
