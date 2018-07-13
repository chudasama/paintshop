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
package com.hbc.tech.paintshop.model.reader;

import java.io.*;
import java.nio.file.Files;

import org.apache.log4j.Logger;

import com.hbc.tech.paintshop.analyser.IPaintShop;
import com.hbc.tech.paintshop.analyser.PaintShop2;
import com.hbc.tech.paintshop.exception.InvalidInputFileException;
import com.hbc.tech.paintshop.model.ColorType;
import com.hbc.tech.paintshop.model.Customer;

/**
 * The <code>CustomInputReader</code> handle the input file parsing and input to generate require model.
 */
public class CustomInputReader {

    final static Logger logger = Logger.getLogger(CustomInputReader.class);

    /**
     * The method process the input file and build the require model.
     *
     * @param inputFile
     *            input file that contain the problem to solve.
     * @return paintshop object that contains all the model needed to solve the problem.
     * @throws IOException
     *             if file not found
     * @throws NumberFormatException
     *             while parsing input numbers.
     */
    public IPaintShop processInputFile(final File inputFile) throws IOException, NumberFormatException {
        final IPaintShop paintshop = new PaintShop2();
        final ModelValidator validator = new ModelValidator();
        try (final BufferedReader br = Files.newBufferedReader(inputFile.toPath())) {
            String readLine = "";
            do {
                readLine = br.readLine();
            } while (readLine.trim().isEmpty());
            final int numberofColor = Integer.parseInt(readLine.trim());
            br.lines().filter(a -> !a.trim().isEmpty()).map(a -> {
                final String arr[] = a.split("\\s+");
                final Customer c = new Customer();
                if (arr.length % 2 == 0) {
                    for (int i = 0; i < arr.length; i = i + 2) {
                        c.addPaintPreference(Integer.parseInt(arr[i]), ColorType.decode(arr[i + 1]));
                    }
                } else {
                    logger.error("Found invalid customer input");
                    throw new RuntimeException(new InvalidInputFileException("Invalid customer input"));
                }
                return c;
            }).forEach(a -> paintshop.addCustomer(validator.validate(a, numberofColor)));
            paintshop.setNumberOfPaints(numberofColor);
        }
        logger.debug("validating input for the basic criteria");
        validator.validate(paintshop);
        return paintshop;
    }
}
