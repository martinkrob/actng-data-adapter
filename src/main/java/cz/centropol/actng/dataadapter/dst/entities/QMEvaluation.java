package cz.centropol.actng.dataadapter.dst.entities;

import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "qm_evaluation")
public class QMEvaluation extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_fk", nullable = true)
    private Task originTask;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "set_fk", nullable = false)
    private QMEvaluationSet evaluationSet;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "qm_evaluation_value", joinColumns = {@JoinColumn(name = "evaluation_fk")})
    @MapKeyJoinColumn(name = "question_fk", referencedColumnName = "id")
    @Column(name = "value", nullable = false)
    private Map<QMQuestion, Integer> values;

    @Enumerated(EnumType.STRING)
    @Column(name = "selection_type", nullable = false)
    private QMFileSelectionType selectionType = QMFileSelectionType.RAND_SELECTION;

    @Column(name = "score", nullable = false)
    private Float score = 0F;

    @Column(name = "score_in_percentage", nullable = false)
    private Float scoreInPercentage = 0F;

    @Column(name = "note")
    private String note = null;

    public Task getOriginTask() {
        return originTask;
    }

    public void setOriginTask(Task originTask) {
        this.originTask = originTask;
    }

    public QMEvaluationSet getEvaluationSet() {
        return evaluationSet;
    }

    public void setEvaluationSet(QMEvaluationSet evaluationSet) {
        this.evaluationSet = evaluationSet;
    }

    public Map<QMQuestion, Integer> getValues() {
        return values;
    }

    public void setValues(Map<QMQuestion, Integer> values) {
        this.values = values;
    }

    public QMFileSelectionType getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(QMFileSelectionType selectionType) {
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
