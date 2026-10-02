package assessment;

/**
 * Abstract class implement logic of assessment with integer grade.
 */
public abstract class DifferentAssessment extends BaseAssessment {

    public static final int MIN_VALUE_GRADE = 2;
    public static final int MAX_VALUE_GRADE = 5;

    protected int grade;

    public DifferentAssessment(String name, int semester, int grade) {
        super(name, semester);
        if (grade < 0) {
            throw new IllegalArgumentException("Argument 'grade' must is more zero");
        }
        if (grade < MIN_VALUE_GRADE || grade > MAX_VALUE_GRADE) {
            throw new IllegalArgumentException("Argument 'grade' must belong range ["
                + MIN_VALUE_GRADE + ";" + MAX_VALUE_GRADE + "]");
        }
        this.grade = grade;
    }

    public int GetGrade() {
        return grade;
    }

    @Override
    public boolean getImpactDiplomaHonor() {
        return true;
    }

    @Override
    public boolean getSatisfyRaiseGrant() {
        return grade == 5;
    }

    @Override
    public boolean getSatisfyBaseGrant() {
        return grade >= 4;
    }
}
