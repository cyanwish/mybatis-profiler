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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A session is a named entity that collects events from a ProfilingInterceptor.
 */
public class Session {
    
    private final String name;
    private final ArrayDeque<Event> events;
    
    public Session(String name) {
        Objects.requireNonNull(name, "name");
        this.name = name;
        this.events = new ArrayDeque<>();
    }

    public String getName() {
        return name;
    }

    public List<Event> events() {
        return new ArrayList<>(events);
    }
    
    public void addEvent(Event event) {
        events.addLast(event);
    }
    
}
