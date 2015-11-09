package org.renci.hearsay.canvas.hgnc.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "hgnc", name = "gene")
@NamedQueries({ @NamedQuery(name = "HGNCGene.findByName", query = "SELECT a FROM HGNCGene a where a.name = :name") })
public class HGNCGene implements Persistable {

    private static final long serialVersionUID = -7535544497322488928L;

    @Id
    @Column(name = "hgnc_id")
    private Integer id;

    @Column(name = "hgnc_symbol")
    private String symbol;

    @Column(name = "hgnc_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "locus_type_name")
    private LocusType locusType;

    @ManyToOne
    @JoinColumn(name = "locus_group_name")
    private LocusGroup locusGroup;

    @Column(name = "chr")
    private String chromosome;

    @Column(name = "chr_region")
    private String chromosomeRegion;

    @Column(name = "date_modified")
    @Type(type = "date")
    private Date dateModified;

    @ManyToOne
    @JoinColumn(name = "status")
    private HGNCStatusType status;

    public HGNCGene() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocusType getLocusType() {
        return locusType;
    }

    public void setLocusType(LocusType locusType) {
        this.locusType = locusType;
    }

    public LocusGroup getLocusGroup() {
        return locusGroup;
    }

    public void setLocusGroup(LocusGroup locusGroup) {
        this.locusGroup = locusGroup;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getChromosomeRegion() {
        return chromosomeRegion;
    }

    public void setChromosomeRegion(String chromosomeRegion) {
        this.chromosomeRegion = chromosomeRegion;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public HGNCStatusType getStatus() {
        return status;
    }

    public void setStatus(HGNCStatusType status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
                "HGNCGene [id=%s, symbol=%s, name=%s, chromosome=%s, chromosomeRegion=%s, dateModified=%s]", id,
                symbol, name, chromosome, chromosomeRegion, dateModified);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((chromosome == null) ? 0 : chromosome.hashCode());
        result = prime * result + ((chromosomeRegion == null) ? 0 : chromosomeRegion.hashCode());
        result = prime * result + ((dateModified == null) ? 0 : dateModified.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((locusGroup == null) ? 0 : locusGroup.hashCode());
        result = prime * result + ((locusType == null) ? 0 : locusType.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
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
        HGNCGene other = (HGNCGene) obj;
        if (chromosome == null) {
            if (other.chromosome != null)
                return false;
        } else if (!chromosome.equals(other.chromosome))
            return false;
        if (chromosomeRegion == null) {
            if (other.chromosomeRegion != null)
                return false;
        } else if (!chromosomeRegion.equals(other.chromosomeRegion))
            return false;
        if (dateModified == null) {
            if (other.dateModified != null)
                return false;
        } else if (!dateModified.equals(other.dateModified))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (locusGroup == null) {
            if (other.locusGroup != null)
                return false;
        } else if (!locusGroup.equals(other.locusGroup))
            return false;
        if (locusType == null) {
            if (other.locusType != null)
                return false;
        } else if (!locusType.equals(other.locusType))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (symbol == null) {
            if (other.symbol != null)
                return false;
        } else if (!symbol.equals(other.symbol))
            return false;
        return true;
    }

}
