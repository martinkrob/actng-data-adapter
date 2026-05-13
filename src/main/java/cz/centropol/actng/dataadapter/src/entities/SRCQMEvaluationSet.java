package cz.centropol.actng.dataadapter.src.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "SRCQMEvaluationSet")
@Table(name = "qm_evaluation_set")
public class SRCQMEvaluationSet implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "evoluation_set_type", nullable = false)
    private SRCQMEvoluationSetType type = SRCQMEvoluationSetType.VOICE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "form_fk", nullable = false)
    private SRCQMForm form;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", nullable = false)
    private Date creationTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluater_fk", nullable = false)
    private SRCUser evaluator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_fk", nullable = false)
    private SRCUser agent;

    @Column(name = "n_callcount", nullable = false)
    private Integer callCount = 0;

    @Column(name = "t_duration", nullable = false)
    private long duration = 0L;

    @Column(name = "score", nullable = false)
    private Float score = 0F;

    @Column(name = "score_in_percentage", nullable = false)
    private Float scoreInPercentage = 0F;

    @Column(name = "note")
    private String note = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SRCQMEvoluationSetType getType() {
        return type;
    }

    public void setType(SRCQMEvoluationSetType type) {
        this.type = type;
    }

    public SRCQMForm getForm() {
        return form;
    }

    public void setForm(SRCQMForm form) {
        this.form = form;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public SRCUser getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(SRCUser evaluator) {
        this.evaluator = evaluator;
    }

    public SRCUser getAgent() {
        return agent;
    }

    public void setAgent(SRCUser agent) {
        this.agent = agent;
    }

    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Float getScoreInPercentage() {
        return scoreInPercentage;
    }

    public void setScoreInPercentage(Float scoreInPercentage) {
        this.scoreInPercentage = scoreInPercentage;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
