package org.renci.hearsay.canvas.refseq.dao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.renci.hearsay.canvas.refseq.dao.model.RefSeqStrandType;

@Converter
public class RefSeqStrandTypeConverter implements AttributeConverter<RefSeqStrandType, String> {

    @Override
    public String convertToDatabaseColumn(RefSeqStrandType strandType) {
        return strandType.getValue();
    }

    @Override
    public RefSeqStrandType convertToEntityAttribute(String strandTypeString) {
        RefSeqStrandType ret = null;
        for (RefSeqStrandType type : RefSeqStrandType.values()) {
            if (strandTypeString.equals(type.getValue())) {
                ret = type;
                break;
            }
        }
        return ret;
    }

}
