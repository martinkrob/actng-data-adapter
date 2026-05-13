package cz.centropol.actng.dataadapter.src.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "SRCQMForm")
@Table(name = "cfg_qm_form")
public class SRCQMForm implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "enable", nullable = false)
    private boolean enable = false;

    @Column(name = "name", nullable = false, length = 255)
    private String name = null;

    @Column(name = "note", nullable = true, length = 255)
    private String note = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time")
    private Date creationTime;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cfg_qm_form_question", 
               joinColumns = {@javax.persistence.JoinColumn(name = "qmform_fk")}, 
               inverseJoinColumns = {@javax.persistence.JoinColumn(name = "qmquestion_fk")})
    private List<SRCQMQuestion> questions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public List<SRCQMQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<SRCQMQuestion> questions) {
        this.questions = questions;
    }
}
