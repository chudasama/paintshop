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

import java.io.*;

import org.apache.log4j.Logger;

import com.hbc.tech.paintshop.exception.NoSolutionExistsException;
import com.hbc.tech.paintshop.model.reader.CustomInputReader;

/**
 * The <code>DriverClass</code> is main entry drive class which drive the solution.
 */
public class DriverClass {

    final static Logger logger = Logger.getLogger(DriverClass.class);

    public static void main(final String[] args) {
        final CustomInputReader reader = new CustomInputReader();
        if (args.length == 0) {
            logger.info("Please provide input file: USAGE java -jar jarName inputfileName");
            return;
        }
        try {
            final IPaintShop processInputFile = reader.processInputFile(new File(args[0]));
            if (processInputFile.solve()) {
                processInputFile.print();
            }
        } catch (final FileNotFoundException e) {
            logger.error("Please provide valid file path", e);
        } catch (final IOException e) {
            logger.error("FILE NOT FOUND", e);
        } catch (final NumberFormatException e) {
            logger.error("Input file contins invalid information", e);
        } catch (final Throwable e) {
            if (e.getCause().getClass().equals(NoSolutionExistsException.class)) {
                logger.info(e.getCause().getMessage());
            } else {
                logger.error(e.getMessage());
            }
        }
    }

}
