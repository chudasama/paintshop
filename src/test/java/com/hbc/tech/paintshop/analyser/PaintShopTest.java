/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hbc.tech.paintshop.analyser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.hbc.tech.paintshop.exception.*;
import com.hbc.tech.paintshop.model.reader.CustomInputReader;

@RunWith(value = Parameterized.class)
public class PaintShopTest {

    private String fileName;
    private Object expectedOutput;

    public PaintShopTest(final String fileName, final Object expectedOutput) {

        this.fileName = fileName;
        this.expectedOutput = expectedOutput;
    }

    @Parameters(name = "Scenario {index}: {0} expected ({1})}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { { "5_GGGGG", "G G G G G" }, { "5_GGGGM", "G G G G M" }, { "5_GMGMG", "G M G M G" }, { "2_MM", "M M" },
                { "10_GMGMGGGGGG", "G M G M G G G G G G" }, { "2_ANY_MM", "M M" },
                { "1_No_solution_exists", new RuntimeException(new NoSolutionExistsException("No solution exists")) },
                { "2_No_solution_exists", new RuntimeException(new NoSolutionExistsException("No solution exists")) },
                { "2_InvalidMattePreference", new RuntimeException(new InvalidMatteChoiceException("Customer provided 2 choice for Matte Type")) },
                { "No_customer_preference", new RuntimeException(new MissingCustomerPreference("Customer preference not provided")) },
                { "invalid_color_range", new RuntimeException(new InvalidColorRangeException("Customer choice has invalid color range")) }, });

    }

    @Test
    public void testScenario() {

        final CustomInputReader reader = new CustomInputReader();
        final Path path = Paths.get("src", "test", "resources", "test_scenario", fileName + ".txt");
        try {
            final IPaintShop processInputFile = reader.processInputFile(path.toFile());
            processInputFile.solve();
            final String formatOutput = processInputFile.formatOutput();
            assert (expectedOutput.toString().equalsIgnoreCase(formatOutput));
        } catch (final Throwable e) {
            System.out.println(e);
            if (expectedOutput instanceof RuntimeException) {
                assert (e.getClass().equals(expectedOutput.getClass()));
                assert (e.getMessage().equals(((RuntimeException) expectedOutput).getMessage()));
            } else {
                assert (false);
            }
        }

    }

}
