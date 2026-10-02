import assessment.Assessment;
import assessment.DifferentAssessment;
import assessment.QualificationPaperAssessment;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class record book of mmf's student.
 */
public class RecordBook {

    int id;
    boolean freeEducationStatus;

    ArrayList<Assessment> assessments;

    public RecordBook(int id, boolean freeEducationStatus) {
        this.id = id;
        this.freeEducationStatus = freeEducationStatus;
        assessments = new ArrayList<>();
    }

    public RecordBook(int id, boolean freeEducationStatus, ArrayList<Assessment> assessments) {
        this(id, freeEducationStatus);
        this.assessments.addAll(assessments);
    }

    /**
     * Method calculate medium grade.
     * @return if record book isn't empty return medium grade, else return 0
     */
    Double getMediumGrade() {
        var result = assessments.stream().filter(obj -> obj instanceof DifferentAssessment)
            .collect(Collectors.toMap(Assessment::getName, obj -> obj,
                (obj1, obj2)
                    -> obj1.getSemester() < obj2.getSemester() ? obj2 : obj1)).values();
        return result.stream().mapToInt(obj -> ((DifferentAssessment) obj).getGrade())
            .average().orElse(0);
    }

    /**
     * Method define state-funded education status student.
     * @return if student is state-funded education return true, else return false
     */
    public boolean getStatusFreeEducation() {
        return freeEducationStatus;
    }

    /**
     * Method define possible transfer to state-funded education student in last semester.
     * @return if student study of state-funded education or satisfy precondition transfer
     * return true, else return false.
     */
    public boolean getPossibleTransferToFreeEducation() {
        if (freeEducationStatus) {
            return true;
        }
        var nowSemester = assessments.stream().mapToInt(Assessment::getSemester).max().orElse(0);
        if (nowSemester == 0) {
            return false;
        }
        return assessments.stream().filter(obj -> (obj.getSemester() == nowSemester
            || obj.getSemester() == nowSemester - 1)).allMatch(Assessment::getSatisfyFreeEducation);
    }

    /**
     * Method define earn diploma with honor status student in last semester.
     * @return return earn diploma with honor
     */
    public boolean getStatusDiplomaHonor() {
        var satisfyMediumGrade = this.getMediumGrade() >= 4.75;
        var gradeOfQualificationPaper = assessments.stream()
            .filter(obj -> obj instanceof QualificationPaperAssessment)
            .mapToInt(obj -> ((QualificationPaperAssessment) obj).getGrade())
            .allMatch(obj -> obj == 5);
        var haveNotThree = assessments.stream().filter(Assessment::getImpactDiplomaHonor)
            .allMatch(obj -> ((DifferentAssessment) obj).getGrade() > 3);
        return satisfyMediumGrade && gradeOfQualificationPaper && haveNotThree;
    }

    /**
     * Method define earn base grant status student in last semester.
     * @return return earn base grant in last semester
     */
    public boolean getStatusBaseGrand() {
        var nowSemester = assessments.stream().mapToInt(Assessment::getSemester).max().orElse(0);
        return assessments.stream().filter(obj -> obj.getSemester() == nowSemester)
                .allMatch(Assessment::getSatisfyBaseGrant);
    }

    /**
     * Method define earn raise grant status student in last semester.
     * @return return earn raise grant in last semester
     */
    public boolean getStatusRaiseGrand() {
        var nowSemester = assessments.stream().mapToInt(Assessment::getSemester).max().orElse(0);
        return assessments.stream().filter(obj -> obj.getSemester() == nowSemester)
                .allMatch(Assessment::getSatisfyRaiseGrant);
    }

    /**
     * Add in record book new result of assessment.
     * @param newGrade new result assessment
     */
    public void addAssessment(Assessment newGrade) {
        if (newGrade == null) {
            throw new NullPointerException("Argument 'newGrade' must isn't null");
        }
        assessments.add(newGrade);
    }

    /**
     * Check precondition transfer to state-funded education and carry transfer.
     */
    public void transferToFreeEducation() {
        if (getPossibleTransferToFreeEducation()) {
            freeEducationStatus = true;
        } else {
            throw new IllegalStateException("grades not satisfy of transfer to free education");
        }
    }

}
