package assessment;

public final class QualificationPaperAssessment extends DifferentAssessment {
    private static final String  QUALIFICATION_PAPER = "Квалификационная работа";

    public QualificationPaperAssessment(int grade) {
        super(QUALIFICATION_PAPER, MAX_SEMESTER, grade);
    }

    @Override
    public boolean getSatisfyFreeEducation() {
        return false;
        //throw new IllegalStateException("Student get grade of qualification paper on last
        // semester. Student can't ");
    }

    @Override
    public boolean getSatisfyRaiseGrant() {
        return false;
    }

    @Override
    public boolean getSatisfyBaseGrant() {
        return false;
    }
}
