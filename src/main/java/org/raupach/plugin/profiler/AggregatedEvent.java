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

public class AggregatedEvent implements Serializable  {
    
    private final String mappedStatement;
    private final int times;
    private final long min;
    private final long max;
    private final long sum;

    public AggregatedEvent(String mappedStatement, int times, long min, long max, long sum) {
        this.mappedStatement = mappedStatement;
        this.times = times;
        this.min = min;
        this.max = max;
        this.sum = sum;
    }

    public String getMappedStatement() {
        return mappedStatement;
    }

    public int getTimes() {
        return times;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public long getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(mappedStatement)
                .append(" ")
                .append(times)
                .append(" ")
                .append(min)
                .append(" ")
                .append(max)
                .append(" ")
                .append(sum)
                .toString();
    }
    
    
}
