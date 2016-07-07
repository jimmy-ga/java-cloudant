/*
 * Copyright © 2016 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.cloudant.tests.util;

import static org.junit.Assert.assertEquals;

import com.cloudant.http.internal.ok.OkHttpClientHttpUrlConnectionFactory;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public abstract class ParameterizedHttp {
    @Parameterized.Parameters(name = "Using okhttp: {0}")
    public static Object[] okUsable() {
        return new Object[]{true, false};
    }

    /**
     * A parameter governing whether to allow okhttp or not. This lets us exercise both
     * HttpURLConnection types in these tests.
     */
    @Parameterized.Parameter
    public boolean okUsable;

    @Before
    public void changeHttpConnectionFactory() throws Exception {
        if (!okUsable) {
            // New up the mock that will stop okhttp's factory being used
            new OkFactoryBlocker();
        }
        // Verify that we are getting the behaviour we expect.
        assertEquals("The OK usable value was not what was expected for the test parameter.",
                okUsable, OkHttpClientHttpUrlConnectionFactory.isOkUsable());
    }
}
