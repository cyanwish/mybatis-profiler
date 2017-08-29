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

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class SessionManagerTest {

    @Test
    public void test_null() {
        new Thread() {

            @Override
            public void run() {
                assertNull(SessionManager.currentSession());
            }

        }.start();
    }

    @Test
    public void test_newSession() {
        new Thread() {

            @Override
            public void run() {
                Session session = SessionManager.newSession("untitled");
                assertNotNull(session);
            }

        }.start();
    }

    @Test
    public void test_currentSession() {
        new Thread() {

            @Override
            public void run() {
                Session session = SessionManager.newSession("untitled");
                assertNotNull(session);
                session = SessionManager.currentSession();
                assertNotNull(session);
            }

        }.start();
    }

    @Test
    public void test_destroySession() {
        new Thread() {

            @Override
            public void run() {
                SessionManager.newSession("untitled");
                SessionManager.destroySession();
                assertNull(SessionManager.currentSession());
            }

        }.start();
    }

}
