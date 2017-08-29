/*
 * Copyright 2017 Björn Raupach <raupach@me.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.raupach.plugin.profiler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.assertThat;

public class EventAggregatorTest {

    @Test(expected = NullPointerException.class)
    public void test_null() {
        List<AggregatedEvent> aggregatedEvents = EventAggregator.compute(null);
    }

    @Test
    public void test_emtpy() {
        List<AggregatedEvent> aggregatedEvents = EventAggregator.compute(Collections.EMPTY_LIST);
    }
    
    @Test
    public void test_one() {
        Event event = new Event(System.currentTimeMillis(), Event.EventType.Query, "select", 25);
        
        List<Event> events = new ArrayList<>();
        events.add(event);
        
        List<AggregatedEvent> aggregatedEvents = EventAggregator.compute(events);
        
        assertThat(aggregatedEvents.size(), is(1));
        AggregatedEvent aggregatedEvent = aggregatedEvents.get(0);
        assertThat(aggregatedEvent.getTimes(), is(1));
        assertThat(aggregatedEvent.getMin(), is(25l));
        assertThat(aggregatedEvent.getMax(), is(25l));
    }
    
    @Test
    public void test_two() {
        Event event0 = new Event(System.currentTimeMillis(), Event.EventType.Query, "select",  5);
        Event event1 = new Event(System.currentTimeMillis(), Event.EventType.Query, "select", 50);
        
        List<Event> events = new ArrayList<>();
        events.add(event0);
        events.add(event1);
        
        List<AggregatedEvent> aggregatedEvents = EventAggregator.compute(events);
        
        assertThat(aggregatedEvents.size(), is(1));
        
        AggregatedEvent aggregatedEvent = aggregatedEvents.get(0);
        assertThat(aggregatedEvent.getMappedStatement(), is("select"));
        assertThat(aggregatedEvent.getTimes(), is(2));
        assertThat(aggregatedEvent.getMin(), is(5l));
        assertThat(aggregatedEvent.getMax(), is(50l));
    }
    
    @Test
    public void test_three() {
        Event event0 = new Event(System.currentTimeMillis(), Event.EventType.Query, "select",  5);
        Event event1 = new Event(System.currentTimeMillis(), Event.EventType.Query, "selectAll", 100);
        Event event2 = new Event(System.currentTimeMillis(), Event.EventType.Query, "select", 50);
        
        List<Event> events = new ArrayList<>();
        events.add(event0);
        events.add(event1);
        events.add(event2);
        
        List<AggregatedEvent> aggregatedEvents = EventAggregator.compute(events);
        
        assertThat(aggregatedEvents.size(), is(2));
        
        AggregatedEvent aggregatedEvent = aggregatedEvents.get(0);
        assertThat(aggregatedEvent.getMappedStatement(), is("select"));
        assertThat(aggregatedEvent.getTimes(), is(2));
        assertThat(aggregatedEvent.getMin(), is(5l));
        assertThat(aggregatedEvent.getMax(), is(50l));
        
        aggregatedEvent = aggregatedEvents.get(1);
        assertThat(aggregatedEvent.getMappedStatement(), is("selectAll"));
        assertThat(aggregatedEvent.getTimes(), is(1));
        assertThat(aggregatedEvent.getMin(), is(100l));
        assertThat(aggregatedEvent.getMax(), is(100l));
    }
    
    @Test
    public void test_five() {
        Event event0 = new Event(System.currentTimeMillis(), Event.EventType.Query, "select",  5);
        Event event1 = new Event(System.currentTimeMillis(), Event.EventType.Query, "selectAll", 100);
        Event event2 = new Event(System.currentTimeMillis(), Event.EventType.Query, "select", 50);
        Event event3 = new Event(System.currentTimeMillis(), Event.EventType.Query, "selectOne", 2);
        Event event4 = new Event(System.currentTimeMillis(), Event.EventType.Query, "select", 50);
        
        List<Event> events = new ArrayList<>();
        events.add(event0);
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        
        List<AggregatedEvent> aggregatedEvents = EventAggregator.compute(events);
        
        assertThat(aggregatedEvents.size(), is(3));
        
        AggregatedEvent aggregatedEvent = aggregatedEvents.get(0);
        assertThat(aggregatedEvent.getMappedStatement(), is("select"));
        assertThat(aggregatedEvent.getTimes(), is(3));
        assertThat(aggregatedEvent.getMin(), is(5l));
        assertThat(aggregatedEvent.getMax(), is(50l));
        
        aggregatedEvent = aggregatedEvents.get(1);
        assertThat(aggregatedEvent.getMappedStatement(), is("selectAll"));
        assertThat(aggregatedEvent.getTimes(), is(1));
        assertThat(aggregatedEvent.getMin(), is(100l));
        assertThat(aggregatedEvent.getMax(), is(100l));
        
        aggregatedEvent = aggregatedEvents.get(2);
        assertThat(aggregatedEvent.getMappedStatement(), is("selectOne"));
        assertThat(aggregatedEvent.getTimes(), is(1));
        assertThat(aggregatedEvent.getMin(), is(2l));
        assertThat(aggregatedEvent.getMax(), is(2l));
    }
    
    @Test
    public void test_three_different() {
        Event event0 = new Event(System.currentTimeMillis(), Event.EventType.Query, "select",  5);
        Event event1 = new Event(System.currentTimeMillis(), Event.EventType.Query, "selectAll", 100);
        Event event2 = new Event(System.currentTimeMillis(), Event.EventType.Query, "selectOne", 75);
        
        List<Event> events = new ArrayList<>();
        events.add(event0);
        events.add(event1);
        events.add(event2);
        
        List<AggregatedEvent> aggregatedEvents = EventAggregator.compute(events);
        
        assertThat(aggregatedEvents.size(), is(3));
        
        AggregatedEvent aggregatedEvent = aggregatedEvents.get(0);
        assertThat(aggregatedEvent.getMappedStatement(), is("select"));
        assertThat(aggregatedEvent.getTimes(), is(1));
        assertThat(aggregatedEvent.getMin(), is(5l));
        assertThat(aggregatedEvent.getMax(), is(5l));
        
        aggregatedEvent = aggregatedEvents.get(1);
        assertThat(aggregatedEvent.getMappedStatement(), is("selectAll"));
        assertThat(aggregatedEvent.getTimes(), is(1));
        assertThat(aggregatedEvent.getMin(), is(100l));
        assertThat(aggregatedEvent.getMax(), is(100l));
        
        aggregatedEvent = aggregatedEvents.get(2);
        assertThat(aggregatedEvent.getMappedStatement(), is("selectOne"));
        assertThat(aggregatedEvent.getTimes(), is(1));
        assertThat(aggregatedEvent.getMin(), is(75l));
        assertThat(aggregatedEvent.getMax(), is(75l));
    }

}
