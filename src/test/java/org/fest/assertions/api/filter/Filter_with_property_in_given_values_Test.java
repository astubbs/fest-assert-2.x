package org.fest.assertions.api.filter;

import static junit.framework.Assert.assertEquals;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.filter.Filters.filter;
import static org.fest.util.Collections.sizeOf;

import static org.junit.Assert.fail;

import org.junit.Test;

import org.fest.assertions.test.Player;
import org.fest.util.IntrospectionError;

public class Filter_with_property_in_given_values_Test extends AbstractTest_filter {

  @Test
  public void should_filter_iterable_elements_with_property_not_equals_to_given_value() {
    Iterable<Player> filteredPlayers = filter(players).with("team").in("OKC", "Chicago Bulls").get();
    assertThat(filteredPlayers).containsOnly(rose, noah, durant);
    // players is not modified
    assertEquals(4, sizeOf(players));

    filteredPlayers = filter(players).with("name.last").in("Rose", "Noah").get();
    assertThat(filteredPlayers).containsOnly(rose, noah);
    // players is not modified
    assertEquals(4, sizeOf(players));
  }

  @Test
  public void should_fail_if_property_to_filter_on_is_null() {
    try {
      filter(players).with(null).in("foo", "bar");
      fail("NullPointerException expected");
    } catch (NullPointerException e) {
      assertEquals("The property name to filter on should not be null", e.getMessage());
    }
  }

  @Test
  public void should_fail_if_elements_to_filter_do_not_have_property_used_by_filter() {
    try {
      filter(players).with("nickname").in("dude", "al");
      fail("IntrospectionError expected");
    } catch (IntrospectionError e) {
      assertEquals("No getter for property 'nickname' in org.fest.assertions.test.Player", e.getMessage());
    }
  }

}