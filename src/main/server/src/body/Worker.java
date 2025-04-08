package body;

import java.time.LocalDate;
/**
 * Model of Worker, contains getters/setter for fields of closes and methods to change fields
 */
public class Worker {

    private Long id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private Integer salary;
    private LocalDate endDate;
    private Position position;
    private Status status;
    private Organization organization;

    public Worker(Long id, String name, Coordinates coordinates, Integer salary, Status status, Organization organization) {
        this.creationDate = LocalDate.now();
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        setSalary(salary);
        setStatus(status);
        setOrganization(organization);
    }

    public Worker() {
    }



    public void updateElement(Worker worker) {
        setName(worker.getName());
        setCoordinates(worker.getCoordinates());
        setCreationDate(worker.getCreationDate());
        setSalary(worker.getSalary());
        setPosition(worker.getPosition());
        setOrganization(worker.getOrganization());
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setId(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Зарплата не может быть null и должна быть больше 0.");
        }
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть null или пустым.");
        }
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Координаты не могут быть null.");
        }
        this.coordinates = coordinates;
    }

    public void setSalary(Integer salary) {
        if (salary == null || salary <= 0) {
            throw new IllegalArgumentException("Зарплата не может быть null и должна быть больше 0.");
        }
        this.salary = salary;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate; // Может быть null
    }

    public void setPosition(Position position) {
        this.position = position; // Может быть null
    }

    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Статус не может быть null.");
        }
        this.status = status;
    }

    public void setOrganization(Organization organization) {
        if (organization == null) {
            throw new IllegalArgumentException("Организация не может быть null.");
        }
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Integer getSalary() {
        return salary;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Position getPosition() {
        return position;
    }

    public Status getStatus() {
        return status;
    }

    public Organization getOrganization() {
        return organization;
    }

    @Override
    public String toString() {
        return "Worker [" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", salary=" + salary +
                ", endDate=" + endDate +
                ", position=" + position +
                ", status=" + status +
                ", organization=" + organization +
                ']';
    }


}
