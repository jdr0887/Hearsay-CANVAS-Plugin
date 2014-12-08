package org.renci.hearsay.commons.canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Region;
import org.renci.hearsay.dao.model.TranscriptAlignment;
import org.renci.hearsay.dao.model.TranscriptRefSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteTranscriptAlignmentsRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(DeleteTranscriptAlignmentsRunnable.class);

    private HearsayDAOBean hearsayDAOBean;

    public DeleteTranscriptAlignmentsRunnable() {
        super();
    }

    @Override
    public void run() {
        logger.debug("ENTERING call()");

        try {
            List<TranscriptRefSeq> transcriptRefSeqs = hearsayDAOBean.getTranscriptRefSeqDAO().findAll();
            if (transcriptRefSeqs != null && !transcriptRefSeqs.isEmpty()) {
                for (TranscriptRefSeq transcriptRefSeq : transcriptRefSeqs) {
                    Set<TranscriptAlignment> alignmentSet = transcriptRefSeq.getAlignments();
                    if (alignmentSet != null && !alignmentSet.isEmpty()) {
                        
                        for (TranscriptAlignment alignment : alignmentSet) {
                            Set<Region> regionSet = alignment.getRegions();
                            if (regionSet != null && !regionSet.isEmpty()) {
                                List<Region> regions = new ArrayList<Region>();
                                regions.addAll(regionSet);
                                hearsayDAOBean.getRegionDAO().delete(regions);
                            }
                        }
                        
                        List<TranscriptAlignment> alignments = new ArrayList<TranscriptAlignment>();
                        alignments.addAll(alignmentSet);
                        hearsayDAOBean.getTranscriptAlignmentDAO().delete(alignments);
                    }
                }
            }
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

    }

    public HearsayDAOBean getHearsayDAOBean() {
        return hearsayDAOBean;
    }

    public void setHearsayDAOBean(HearsayDAOBean hearsayDAOBean) {
        this.hearsayDAOBean = hearsayDAOBean;
    }

}
