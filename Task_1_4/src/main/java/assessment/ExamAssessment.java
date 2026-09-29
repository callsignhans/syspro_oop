package assessment;

public class ExamAssessment extends DifferentAssessment {

    public ExamAssessment(String name, int semester, int grade) {
        super(name, semester, grade);
    }

    @Override
    public boolean GetSatisfyFreeStudy() {
        return grade >= 4;
    }
}
