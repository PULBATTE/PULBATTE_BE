package com.pulbatte.pulbatte.plantSearch.entity;

import javax.persistence.AttributeConverter;

public class PlantTagConverter implements AttributeConverter<PlantTag, String> {

    @Override
    public String convertToDatabaseColumn(PlantTag attribute) {
        if(attribute == null) {
            return null;
        }
        return attribute.getTagCode();
    }

    @Override
    public PlantTag convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }
        return PlantTag.enumOf(dbData);
    }
}
