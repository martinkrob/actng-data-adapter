package cz.centropol.actng.dataadapter.dst.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "qm_worktop")
public class QMWorktop extends AbstractEntity {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "note", length = 1000)
    private String note;

    @Column(name = "owner", nullable = false, length = 50)
    private String owner;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
