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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.xba.http.mock.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public abstract class JsonObjectReader<T> {

  public JsonObjectReader() {
  }

  public T readValue(String jsonFilePath, Class klass) throws IOException {
    Preconditions.checkNotNull(jsonFilePath);
    Preconditions.checkArgument(
        FileUtils.isFile(jsonFilePath),
        String.format("File %s does not exist or is not a file but a directory", jsonFilePath)
    );
    ObjectMapper objectMapper = new ObjectMapper();
    T objectToReturn = (T) objectMapper.readValue(new File(jsonFilePath), klass);
    return objectToReturn;
  }

}
