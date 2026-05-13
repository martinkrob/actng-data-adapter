package cz.centropol.actng.dataadapter.dst.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "cfg_qm_question")
public class QMQuestion extends AbstractEntity {

    @Column(name = "integration_id")
    private Integer integrationId;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "question", nullable = false)
    private String question = null;

    @Column(name = "weight", nullable = false)
    private Float weight = 0F;

    @Enumerated(EnumType.STRING)
    @Column(name = "answer_type", nullable = false)
    private QMAnswerType answerType = QMAnswerType.NUMERIC;

    public Integer getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(Integer integrationId) {
        this.integrationId = integrationId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public QMAnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(QMAnswerType answerType) {
        this.answerType = answerType;
    }
}
