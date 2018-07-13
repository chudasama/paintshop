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
package com.hbc.tech.paintshop.model;

import java.util.*;

import org.apache.log4j.Logger;

/**
 * The <code>Customer</code> represent the customer entity for the given model.
 */
public class Customer {

    final static Logger logger = Logger.getLogger(Customer.class);
    /**
     * The number of color preference provided by customer.
     */
    private Map<Integer, Paint> customerPreference = new HashMap<>();
    /**
     * is customer provided any choice for any color.
     */
    private boolean isAnypreference = false;

    /**
     * @return the customerPreference
     */
    public Map<Integer, Paint> getCustomerPreference() {
        return customerPreference;
    }

    /**
     * @param customerPreference
     *            the customerPreference to set
     */
    public void setCustomerPreference(final Map<Integer, Paint> customerPreference) {
        this.customerPreference = customerPreference;
    }

    public void addPaintPreference(final int colorCode, final ColorType paintType) {
        final Paint paint = new Paint();
        paint.setColorCode(colorCode);
        paint.setPaintType(paintType);
        final Paint paint2 = customerPreference.get(colorCode);
        if (paint2 == null) {
            customerPreference.put(colorCode, paint);
        } else if (paint2.getPaintType() != ColorType.ANY) {
            {
                if (paint2.getPaintType() != paintType) {
                    paint.setPaintType(ColorType.ANY);
                    logger.debug("Customer has any choice for color " + paint.getColorCode());
                    isAnypreference = true;
                    customerPreference.put(colorCode, paint);
                }
            }
        }
    }

    public Optional<Paint> getPaint(final int colorCode) {
        final Paint paint = customerPreference.get(colorCode);
        return paint == null ? Optional.empty() : Optional.of(paint);

    }

    public int getPreferenceCount() {
        return customerPreference.size();
    }

    public boolean hasAnyColorPreference() {
        return isAnypreference;
    }

}
