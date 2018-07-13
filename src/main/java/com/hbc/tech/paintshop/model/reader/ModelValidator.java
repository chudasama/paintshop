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

import java.util.Map;

import com.hbc.tech.paintshop.analyser.IPaintShop;
import com.hbc.tech.paintshop.exception.*;
import com.hbc.tech.paintshop.model.*;

/**
 * The <code>ModelValidator</code> validate the require pre-conditions.
 */
public class ModelValidator {

    /**
     * Validate individual customer object.
     *
     * @param customer
     *            customer object that needs to validate
     * @param numberofColor
     *            total number of color for which solution is required.
     * @return customer object if successful else throws the exception which is wrap inside runtimeexcpetion due to streaming needs.
     */
    public Customer validate(final Customer customer, final int numberofColor) {

        final Map<Integer, Paint> customerPreference = customer.getCustomerPreference();
        if (customerPreference.size() == 0) {
            // this wont appear as it is handle in as part of input validation
            throw new RuntimeException(new MissingCustomerPreference("Customer preference not provided"));
        }

        int numberOfMatteCount = 0;
        int invalidPaintColorCodeCount = 0;
        for (final Paint p : customerPreference.values()) {
            if (p.getPaintType() == ColorType.ANY || p.getPaintType() == ColorType.MATTE) {
                numberOfMatteCount++;
            }
            if (!(p.getColorCode() > 0 && p.getColorCode() <= numberofColor)) {
                invalidPaintColorCodeCount++;
            }
        }
        if (numberOfMatteCount > 1) {
            throw new RuntimeException(new InvalidMatteChoiceException("Customer provided " + numberOfMatteCount + " choice for Matte Type"));
        }

        if (invalidPaintColorCodeCount >= 1) {
            throw new RuntimeException(new InvalidColorRangeException("Customer choice has invalid color range"));
        }
        return customer;
    }

    /**
     * This will validate customer provided at least one choice.
     *
     * @param paintshop
     */
    public void validate(final IPaintShop paintshop) {

        if (!(paintshop.getCustomers().size() + paintshop.getCustomersAlreadySatisfied().size() > 0)) {
            throw new RuntimeException(new MissingCustomerPreference("Customer preference not provided"));
        }
    }
}
