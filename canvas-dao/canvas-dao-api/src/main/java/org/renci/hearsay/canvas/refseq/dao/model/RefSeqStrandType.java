package org.renci.hearsay.canvas.refseq.dao.model;

public enum RefSeqStrandType {

    POSITIVE("+"), NEGATIVE("-");

    private String value;

    private RefSeqStrandType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
