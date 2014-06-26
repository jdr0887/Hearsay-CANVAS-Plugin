package org.renci.hearsay.canvas.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.renci.hearsay.dao.model.RegionType;
import org.renci.hearsay.dao.model.StrandType;

public class Mapping implements Serializable {

    private static final long serialVersionUID = 1556350565254385634L;

    private String versionAccession;

    private StrandType strandType;

    private final TreeSet<Region> regions = new TreeSet<Region>();

    public Mapping(String versionAccession, StrandType strandType) {
        super();
        this.versionAccession = versionAccession;
        this.strandType = strandType;
    }

    public StrandType getStrandType() {
        return strandType;
    }

    public void setStrandType(StrandType strandType) {
        this.strandType = strandType;
    }

    public String getVersionAccession() {
        return versionAccession;
    }

    public void setVersionAccession(String versionAccession) {
        this.versionAccession = versionAccession;
    }

    public TreeSet<Region> getRegions() {
        return regions;
    }

    public void addCDSCoordinates(Integer regionStart) {
        for (Region exon : getRegions()) {
            if (exon.getRegionType() == RegionType.EXON) {
                exon.setContigStart(exon.getTranscriptStart() - regionStart + 1);
                exon.setContigEnd(exon.getTranscriptEnd() - regionStart + 1);
            }
        }
    }

    public void addUTRs(Integer regionStart, Integer regionEnd) {

        int strand = getStrandType().equals(StrandType.POSITIVE) ? 1 : -1;

        Region firstExon = getRegions().first();

        if (regionStart > firstExon.getTranscriptStart()) {
            int v = regionStart - firstExon.getTranscriptStart();
            int gc = firstExon.getGenomeStart() + strand * v;
            Region utr5 = new Region();
            utr5.setGenomeEnd(gc - strand);
            utr5.setGenomeStart(firstExon.getGenomeStart());
            utr5.setTranscriptEnd(regionStart - 1);
            utr5.setTranscriptStart(firstExon.getTranscriptStart());
            utr5.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
            getRegions().add(utr5);
            firstExon.setGenomeStart(gc);
            firstExon.setTranscriptStart(regionStart);
        }

        Region lastExon = getRegions().last();

        if (regionEnd < lastExon.getTranscriptEnd()) {
            int v = regionEnd - lastExon.getTranscriptStart();
            int gc = lastExon.getGenomeStart() + strand * v;
            Region utr3 = new Region();
            utr3.setGenomeStart(gc + strand);
            utr3.setGenomeEnd(lastExon.getGenomeEnd());
            utr3.setTranscriptEnd(lastExon.getTranscriptEnd());
            utr3.setTranscriptStart(regionEnd + 1);
            utr3.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
            getRegions().add(utr3);
            lastExon.setGenomeEnd(gc);
            lastExon.setTranscriptEnd(regionEnd);
        }

    }

    public void addIntrons() {

        List<Region> exonsToAdd = new ArrayList<Region>();

        Iterator<Region> iter = getRegions().descendingIterator();
        while (iter.hasNext()) {
            Region current = iter.next();
            Region next = getRegions().higher(current);
            Region previous = getRegions().lower(current);

            if (previous != null) {
                if (getStrandType().equals(StrandType.POSITIVE)
                        && previous.getGenomeEnd() + 1 != current.getGenomeStart()) {

                    Region exon = new Region();
                    exon.setGenomeStart(previous.getGenomeEnd() + 1);
                    exon.setGenomeEnd(current.getGenomeStart() - 1);
                    exon.setRegionType(RegionType.INTRON);
                    exonsToAdd.add(exon);
                }

                if (getStrandType().equals(StrandType.NEGATIVE)
                        && current.getGenomeStart() + 1 != previous.getGenomeEnd()) {

                    Region exon = new Region();
                    exon.setGenomeStart(current.getGenomeStart() + 1);
                    exon.setGenomeEnd(previous.getGenomeEnd() - 1);
                    exon.setRegionType(RegionType.INTRON);
                    exonsToAdd.add(exon);

                }
            }

        }
        getRegions().addAll(exonsToAdd);

    }

    @Override
    public String toString() {
        return String.format("Mapping [versionAccession=%s]", versionAccession);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((versionAccession == null) ? 0 : versionAccession.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mapping other = (Mapping) obj;
        if (versionAccession == null) {
            if (other.versionAccession != null)
                return false;
        } else if (!versionAccession.equals(other.versionAccession))
            return false;
        return true;
    }

}
