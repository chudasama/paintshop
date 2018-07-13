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

/**
 * The <code>ColorType</code> represent the finishing propoerty associated with color.
 */
public enum ColorType {

    GLOSS("G", "gloss"), MATTE("M", "matte"), ANY("", "Any");

    private String typeDescription;
    private String typeAbbreviation;

    ColorType(final String abbreviation, final String description) {
        this.typeDescription = description;
        this.typeAbbreviation = abbreviation;
    }

    /**
     * @return the typeDescription
     */
    public String getTypeDescription() {
        return typeDescription;
    }

    /**
     * @return the typeAbbreviation
     */
    public String getTypeAbbreviation() {
        return typeAbbreviation;
    }

    /**
     * The method will decode the Abbreviation to respective Type.
     *
     * @param typeAbbr
     *            String represent abbreviation
     * @return
     */
    public static ColorType decode(final String typeAbbr) {

        for (final ColorType type : ColorType.values()) {
            if (type.typeAbbreviation.equalsIgnoreCase(typeAbbr.trim())) {
                return type;
            }
        }

        throw new IllegalArgumentException("Type not supported " + typeAbbr);
    }

}
