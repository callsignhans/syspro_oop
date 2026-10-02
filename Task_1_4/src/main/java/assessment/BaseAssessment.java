package assessment;

public abstract class BaseAssessment implements Assessment {
    public static final int MAX_SEMESTER = 8;

    String name;
    int semester;

    public BaseAssessment(String name, int semester) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name must isn't empty");
        }
        if (semester <= 0) {
            throw new IllegalArgumentException("Argument 'semster' must is positive");
        }
        if (semester > MAX_SEMESTER) {
            throw new IllegalArgumentException("Argument 'semester' equal " + semester
                + ". Value argument must less " + MAX_SEMESTER);
        }
        this.name = name;
        this.semester = semester;
    }

    @Override
    public int getSemester() {
        return semester;
    }

    @Override
    public String getName() {
        return name;
    }

}
