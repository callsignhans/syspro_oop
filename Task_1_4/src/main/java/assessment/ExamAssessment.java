package assessment;

/**
 * Assessment type match exam.
 */
public final class ExamAssessment extends DifferentAssessment {

    public ExamAssessment(String name, int semester, int grade) {
        super(name, semester, grade);
    }

    @Override
    public boolean getSatisfyFreeEducation() {
        return grade >= 4;
    }
}
