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

import java.util.List;

import com.hbc.tech.paintshop.model.Customer;

public interface IPaintShop {
    /**
     * Try to resolve the problem.
     *
     * @return true on success.
     */
    public boolean solve();

    /**
     * Print solution on console.
     */
    public void print();

    /**
     * Generate the formatted string for the output.
     *
     * @return formatted output string.
     */
    public String formatOutput();

    /**
     * Add customer to paint shop decision model. where it will check for several criteria and bifurcate the customer list to two type ordinary
     * Customer and customer which are already satisfied.
     *
     * @param validate
     */
    public void addCustomer(Customer validate);

    /**
     * set the number of total paint batch needs to create.
     *
     * @param numberofColor
     *            number of paint
     */
    public void setNumberOfPaints(int numberofColor);

    /**
     * @return all customer excluding customer available in already satisfied list.
     */
    public List<Customer> getCustomers();

    /**
     * @return all customer which are already satisfied by providing any choice preference for at least one color.
     */
    public List<Customer> getCustomersAlreadySatisfied();
}
