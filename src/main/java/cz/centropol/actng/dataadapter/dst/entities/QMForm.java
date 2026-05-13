package cz.centropol.actng.dataadapter.dst.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cfg_qm_form")
public class QMForm extends AbstractEntity {

    @Column(name = "integration_id")
    private Integer integrationId;

    @Column(name = "enable", nullable = false)
    private boolean enable = false;

    @Column(name = "name", nullable = false, unique = true, length = 255)
    private String name = null;

    @Column(name = "note", nullable = true, length = 255)
    private String note = null;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cfg_qm_form_question", 
               joinColumns = {@javax.persistence.JoinColumn(name = "qmform_fk")}, 
               inverseJoinColumns = {@javax.persistence.JoinColumn(name = "qmquestion_fk")})
    private List<QMQuestion> questions;

    public Integer getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(Integer integrationId) {
        this.integrationId = integrationId;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<QMQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QMQuestion> questions) {
        this.questions = questions;
    }
}
