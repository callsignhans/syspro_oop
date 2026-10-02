package assessment;

/**
 * Assessment type match qualification paper in last semester.
 */
public final class QualificationPaperAssessment extends DifferentAssessment {
    private static final String  QUALIFICATION_PAPER = "Квалификационная работа";

    public QualificationPaperAssessment(int grade) {
        super(QUALIFICATION_PAPER, MAX_SEMESTER, grade);
    }

    @Override
    public boolean getSatisfyFreeEducation() {
        return false;
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
