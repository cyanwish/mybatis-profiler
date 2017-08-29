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

import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;

public class SessionPrinter {

    private static final String[] header = {
        "MappedStatement",
        "Times",
        "Min. Response",
        "Max. Response"
    };

    public static void print(Session session) {

        List<Event> events = session.events();

        if (events.size() > 0) {
            List<AggregatedEvent> aggregatedEvents = EventAggregator.compute(events);

            Object[][] data = new Object[aggregatedEvents.size()][];

            for (int i = 0; i < aggregatedEvents.size(); i++) {
                AggregatedEvent event = aggregatedEvents.get(i);
                Object[] row = new Object[]{
                    event.getMappedStatement(),
                    event.getTimes(),
                    event.getMin(),
                    event.getMax()
                };
                data[i] = row;
            }

            Table table = new Table(header, data);

            PrintStream out = System.out;

            out.println(session.getName());
            table.print(out);
            out.println();
        }
    }

    static class AggregatedEventComparator implements Comparator<AggregatedEvent> {

        @Override
        public int compare(AggregatedEvent event1, AggregatedEvent event2) {
            return event1.getMappedStatement().compareTo(event2.getMappedStatement());
        }

    }

}
