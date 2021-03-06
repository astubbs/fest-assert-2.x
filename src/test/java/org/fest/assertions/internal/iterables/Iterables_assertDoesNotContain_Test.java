/*
 * Created on Oct 12, 2010
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * 
 * Copyright @2010-2011 the original author or authors.
 */
package org.fest.assertions.internal.iterables;

import static java.util.Collections.emptyList;

import static org.fest.assertions.error.ShouldNotContain.shouldNotContain;
import static org.fest.assertions.test.ErrorMessages.*;
import static org.fest.assertions.test.FailureMessages.actualIsNull;
import static org.fest.assertions.test.ObjectArrayFactory.emptyArray;
import static org.fest.assertions.test.TestData.someInfo;
import static org.fest.assertions.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.fest.util.Arrays.array;
import static org.fest.util.Collections.*;

import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.internal.Iterables;
import org.fest.assertions.internal.IterablesBaseTest;

/**
 * Tests for <code>{@link Iterables#assertDoesNotContain(AssertionInfo, Collection, Object[])}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Iterables_assertDoesNotContain_Test extends IterablesBaseTest {

  private static List<String> actual = list("Luke", "Yoda", "Leia");;

  @Test
  public void should_pass_if_actual_does_not_contain_given_values() {
    iterables.assertDoesNotContain(someInfo(), actual, array("Han"));
  }

  @Test
  public void should_pass_if_actual_does_not_contain_given_values_even_if_duplicated() {
    iterables.assertDoesNotContain(someInfo(), actual, array("Han", "Han", "Anakin"));
  }

  @Test
  public void should_throw_error_if_array_of_values_to_look_for_is_empty() {
    thrown.expectIllegalArgumentException(valuesToLookForIsEmpty());
    iterables.assertDoesNotContain(someInfo(), actual, emptyArray());
  }

  @Test
  public void should_throw_error_if_array_of_values_to_look_for_is_null() {
    thrown.expectNullPointerException(valuesToLookForIsNull());
    iterables.assertDoesNotContain(someInfo(), emptyList(), null);
  }

  @Test
  public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    iterables.assertDoesNotContain(someInfo(), null, array("Yoda"));
  }

  @Test
  public void should_fail_if_actual_contains_given_values() {
    AssertionInfo info = someInfo();
    Object[] expected = { "Luke", "Yoda", "Han" };
    try {
      iterables.assertDoesNotContain(info, actual, expected);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldNotContain(actual, expected, set("Luke", "Yoda")));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  // ------------------------------------------------------------------------------------------------------------------
  // tests using a custom comparison strategy
  // ------------------------------------------------------------------------------------------------------------------

  @Test
  public void should_pass_if_actual_does_not_contain_given_values_according_to_custom_comparison_strategy() {
    iterablesWithCaseInsensitiveComparisonStrategy.assertDoesNotContain(someInfo(), actual, array("Han"));
  }

  @Test
  public void should_pass_if_actual_does_not_contain_given_values_even_if_duplicated_according_to_custom_comparison_strategy() {
    iterablesWithCaseInsensitiveComparisonStrategy.assertDoesNotContain(someInfo(), actual, array("Han", "Han", "Anakin"));
  }

  @Test
  public void should_fail_if_actual_contains_given_values_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    Object[] expected = { "LuKe", "YODA", "Han" };
    try {
      iterablesWithCaseInsensitiveComparisonStrategy.assertDoesNotContain(info, actual, expected);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldNotContain(actual, expected, set("LuKe", "YODA"), comparisonStrategy));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

}
