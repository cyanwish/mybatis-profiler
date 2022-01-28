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
import java.util.Comparator;
import java.util.List;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.Objects;

/**
 * EventAggregator computes AggregatedEvents from profiling Events.
 */
public class EventAggregator {
    
    private static final EventComparator COMPARATOR = new EventComparator();
    
    public static List<AggregatedEvent> compute(List<Event> events) {
        Objects.requireNonNull(events, "events");
        
        if (events.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        
        List<AggregatedEvent> aggregatedEvents = new ArrayList<>();
        
        Collections.sort(events, COMPARATOR);
        
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            
            final String mappedStatement = event.getMappedStatement();
            int times = 1;
            long min = event.getResponseTime();
            long max = event.getResponseTime();
            long sum = event.getResponseTime();
            
            int j;
            
            for (j = i + 1; j < events.size(); j++) {
                event = events.get(j);
                
                if (event.getMappedStatement().equals(mappedStatement)) {
                    times++;
                    min = min(min, event.getResponseTime());
                    max = max(max, event.getResponseTime());
                    sum += event.getResponseTime();
                } else {
                    break;
                }
            }
            
            i = j - 1;
            
            AggregatedEvent aggregatedEvent = 
                    new AggregatedEvent(mappedStatement, times, min, max, sum);
            aggregatedEvents.add(aggregatedEvent);
        }   
        
        return aggregatedEvents;
    }
    
    static class EventComparator implements Comparator<Event> {

        @Override
        public int compare(Event event1, Event event2) {
            int c = event1.getMappedStatement().compareTo(event2.getMappedStatement());
            return c == 0 ? Long.compare(event1.getResponseTime(), event2.getResponseTime()) : c;
        }
        
    }

}
