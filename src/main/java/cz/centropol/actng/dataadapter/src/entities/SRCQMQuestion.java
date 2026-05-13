package cz.centropol.actng.dataadapter.src.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity(name = "SRCQMQuestion")
@Table(name = "cfg_qm_question")
public class SRCQMQuestion implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "question", nullable = false)
    private String question = null;

    @Column(name = "weight", nullable = false)
    private Float weight = 0F;

    @Enumerated(EnumType.STRING)
    @Column(name = "answer_type", nullable = false)
    private SRCQMAnswerType answerType = SRCQMAnswerType.NUMERIC;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public SRCQMAnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(SRCQMAnswerType answerType) {
        this.answerType = answerType;
    }
}
