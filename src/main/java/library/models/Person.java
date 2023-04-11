package library.models;

public class Person {
    private int id;
    private String FIO;
    private int yearOfBirth;

    public String getFIO() {
        return FIO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
