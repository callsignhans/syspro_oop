package assessment;

public class DifferentCreditAssessment extends DifferentAssessment {

    public DifferentCreditAssessment(String name, int semester, int grade) {
        super(name, semester, grade);
    }

    @Override
    public boolean GetSatisfyFreeStudy() {
        return grade >= 3;
    }
}
