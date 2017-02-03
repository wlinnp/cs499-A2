package edu.cpp.cs499.p2.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Classes.
 */
@Entity
@Table(name = "classes")
public class Classes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lecturer")
    private String lecturer;

    @Column(name = "major")
    private String major;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Classes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLecturer() {
        return lecturer;
    }

    public Classes lecturer(String lecturer) {
        this.lecturer = lecturer;
        return this;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getMajor() {
        return major;
    }

    public Classes major(String major) {
        this.major = major;
        return this;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Classes classes = (Classes) o;
        if (classes.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, classes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Classes{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", lecturer='" + lecturer + "'" +
            ", major='" + major + "'" +
            '}';
    }
}
