package org.renci.hearsay.canvas.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;

import org.renci.hearsay.canvas.dao.model.Mapping;
import org.renci.hearsay.canvas.dao.model.Region;
import org.renci.hearsay.dao.model.RegionType;
import org.renci.hearsay.dao.model.StrandType;

public class TranscriptUtil {

    public static void addCDSCoordinates(Mapping mapping, Integer regionStart) {
        for (Region exon : mapping.getRegions()) {
            if (exon.getRegionType() == RegionType.EXON) {
                exon.setContigStart(Math.max(1, exon.getTranscriptStart() - regionStart + 1));
                exon.setContigStop(exon.getTranscriptStop() - regionStart + 1);
            }
        }
    }

    public static void addUTR5s(Mapping mapping, Integer regionStart) {
        StrandType strandType = mapping.getStrandType();

        if (strandType.equals(StrandType.MINUS)) {

            for (Region region : mapping.getRegions()) {
                if (regionStart > region.getTranscriptStart()) {
                    region.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
                }
            }

            Region firstRegion = null;
            Iterator<Region> navigableRegionIter = mapping.getRegions().iterator();
            while (navigableRegionIter.hasNext()) {
                Region r = navigableRegionIter.next();
                if (regionStart > r.getTranscriptStop()) {
                    firstRegion = r;
                    break;
                }
            }

            if (regionStart > firstRegion.getTranscriptStop()) {
                Region utr5 = new Region();
                utr5.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
                int startTranscript = firstRegion.getTranscriptStop();
                int stopTranscript = regionStart - 1;
                int diff = startTranscript - stopTranscript;
                utr5.setGenomeStart(firstRegion.getGenomeStop() + diff);
                utr5.setGenomeStop(firstRegion.getGenomeStop());
                utr5.setTranscriptStart(regionStart - 1);
                utr5.setTranscriptStop(firstRegion.getTranscriptStop());
                mapping.getRegions().add(utr5);

                firstRegion.setGenomeStop(utr5.getGenomeStart() - 1);
                firstRegion.setTranscriptStop(regionStart);

            }

        } else {

            for (Region region : mapping.getRegions()) {
                if (regionStart > region.getTranscriptStop()) {
                    region.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
                }
            }

            Region firstRegion = null;
            Iterator<Region> navigableRegionIter = mapping.getRegions().iterator();
            while (navigableRegionIter.hasNext()) {
                Region r = navigableRegionIter.next();
                if (regionStart > r.getTranscriptStop()) {
                    firstRegion = r;
                    break;
                }
            }

            Region nextRegion = mapping.getRegions().higher(firstRegion);

            if (regionStart > firstRegion.getTranscriptStop()) {
                Region utr5 = new Region();
                utr5.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
                utr5.setGenomeStart(nextRegion.getGenomeStart());
                int startTranscript = firstRegion.getTranscriptStop() + 1;
                utr5.setTranscriptStart(startTranscript);
                int stopTranscript = regionStart - 1;
                utr5.setTranscriptStop(stopTranscript);
                int diff = startTranscript - stopTranscript;
                utr5.setGenomeStop(nextRegion.getGenomeStart() - (1 * diff));
                mapping.getRegions().add(utr5);

                nextRegion.setGenomeStart(utr5.getGenomeStop() + 1);
                nextRegion.setTranscriptStart(regionStart);
                System.out.println();
            }

        }

    }

    public static void addUTR3s(Mapping mapping, Integer regionStop) {
        StrandType strandType = mapping.getStrandType();

        if (strandType.equals(StrandType.MINUS)) {

            NavigableSet<Region> navigableRegionSet = mapping.getRegions();
            for (Region region : navigableRegionSet) {
                if (regionStop < region.getTranscriptStop() && !region.equals(navigableRegionSet.first())) {
                    region.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
                }
            }

            Region lastRegion = null;
            Iterator<Region> navigableRegionIter = mapping.getRegions().iterator();
            while (navigableRegionIter.hasNext()) {
                Region r = navigableRegionIter.next();
                if (regionStop > r.getTranscriptStop()) {
                    lastRegion = r;
                    break;
                }
            }

            if (regionStop > lastRegion.getTranscriptStop()) {

                Region utr3 = new Region();
                utr3.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
                utr3.setGenomeStart(lastRegion.getGenomeStart());
                int startTranscript = lastRegion.getTranscriptStart();
                utr3.setTranscriptStart(startTranscript);
                int stopTranscript = regionStop + 1;
                utr3.setTranscriptStop(stopTranscript);
                int diff = startTranscript - stopTranscript;
                utr3.setGenomeStop(utr3.getGenomeStart() + diff);
                mapping.getRegions().add(utr3);

                lastRegion.setGenomeStart(utr3.getGenomeStop() + 1);
                lastRegion.setTranscriptStart(regionStop);

            }

        } else {

            NavigableSet<Region> navigableRegionSet = mapping.getRegions().descendingSet();
            for (Region region : navigableRegionSet) {
                if (regionStop < region.getTranscriptStart() && !region.equals(navigableRegionSet.first())) {
                    region.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
                }
            }

            Region lastRegion = null;
            Iterator<Region> navigableRegionIter = mapping.getRegions().descendingIterator();
            while (navigableRegionIter.hasNext()) {
                Region r = navigableRegionIter.next();
                if (r.getTranscriptStop() > regionStop) {
                    lastRegion = r;
                    break;
                }
            }

            if (lastRegion != null) {

                if (lastRegion.getTranscriptStop() > regionStop) {
                    Region utr3 = new Region();
                    utr3.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
                    int startTranscript = lastRegion.getTranscriptStop();
                    utr3.setTranscriptStop(startTranscript);
                    int stopTranscript = regionStop + 1;
                    utr3.setTranscriptStart(stopTranscript);
                    int diff = startTranscript - stopTranscript;
                    utr3.setGenomeStop(lastRegion.getGenomeStop());
                    utr3.setGenomeStart(lastRegion.getGenomeStop() - diff);
                    mapping.getRegions().add(utr3);

                    lastRegion.setGenomeStop(lastRegion.getGenomeStart()
                            + (regionStop - lastRegion.getTranscriptStart()));
                    lastRegion.setTranscriptStop(regionStop);
                    System.out.println();
                }

            }

        }

    }

    public static void addIntrons(Mapping mapping) {

        List<Region> regions = new ArrayList<Region>();

        if (mapping.getStrandType().equals(StrandType.MINUS)) {

            Iterator<Region> regionIter = mapping.getRegions().descendingIterator();
            while (regionIter.hasNext()) {
                Region current = regionIter.next();
                if (current.equals(mapping.getRegions().first())) {
                    continue;
                }
                Region previous = mapping.getRegions().higher(current);

                if (previous == null || current.equals(previous)) {
                    continue;
                }

                if (current.getGenomeStop() + 1 != previous.getGenomeStart()) {
                    Region exon = new Region();
                    exon.setRegionType(RegionType.INTRON);
                    exon.setGenomeStart(current.getGenomeStop() + 1);
                    exon.setGenomeStop(previous.getGenomeStart() - 1);
                    regions.add(exon);
                }
            }

        } else {

            Iterator<Region> regionIter = mapping.getRegions().iterator();
            while (regionIter.hasNext()) {
                Region current = regionIter.next();
                if (current.equals(mapping.getRegions().first())) {
                    continue;
                }
                Region previous = mapping.getRegions().lower(current);

                if (current.equals(previous)) {
                    continue;
                }

                if (previous.getGenomeStop() + 1 != current.getGenomeStart()) {
                    Region exon = new Region();
                    exon.setRegionType(RegionType.INTRON);
                    exon.setGenomeStart(previous.getGenomeStop() + 1);
                    exon.setGenomeStop(current.getGenomeStart() - 1);
                    regions.add(exon);
                }
            }

        }

        mapping.getRegions().addAll(regions);

    }

}
