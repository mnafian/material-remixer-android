/*
 * Copyright 2016 Google Inc.
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

package com.google.android.libraries.remixer;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class ItemListVariableTest {

  @Mock
  Callback<String> mockCallback;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expected = IllegalArgumentException.class)
  public void initFailsOnDefaultValueNotInList() {
    new ItemListVariable<>(
        "name", "key", "None", Arrays.asList("Something else"), this, null, 0).init();
  }

  @Test(expected = IllegalArgumentException.class)
  public void setValueRejectsUnknownString() {
    ItemListVariable<String> variable =
        new ItemListVariable<>("name", "key", "A", Arrays.asList("A", "B"), this, null, 0);
    variable.init();
    variable.setValue("C");
  }

  @Test
  public void initCallsCallback() {
    ItemListVariable<String> variable =
        new ItemListVariable<>("name", "key", "A", Arrays.asList("A", "B"), this, mockCallback, 0);
    variable.init();
    Mockito.verify(mockCallback, Mockito.times(1)).onValueSet(variable);
  }

  @Test
  public void setValueCallsCallback() {
    ItemListVariable<String> variable =
        new ItemListVariable<>("name", "key", "A", Arrays.asList("A", "B"), this, mockCallback, 0);
    variable.init();
    variable.setValue("B");
    Mockito.verify(mockCallback, Mockito.times(2)).onValueSet(variable);
  }

  @Test
  public void doesNotCrashOnNullCallback() {
    ItemListVariable<String> variable =
        new ItemListVariable<>("name", "key", "A", Arrays.asList("A", "B"), this, null, 0);
    variable.init();
    variable.setValue("B");
  }
}
