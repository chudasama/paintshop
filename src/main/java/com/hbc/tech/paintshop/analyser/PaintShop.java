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

import java.util.*;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.hbc.tech.paintshop.exception.NoSolutionExistsException;
import com.hbc.tech.paintshop.model.*;

public class PaintShop implements IPaintShop {

    final static Logger logger = Logger.getLogger(PaintShop.class);
    private static String NO_SOLUTION_EXISTS = "No solution exists";
    private List<Customer> customers = new ArrayList<>();
    private List<Customer> customersAlreadySatisfied = new ArrayList<>();
    private int numberOfPaints;
    Map<Integer, BatchColor> output = new LinkedHashMap<>();
    private Set<Integer> uniquePaintColurCode = new HashSet<>();

    public void addCustomer(final Customer customer) {
        if (customer.hasAnyColorPreference()) {
            customersAlreadySatisfied.add(customer);
        } else {
            customers.add(customer);
            customer.getCustomerPreference().keySet().forEach(uniquePaintColurCode::add);
        }
    }

    public Map<Integer, BatchColor> findSolution(final List<Customer> customers, final Set<Integer> solutionNeeded,
                                                 final Map<Integer, BatchColor> currentDecision) {

        if (customers.size() == 0 && solutionNeeded.size() == 0 && currentDecision.keySet().containsAll(uniquePaintColurCode)) {
            return currentDecision;
        }

        final Customer customer = customers.get(0);
        final Map<Integer, Paint> customerPreference = new HashMap<>(customer.getCustomerPreference());
        customerPreference.remove(currentDecision.keySet());
        final List<Paint> collect = customerPreference.values().stream().sorted().collect(Collectors.toList());

        for (final Paint p : collect) {
            final BatchColor batchColor = currentDecision.get(p.getColorCode());
            if (batchColor != null) {
                final List<Customer> needDecsionForThisEmp = customers.stream().filter(a -> {
                    boolean found = false;
                    for (final int key : a.getCustomerPreference().keySet()) {
                        final BatchColor colorType = currentDecision.get(key);
                        if (colorType != null && a.getCustomerPreference().get(key).getPaintType() == colorType.getPaintType()
                                || solutionNeeded.contains(a.getCustomerPreference().get(key).getColorCode())) {
                            found = true;
                            break;
                        }
                    }
                    return !found;
                }).collect(Collectors.toList());
                if (!needDecsionForThisEmp.isEmpty()) {
                    throw new RuntimeException(new NoSolutionExistsException(NO_SOLUTION_EXISTS));
                }
            } else {
                currentDecision.put(p.getColorCode(), new BatchColor(p.getColorCode(), p.getPaintType()));
                solutionNeeded.remove(p.getColorCode());
                final List<Customer> needDecsionForThisEmp = customers.stream().filter(a -> {
                    boolean found = false;
                    for (final int key : a.getCustomerPreference().keySet()) {
                        final BatchColor colorType = currentDecision.get(key);
                        if (colorType != null && a.getCustomerPreference().get(key).getPaintType() == colorType.getPaintType()) {
                            found = true;
                            break;
                        }
                    }
                    return !found;
                }).collect(Collectors.toList());

                if (!needDecsionForThisEmp.isEmpty() && solutionNeeded.size() != 0) {
                    return findSolution(needDecsionForThisEmp, solutionNeeded, currentDecision);

                } else if (solutionNeeded.size() == 0 && !needDecsionForThisEmp.isEmpty()) {
                    throw new RuntimeException(new NoSolutionExistsException(NO_SOLUTION_EXISTS));
                } else {
                    return currentDecision;

                }
            }
        }

        return null;

    }

    /**
     * @return the customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * @return the customersAlreadySatisfied
     */
    public List<Customer> getCustomersAlreadySatisfied() {
        return customersAlreadySatisfied;
    }

    /**
     * @return the numberOfPaints
     */
    public long getNumberOfPaints() {
        return numberOfPaints;
    }

    public void print() {
        logger.info(formatOutput());
        //System.out.println(formatOutput());
    }

    public String formatOutput() {
        final StringBuffer formatedOutput = new StringBuffer();
        for (int i = 1; i <= numberOfPaints; i++) {
            final BatchColor batchColor = output.get(i);
            if (batchColor == null) {
                formatedOutput.append(ColorType.GLOSS.getTypeAbbreviation());
            } else {
                formatedOutput.append(batchColor.getPaintType().getTypeAbbreviation());
            }
            if (i != numberOfPaints) {
                formatedOutput.append(" ");
            }
        }
        return formatedOutput.toString();
    }

    /**
     * @param customers
     *            the customers to set
     */
    public void setCustomers(final List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * @param customersAlreadySatisfied
     *            the customersAlreadySatisfied to set
     */
    public void setCustomersAlreadySatisfied(final List<Customer> customersAlreadySatisfied) {
        this.customersAlreadySatisfied = customersAlreadySatisfied;
    }

    /**
     * @param numberOfPaints
     *            the numberOfPaints to set
     */
    public void setNumberOfPaints(final int numberOfPaints) {
        this.numberOfPaints = numberOfPaints;
    }

    /**
     *
     */
    public boolean solve() {

        customers.sort(Comparator.comparing(Customer::getPreferenceCount));

        // case 1  when user has only one choice which yield probability of 1/n for the current preferred color
        final List<Customer> customerWithSinglePreference = customers.stream().filter(a -> a.getPreferenceCount() == 1).collect(Collectors.toList());

        // try to identify the case where we don't have multiple choice to solve the problem
        if (customerWithSinglePreference.size() != 0) {
            customerWithSinglePreference.stream().forEach(cust -> {
                final Paint paint = (Paint) cust.getCustomerPreference().values().toArray()[0];
                final BatchColor colorType = output.get(paint.getColorCode());
                if (colorType == null) {
                    output.put(paint.getColorCode(), new BatchColor(paint.getColorCode(), paint.getPaintType(), true));
                } else if (colorType.getPaintType() != paint.getPaintType()) {
                    logger.debug(NO_SOLUTION_EXISTS);
                    throw new RuntimeException(new NoSolutionExistsException(NO_SOLUTION_EXISTS));
                }
            });
        }

        if (customerWithSinglePreference.size() == customers.size()) {
            // all customer with single choice hence no needs to do any further analysis
            return true;
        } else if (customerWithSinglePreference.size() != 0 && customerWithSinglePreference.size() < customers.size()) {
            // case 2 B when you already had some decision
            //1 remove customer which is satisfied using previous decision.
            final List<Customer> customerWithMorePreference = customers.stream().filter(a -> {
                boolean found = false;
                for (final int key : a.getCustomerPreference().keySet()) {
                    final BatchColor colorType = output.get(key);
                    if (colorType != null && a.getCustomerPreference().get(key).getPaintType() == colorType.getPaintType()) {
                        found = true;
                        break;
                    }
                }
                return a.getPreferenceCount() > 1 && !found;
            }).sorted(Comparator.comparing(Customer::getPreferenceCount)).collect(Collectors.toList());

            // all customer satisfied with previous color combination
            if (customerWithMorePreference.size() == 0) {
                return true;
            }

            if (customerWithMorePreference.size() != 0 && output.keySet().containsAll(uniquePaintColurCode)) {
                logger.debug(NO_SOLUTION_EXISTS);
                throw new RuntimeException(new NoSolutionExistsException(NO_SOLUTION_EXISTS));

            } else {
                // now process for unresolved list
                final Set<Integer> requireColor = new HashSet<>(uniquePaintColurCode);
                requireColor.removeAll(output.keySet());
                final Map<Integer, BatchColor> findSolution = findSolution(customerWithMorePreference, requireColor, output);

                if (findSolution == null) {
                    logger.debug(NO_SOLUTION_EXISTS);
                    throw new RuntimeException(new NoSolutionExistsException(NO_SOLUTION_EXISTS));
                } else {
                    output = findSolution;
                    return true;
                }
            }

        } else {
            // case 2 A when you don't have any customer with single preference which gives you probability to full fill its choice by number of preference
            final Set<Integer> requireColor = new HashSet<>(uniquePaintColurCode);
            requireColor.removeAll(output.keySet());
            final Map<Integer, BatchColor> findSolution = findSolution(customers, requireColor, output);

            if (findSolution == null) {
                logger.debug(NO_SOLUTION_EXISTS);
                throw new RuntimeException(new NoSolutionExistsException(NO_SOLUTION_EXISTS));
            } else {
                output = findSolution;
                return true;
            }
        }

    }

}
