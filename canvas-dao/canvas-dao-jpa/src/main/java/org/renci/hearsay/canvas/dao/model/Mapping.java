package org.renci.hearsay.canvas.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.renci.hearsay.dao.model.RegionType;
import org.renci.hearsay.dao.model.StrandType;

public class Mapping implements Serializable {

    private static final long serialVersionUID = 1556350565254385634L;

    private String versionAccession;

    private StrandType strandType;

    private TreeSet<Region> regions;

    public Mapping(String versionAccession, StrandType strandType) {
        super();
        this.versionAccession = versionAccession;
        this.strandType = strandType;
        this.regions = new TreeSet<Region>();
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
                exon.setContigStop(exon.getTranscriptStop() - regionStart + 1);
            }
        }
    }

    public void addUTRs(Integer regionStart, Integer regionStop) {

        int strand = getStrandType().equals(StrandType.PLUS) ? 1 : -1;

        NavigableSet<Region> navigableRegionSet = getRegions();
        if (getStrandType().equals(StrandType.MINUS)) {
            navigableRegionSet = getRegions().descendingSet();
        }
        Region firstRegion = navigableRegionSet.first();

        if (regionStart > firstRegion.getTranscriptStart()) {
            int previousGenomeStop = getRegions().lower(firstRegion).getGenomeStop();
            int adjustment = ((firstRegion.getTranscriptStart() + 1) - (regionStart - 1));
            getRegions().lower(firstRegion).setGenomeStop(previousGenomeStop + adjustment - 1);
            getRegions().lower(firstRegion).setTranscriptStop(regionStart);
            Region utr5 = new Region();
            utr5.setGenomeStart(previousGenomeStop + adjustment);
            utr5.setGenomeStop(previousGenomeStop);
            utr5.setTranscriptStart(regionStart - 1);
            utr5.setTranscriptStop(firstRegion.getTranscriptStart() + 1);
            utr5.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
            getRegions().add(utr5);
            firstRegion.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
        }

        Region lastRegion = navigableRegionSet.last();

        if (regionStop < lastRegion.getTranscriptStart() || regionStop > lastRegion.getTranscriptStop()) {
            int v = regionStop - lastRegion.getTranscriptStart();
            int gc = lastRegion.getGenomeStart() + strand * v;

            Region utr3 = new Region();
            utr3.setGenomeStart(lastRegion.getGenomeStart());
            utr3.setGenomeStop(gc - 1);
            utr3.setTranscriptStart(lastRegion.getTranscriptStart());
            utr3.setTranscriptStop(regionStop + 1);
            utr3.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
            getRegions().add(utr3);
            lastRegion.setTranscriptStart(regionStop);
            lastRegion.setGenomeStart(gc);
        }

        for (Region region : getRegions()) {
            System.out.println(region.toString());
        }
    }

    public void addIntrons() {

        List<Region> exonsToAdd = new ArrayList<Region>();

        Iterator<Region> iter = getRegions().iterator();
        while (iter.hasNext()) {
            Region current = iter.next();
            Region previous = getRegions().lower(current);
            Region next = getRegions().higher(current);

            if (previous != null) {
                
                if (current == previous && next == null) {
                    continue;
                }

                if (getStrandType().equals(StrandType.PLUS) && previous.getGenomeStop() + 1 != current.getGenomeStart()) {
                    Region exon = new Region();
                    exon.setGenomeStart(previous.getGenomeStop() + 1);
                    exon.setGenomeStop(current.getGenomeStart() - 1);
                    exon.setRegionType(RegionType.INTRON);
                    exonsToAdd.add(exon);
                }

                if (getStrandType().equals(StrandType.MINUS)
                        && current.getGenomeStart() - 1 != previous.getGenomeStop()) {

                    Region exon = new Region();
                    exon.setGenomeStart(previous.getGenomeStop() + 1);
                    exon.setGenomeStop(current.getGenomeStart() - 1);
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
