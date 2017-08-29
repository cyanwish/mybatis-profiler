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

import java.io.Serializable;
import java.util.Objects;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;

public final class Event implements Serializable {
    
    public enum EventType {
        Query,
        Update
    }

    private final long timestamp;
    private final EventType eventType;
    private final String mappedStatement;
    private final long responseTime;
    
    Event(long timestamp, EventType eventType, String mappedStatement, long responseTime) {
        this.timestamp = timestamp;
        this.eventType = eventType;
        this.mappedStatement = mappedStatement;
        this.responseTime = responseTime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getMappedStatement() {
        return mappedStatement;
    }

    public long getResponseTime() {
        return responseTime;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Event[")
                .append("timestamp=").append(timestamp)
                .append(", mappedStatement=").append(mappedStatement)
                .append(", eventType=").append(eventType)
                .append(", responseTime=").append(responseTime)
                .append("]")
                .toString();
    }

    public static Event from(Invocation invocation, long responseTime) {
        Objects.requireNonNull(invocation, "invocation");
        String mappedStatement = ((MappedStatement) invocation.getArgs()[0]).getId();
        EventType eventType = null;
        String methodName = invocation.getMethod().getName();
        switch (methodName) {
            case "query":
                eventType = EventType.Query;
                break;
            case "update":
                eventType = EventType.Update;
                break;
            default:
                throw new UnsupportedOperationException("Missing EventType for methodName = '" + methodName + "'");
        }
        return new Event(System.currentTimeMillis(), eventType, mappedStatement, responseTime);
    }

}
