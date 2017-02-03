package edu.cpp.cs499.p2.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "major")
    private String major;

    @ManyToMany
    @JoinTable(name = "student_take",
               joinColumns = @JoinColumn(name="students_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="takes_id", referencedColumnName="ID"))
    private Set<Classes> takes = new HashSet<>();

    @ManyToOne
    private Lecturer lecturer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Student name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public Student age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }

    public Student major(String major) {
        this.major = major;
        return this;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Set<Classes> getTakes() {
        return takes;
    }

    public Student takes(Set<Classes> classes) {
        this.takes = classes;
        return this;
    }

    public Student addTake(Classes classes) {
        takes.add(classes);
        return this;
    }

    public Student removeTake(Classes classes) {
        takes.remove(classes);
        return this;
    }

    public void setTakes(Set<Classes> classes) {
        this.takes = classes;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public Student lecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
        return this;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        if (student.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", age='" + age + "'" +
            ", major='" + major + "'" +
            '}';
    }
}
