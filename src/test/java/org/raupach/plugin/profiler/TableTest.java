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

import org.junit.Test;

public class TableTest {
    
    @Test
    public void test_print() {
        
        String[] header = { "Name", "Min", "Max", "Mean" };
        Object[][] data = {
            { "selectMandanten", 0, 14, 0.232323 },
            { "selectMandantenBySomething", 0, 1, 0.1 }
        };
        
        Table table = new Table(header, data);
        table.print(System.out);
        
    }
    
}
