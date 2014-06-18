package org.renci.hearsay.canvas.dao.model;

public class MappingResult {

    private String accession;

    private Integer start;

    private Integer end;

    public MappingResult(String accession, Integer start, Integer end) {
        super();
        this.accession = accession;
        this.start = start;
        this.end = end;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s\n", accession, start, end);
    }

}
