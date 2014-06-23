package org.renci.hearsay.canvas.dao.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.renci.hearsay.dao.model.RegionType;
import org.renci.hearsay.dao.model.StrandType;

public class Mapping implements Serializable {

    private static final long serialVersionUID = 1556350565254385634L;

    private String versionAccession;

    private StrandType strandType;

    private TreeSet<Exon> exons = new TreeSet<Exon>(new Comparator<Exon>() {

        @Override
        public int compare(Exon o1, Exon o2) {
            return o1.getTranscriptStart().compareTo(o2.getTranscriptStart());
        }

    });

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

    public TreeSet<Exon> getExons() {
        return exons;
    }

    public void setExons(TreeSet<Exon> exons) {
        this.exons = exons;
    }

    public void addCDSCoordinates(Integer regionStart) {
        for (Exon exon : getExons()) {
            if (exon.getRegionType() == RegionType.EXON) {
                exon.setContigStart(exon.getTranscriptStart() - regionStart + 1);
                exon.setContigEnd(exon.getTranscriptEnd() - regionStart + 1);
            }
        }
    }

    public void addUTRs(Integer regionStart, Integer regionEnd) {

        int exonIndex = 0;
        Exon firstExon = exons.first();
        boolean foundFirst = true;

        while (regionStart > firstExon.getTranscriptEnd()) {
            firstExon.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
            ++exonIndex;
            if (exonIndex == exons.size()) {
                foundFirst = false;
                break;
            }
            firstExon = exons.higher(firstExon);
        }
        int strand = getStrandType().equals(StrandType.POSITIVE) ? 1 : -1;

        if (foundFirst && regionStart > firstExon.getTranscriptStart()) {
            int v = regionStart - firstExon.getTranscriptStart();
            int gc = firstExon.getGenomeStart() + strand * v;
            Exon utr5 = new Exon();
            utr5.setGenomeEnd(gc - strand);
            utr5.setGenomeStart(firstExon.getGenomeStart());
            utr5.setTranscriptEnd(regionStart - 1);
            utr5.setTranscriptStart(firstExon.getTranscriptStart());
            utr5.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
            getExons().add(utr5);
            firstExon.setContigStart(gc);
            firstExon.setTranscriptStart(regionStart);
        }

        exonIndex = getExons().size() - 1;

        Exon lastExon = getExons().last();
        boolean foundLast = true;
        while (regionEnd < lastExon.getTranscriptStart() || regionEnd > lastExon.getTranscriptEnd()) {
            lastExon.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
            --exonIndex;
            if (exonIndex == -1) {
                foundLast = false;
                break;
            }
            lastExon = exons.lower(lastExon);
        }

        if (foundLast) {
            int v = regionEnd - lastExon.getTranscriptStart();
            int gc = lastExon.getGenomeStart() + strand * v;
            Exon utr3 = new Exon();

            // TODO ensure that this is right....
            utr3.setGenomeStart(gc + strand);
            utr3.setGenomeEnd(lastExon.getGenomeEnd());

            utr3.setTranscriptEnd(lastExon.getTranscriptEnd());
            utr3.setTranscriptStart(regionEnd + 1);
            utr3.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
            getExons().add(utr3);
            lastExon.setContigEnd(gc);
            lastExon.setTranscriptEnd(regionEnd);
        }

    }

    public void addIntrons() {

        Set<Exon> exonsToAdd = new HashSet<Exon>();

        Iterator<Exon> iter = getExons().descendingIterator();
        while (iter.hasNext()) {
            Exon current = iter.next();
            Exon next = getExons().higher(current);
            Exon previous = getExons().lower(current);

            if (previous != null) {
                if (getStrandType().equals(StrandType.POSITIVE)
                        && previous.getGenomeEnd() + 1 != current.getGenomeStart()) {

                    Exon exon = new Exon();
                    exon.setGenomeStart(previous.getGenomeEnd() + 1);
                    exon.setGenomeEnd(current.getGenomeStart() - 1);
                    exon.setTranscriptStart(-1);
                    exon.setTranscriptEnd(-1);
                    exon.setRegionType(RegionType.INTRON);
                    exonsToAdd.add(exon);
                }

                if (getStrandType().equals(StrandType.NEGATIVE)
                        && current.getGenomeStart() + 1 != previous.getGenomeEnd()) {

                    Exon exon = new Exon();
                    exon.setGenomeStart(current.getGenomeStart() + 1);
                    exon.setGenomeEnd(previous.getGenomeEnd() - 1);
                    exon.setTranscriptStart(-1);
                    exon.setTranscriptEnd(-1);
                    exon.setRegionType(RegionType.INTRON);
                    exonsToAdd.add(exon);

                }
            }

        }
        getExons().addAll(exonsToAdd);

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
