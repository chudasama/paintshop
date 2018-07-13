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

import org.apache.log4j.Logger;

/**
 * The <code> BatchColor</code> represent the batch color needs to produce for the respective colorcode.
 */
/**
 *
 */
public class BatchColor {
    final static Logger logger = Logger.getLogger(BatchColor.class);
    /**
     * Color Code
     */
    int colorCode;

    /**
     * isSticky shows shows the stickiness associated with color choice.
     */
    boolean isSticky = false;
    /**
     * Paint type.
     */
    ColorType paintType;

    /**
     * Default constructor.
     */
    public BatchColor() {
        super();
    }

    /**
     * Constructor with options.
     *
     * @param colorCode
     *            : color code associated with batch
     * @param paintType
     *            : paint type associated with color
     * @param isSticky
     *            : stickiness of color
     */
    public BatchColor(final int colorCode, final ColorType paintType, final boolean isSticky) {
        super();
        this.colorCode = colorCode;
        this.paintType = paintType;
        this.isSticky = isSticky;
    }

    /**
     * @return the colorCode
     */
    public int getColorCode() {
        return colorCode;
    }

    /**
     * @return the paintType
     */
    public ColorType getPaintType() {
        return paintType;
    }

    /**
     * Constructor with options.
     *
     * @param colorCode
     *            : color code associated with batch
     * @param paintType
     *            : paint type associated with color
     */
    public BatchColor(final int colorCode, final ColorType paintType) {
        super();
        this.colorCode = colorCode;
        this.paintType = paintType;
    }

    /**
     * @return the isSticky
     */
    public boolean isSticky() {
        return isSticky;
    }

    /**
     * @param colorCode
     *            the colorCode to set
     */
    public void setColorCode(final int colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * @param paintType
     *            the paintType to set
     */
    public void setPaintType(final ColorType paintType) {
        this.paintType = paintType;
    }

    /**
     * @param isSticky
     *            the isSticky to set
     */
    public void setSticky(final boolean isSticky) {
        this.isSticky = isSticky;
    }

}
