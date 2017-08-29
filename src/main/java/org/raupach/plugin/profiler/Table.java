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
import static java.lang.Math.max;
import java.util.regex.Pattern;

/**
 * Pretty prints a table
 */
class Table {

    private static final Pattern NUMBER = Pattern.compile("^\\d+(\\.\\d+)?$");

    private final String[] header;
    private final Object[][] data;

    public Table(String[] header, Object[][] data) {
        this.header = header;
        this.data = data;
    }

    public void print(PrintStream out) {
        
        // --------------------------------------------------------------------
        // Width of each cell must hold the maximum data value
        // --------------------------------------------------------------------
        int[] cellValueWidth = new int[header.length];
        
        for (int i = 0; i < header.length; i++) {
            cellValueWidth[i] = header[i].length();
        }
        
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < header.length; j++) {
                cellValueWidth[j] = max(cellValueWidth[j], String.valueOf(data[i][j]).length());
            }
        }
        
        StringBuilder lineBuffer = new StringBuilder();

        // --------------------------------------------------------------------
        // Cache separator line
        // --------------------------------------------------------------------
        for (int i = 0; i < header.length; i++) {
            lineBuffer.append('+');
            for (int j = 0; j < cellValueWidth[i] + 2; j++) {
                lineBuffer.append('-');
            }
        }
        lineBuffer.append('+');
        final String separatorLine = lineBuffer.toString();

        // --------------------------------------------------------------------
        // Print table header
        // --------------------------------------------------------------------
        out.println(separatorLine);
        lineBuffer = new StringBuilder(separatorLine.length());
        for (int i = 0; i < header.length; i++) {
            lineBuffer.append('|');
            lineBuffer.append(' ');
            lineBuffer.append(header[i]);
            for (int j = 0; j < cellValueWidth[i] - header[i].length() + 1; j++) {
                lineBuffer.append(' ');
            }
        }
        lineBuffer.append('|');
        out.println(lineBuffer);
        out.println(separatorLine);
        
        // --------------------------------------------------------------------
        // Print table body
        // --------------------------------------------------------------------
        if (data.length > 0) {
            for (int i = 0; i < data.length; i++) {
                lineBuffer = new StringBuilder(separatorLine.length());
                for (int j = 0; j < header.length; j++) {
                    String value = String.valueOf(data[i][j]);
                    lineBuffer.append('|');
                    lineBuffer.append(' ');
                    if (NUMBER.matcher(value).matches()) {
                        for (int k = 0; k < cellValueWidth[j] - value.length() + 1; k++) {
                            lineBuffer.append(' ');
                        }
                        lineBuffer.append(value);
                    } else {
                        lineBuffer.append(value);
                        for (int k = 0; k < cellValueWidth[j] - value.length() + 1; k++) {
                            lineBuffer.append(' ');
                        }
                    }
                }
                lineBuffer.append('|');
                out.println(lineBuffer);
            }
            out.println(separatorLine);
        }
    }

}
