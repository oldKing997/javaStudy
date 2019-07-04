package com.wwq.designpattern.factorypattern;

import org.apache.commons.lang3.StringUtils;

public class FactoryPattern {
    public Shape getShape(String shapeType) {
        if (!StringUtils.isNotBlank(shapeType)) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }
}
