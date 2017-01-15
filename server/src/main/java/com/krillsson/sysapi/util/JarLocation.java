/*
 * Sys-Api (https://github.com/Krillsson/sys-api)
 *
 * Copyright 2017 Christian Jensen / Krillsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Maintainers:
 * contact[at]christian-jensen[dot]se
 */
package com.krillsson.sysapi.util;

import com.krillsson.sysapi.SystemApiApplication;

import java.io.File;
import java.net.URISyntaxException;


public class JarLocation {
    private static final File JAR = jarLocation();
    private static final boolean IS_JAR = JAR.isFile() && JAR.getName().endsWith(".jar");
    public static final String SEPARATOR = System.getProperty("file.separator");
    public static final String SOURCE_LIB_LOCATION = JAR.getParentFile().getParent() + SEPARATOR + "lib" + SEPARATOR;
    private static final String JAR_LIB_LOCATION = JAR.getParentFile().getParent() + SEPARATOR + "lib" + SEPARATOR;
    public static final String LIB_LOCATION = IS_JAR ? JAR_LIB_LOCATION : SOURCE_LIB_LOCATION;

    private static File jarLocation() {
        try {
            return new File(SystemApiApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            return new File(SystemApiApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        }
    }
}
