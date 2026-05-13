package cz.centropol.actng.dataadapter.dst.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "qm_evaluation_set")
public class QMEvaluationSet extends AbstractEntity {

    @Column(name = "integration_id")
    private Integer integrationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "evoluation_set_type", nullable = false)
    private QMEvoluationSetType type = QMEvoluationSetType.VOICE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worktop_fk", nullable = false)
    private QMWorktop worktop;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "form_fk", nullable = false)
    private QMForm form;

    @Column(name = "evaluater", nullable = false, length = 50)
    private String evaluator;

    @Column(name = "agent", nullable = false, length = 50)
    private String agent;

    @Column(name = "n_callcount", nullable = false)
    private Integer callCount = 0;

    @Column(name = "t_duration", nullable = false)
    private long duration = 0L;

    @Column(name = "score", nullable = false)
    private Float score = 0F;

    @Column(name = "score_in_percentage", nullable = false)
    private Float scoreInPercentage = 0F;

    @Column(name = "note", length = 1000)
    private String note;

    public Integer getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(Integer integrationId) {
        this.integrationId = integrationId;
    }

    public QMEvoluationSetType getType() {
        return type;
    }

    public void setType(QMEvoluationSetType type) {
        this.type = type;
    }

    public QMWorktop getWorktop() {
        return worktop;
    }

    public void setWorktop(QMWorktop worktop) {
        this.worktop = worktop;
    }

    public QMForm getForm() {
        return form;
    }

    public void setForm(QMForm form) {
        this.form = form;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
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
