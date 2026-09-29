package assessment;

public class QualificationPaperAssessment extends DifferentAssessment {
    private static final String  QUALIFICATION_PAPER = "Квалификационная работа";

    public QualificationPaperAssessment(int grade) {
        super(QUALIFICATION_PAPER, MAX_SEMESTER, grade);
    }

    @Override
    public boolean GetSatisfyFreeStudy() {
        return false;
        //throw new IllegalStateException("Student get grade of qualification paper on last semester. Student can't ");
    }

    @Override
    public boolean GetSatisfyRaiseGrant() {
        return false;
    }

    @Override
    public boolean GetSatisfyBaseGrant() {
        return false;
    }
}
