package cz.centropol.actng.dataadapter.src.entities;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

@Entity(name = "SRCQMEvaluation")
@Table(name = "qm_evaluation")
public class SRCQMEvaluation implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "task_fk")
    private Integer originTaskId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "set_fk", nullable = false)
    private SRCQMEvaluationSet evaluationSet;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "qm_evaluation_value", joinColumns = {@JoinColumn(name = "evaluation_fk")})
    @MapKeyJoinColumn(name = "question_fk", referencedColumnName = "id")
    @Column(name = "value", nullable = false)
    private Map<SRCQMQuestion, Integer> values;

    @Enumerated(EnumType.STRING)
    @Column(name = "selection_type", nullable = false)
    private SRCQMFileSelectionType selectionType = SRCQMFileSelectionType.RAND_SELECTION;

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

    public Integer getOriginTaskId() {
        return originTaskId;
    }

    public void setOriginTaskId(Integer originTaskId) {
        this.originTaskId = originTaskId;
    }

    public SRCQMEvaluationSet getEvaluationSet() {
        return evaluationSet;
    }

    public void setEvaluationSet(SRCQMEvaluationSet evaluationSet) {
        this.evaluationSet = evaluationSet;
    }

    public Map<SRCQMQuestion, Integer> getValues() {
        return values;
    }

    public void setValues(Map<SRCQMQuestion, Integer> values) {
        this.values = values;
    }

    public SRCQMFileSelectionType getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(SRCQMFileSelectionType selectionType) {
        this.selectionType = selectionType;
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
