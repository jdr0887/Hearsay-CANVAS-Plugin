package org.renci.hearsay.commands.canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.refseq.dao.model.Feature;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.Identifier;
import org.renci.hearsay.dao.model.Location;
import org.renci.hearsay.dao.model.ReferenceSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullFeaturesCallable implements Callable<Void> {

    private final Logger logger = LoggerFactory.getLogger(PullFeaturesCallable.class);

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    private String refSeqVersion;

    private String geneName;

    public PullFeaturesCallable(CANVASDAOBean canvasDAOBean, HearsayDAOBean hearsayDAOBean, String refSeqVersion) {
        super();
        this.canvasDAOBean = canvasDAOBean;
        this.hearsayDAOBean = hearsayDAOBean;
        this.refSeqVersion = refSeqVersion;
    }

    @Override
    public Void call() throws HearsayDAOException {
        logger.debug("ENTERING call()");
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(8, 8, 3, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>());
        List<ReferenceSequence> refSeqs = new ArrayList<ReferenceSequence>();

        if (StringUtils.isNotEmpty(geneName)) {
            List<Gene> geneList = hearsayDAOBean.getGeneDAO().findBySymbol(geneName);
            refSeqs.addAll(hearsayDAOBean.getReferenceSequenceDAO().findByGeneId(geneList.get(0).getId()));
        } else {
            refSeqs.addAll(hearsayDAOBean.getReferenceSequenceDAO().findAll());
        }

        if (refSeqs != null && !refSeqs.isEmpty()) {
            for (ReferenceSequence refSeq : refSeqs) {

                String transcriptId = null;
                for (Identifier identifier : refSeq.getIdentifiers()) {
                    if (identifier.getSystem().equals("www.ncbi.nlm.nih.gov/nuccore")) {
                        transcriptId = identifier.getValue();
                        break;
                    }
                }
                List<Feature> canvasFeatures = canvasDAOBean.getFeatureDAO()
                        .findByRefSeqVersionAndTranscriptId(refSeqVersion, transcriptId);
                if (canvasFeatures != null && !canvasFeatures.isEmpty()) {
                    for (Feature canvasFeature : canvasFeatures) {
                        RegionGroup regionGroup = canvasFeature.getRegionGroup();
                        if (regionGroup != null) {
                            Set<RegionGroupRegion> regionGroupRegions = regionGroup.getRegionGroupRegions();
                            if (regionGroupRegions != null && !regionGroupRegions.isEmpty()) {
                                RegionGroupRegion[] regionGroupRegionArray = regionGroupRegions
                                        .toArray(new RegionGroupRegion[regionGroupRegions.size()]);
                                org.renci.hearsay.dao.model.Feature hearsayFeature = new org.renci.hearsay.dao.model.Feature();
                                hearsayFeature.setNote(canvasFeature.getNote());
                                Location region = new Location(regionGroupRegionArray[0].getRegionStart(), regionGroupRegionArray[0].getRegionEnd());
                                
                                hearsayFeature.getLocations().add(region);
                                hearsayFeature.getReferenceSequences().add(refSeq);
                                hearsayDAOBean.getFeatureDAO().save(hearsayFeature);
                            }
                        }
                    }
                }

            }
        }
        tpe.shutdown();

        return null;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public CANVASDAOBean getCanvasDAOBean() {
        return canvasDAOBean;
    }

    public void setCanvasDAOBean(CANVASDAOBean canvasDAOBean) {
        this.canvasDAOBean = canvasDAOBean;
    }

    public HearsayDAOBean getHearsayDAOBean() {
        return hearsayDAOBean;
    }

    public void setHearsayDAOBean(HearsayDAOBean hearsayDAOBean) {
        this.hearsayDAOBean = hearsayDAOBean;
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

}
