package ecjtu.husen.pojo.converter;

import ecjtu.husen.pojo.DTO.InStatu;

import javax.persistence.AttributeConverter;

/**
 * @author 11785
 */
public class InStatuConverter implements AttributeConverter<InStatu, Integer> {

    @Override
    public Integer convertToDatabaseColumn(InStatu inStatu) {
        return inStatu.getValue();
    }

    @Override
    public InStatu convertToEntityAttribute(Integer integer) {
        for(InStatu inStatu : InStatu.values()){
            if(integer.equals(inStatu.getValue())){
                return inStatu;
            }
        }
        return null;
    }
}
