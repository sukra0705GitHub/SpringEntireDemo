package Interface;

public interface StudentIF extends PersonIF {
    void goToSchool(String doWhat);

    void studyAt(String location);

    void course(String[] courseName);

    void readBook();
}
