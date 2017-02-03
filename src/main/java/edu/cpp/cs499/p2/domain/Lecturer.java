package edu.cpp.cs499.p2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lecturer.
 */
@Entity
@Table(name = "lecturer")
public class Lecturer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "lecturer")
    @JsonIgnore
    private Set<Student> trains = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "lecturer_teach",
               joinColumns = @JoinColumn(name="lecturers_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="teaches_id", referencedColumnName="ID"))
    private Set<Classes> teaches = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Lecturer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public Lecturer title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Student> getTrains() {
        return trains;
    }

    public Lecturer trains(Set<Student> students) {
        this.trains = students;
        return this;
    }

    public Lecturer addTrain(Student student) {
        trains.add(student);
        student.setLecturer(this);
        return this;
    }

    public Lecturer removeTrain(Student student) {
        trains.remove(student);
        student.setLecturer(null);
        return this;
    }

    public void setTrains(Set<Student> students) {
        this.trains = students;
    }

    public Set<Classes> getTeaches() {
        return teaches;
    }

    public Lecturer teaches(Set<Classes> classes) {
        this.teaches = classes;
        return this;
    }

    public Lecturer addTeach(Classes classes) {
        teaches.add(classes);
        return this;
    }

    public Lecturer removeTeach(Classes classes) {
        teaches.remove(classes);
        return this;
    }

    public void setTeaches(Set<Classes> classes) {
        this.teaches = classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lecturer lecturer = (Lecturer) o;
        if (lecturer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lecturer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Lecturer{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", title='" + title + "'" +
            '}';
    }
}
