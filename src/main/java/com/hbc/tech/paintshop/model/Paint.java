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
 * The <code>Paint</code> model the paint.
 */
public class Paint implements Comparable<Paint> {

    /**
     * The paintType represent the type of finishing expected for the provided color code.
     */
    private ColorType paintType;
    /**
     * The colorCode represent the code/number associated with paint.
     */
    private int colorCode;

    /**
     * @return the paintType
     */
    public ColorType getPaintType() {
        return paintType;
    }

    /**
     * Default Constructor.
     */
    public Paint() {
        super();
    }

    /**
     * Constructor with options.
     *
     * @param colorCode
     *            : color code associated with batch
     * @param paintType
     *            : paint type associated with color
     */
    public Paint(final ColorType paintType, final int colorCode) {
        super();
        this.paintType = paintType;
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
     * @return the colorCode
     */
    public int getColorCode() {
        return colorCode;
    }

    /**
     * @param colorCode
     *            the colorCode to set
     */
    public void setColorCode(final int colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + colorCode;
        result = prime * result + ((paintType == null) ? 0 : paintType.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Paint other = (Paint) obj;
        if (colorCode != other.colorCode) {
            return false;
        }
        if (paintType != other.paintType) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(final Paint o) {

        if (this.paintType == ColorType.GLOSS && o.paintType == ColorType.MATTE) {
            return -1;
        } else if (this.paintType == ColorType.MATTE && o.paintType == ColorType.GLOSS) {
            return 1;
        }
        return this.colorCode - o.colorCode;

    }

}
