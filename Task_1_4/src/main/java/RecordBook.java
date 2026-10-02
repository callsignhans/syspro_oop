import assessment.Assessment;
import assessment.DifferentAssessment;
import assessment.QualificationPaperAssessment;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RecordBook {

    int id;
    boolean freeEducationStatus;

    ArrayList<Assessment> assessments;

    public RecordBook(int id, boolean freeEducationStatus){
        this.id = id;
        this.freeEducationStatus = freeEducationStatus;
        assessments = new ArrayList<>();
    }

    public RecordBook(int id, boolean freeEducationStatus, ArrayList<Assessment> assessments){
        this(id, freeEducationStatus);
        this.assessments.addAll(assessments);
    }

    Double getMediumGrade() {
        var result = assessments.stream().filter(obj -> obj instanceof DifferentAssessment)
                .collect(Collectors.toMap(Assessment::getName, obj -> obj,
                (obj1, obj2) -> obj1.getSemester() < obj2.getSemester() ? obj2 : obj1)).values();
        return result.stream().mapToInt(obj -> ((DifferentAssessment)obj).GetGrade()).average()
                .orElse(0);
    }

    boolean getStatusFreeEducation() {
        return freeEducationStatus;
    }

    boolean getPossibleTransferToFreeEducation() {
        if (freeEducationStatus) {
            return true;
        }
        var nowSemester = assessments.stream().mapToInt(Assessment::getSemester).max().orElse(0);
        if (nowSemester == 0) {
            return false;
        }
        return assessments.stream().filter(obj -> (obj.getSemester() == nowSemester ||
                obj.getSemester() == nowSemester - 1)).allMatch(Assessment::getSatisfyFreeEducation);
    }

    boolean getStatusDiplomaHonor() {
        var satisfyMediumGrade = this.getMediumGrade() >= 4.75;
        var gradeOfQualificationPaper = assessments.stream()
                .filter(obj -> obj instanceof QualificationPaperAssessment)
                .mapToInt(obj -> ((QualificationPaperAssessment)obj).GetGrade())
                .allMatch(obj -> obj == 5);
        var haveNotThree = assessments.stream().filter(Assessment::getImpactDiplomaHonor)
                .allMatch( obj -> ((DifferentAssessment)obj).GetGrade() > 3);
        return satisfyMediumGrade && gradeOfQualificationPaper && haveNotThree;
    }

    boolean getStatusBaseGrand() {
        var nowSemester = assessments.stream().mapToInt(Assessment::getSemester).max().orElse(0);
        return assessments.stream().filter(obj -> obj.getSemester() == nowSemester)
                .allMatch(Assessment::getSatisfyBaseGrant);
    }

    boolean getStatusRaiseGrand() {
        var nowSemester = assessments.stream().mapToInt(Assessment::getSemester).max().orElse(0);
        return assessments.stream().filter(obj -> obj.getSemester() == nowSemester)
                .allMatch(Assessment::getSatisfyRaiseGrant);
    }

    void addAssessment(Assessment newGrade) {
        if (newGrade == null) {
            throw new NullPointerException("Argument 'newGrade' must isn't null");
        }
        assessments.add(newGrade);
    }

    void transferToFreeEducation() {
        if (getPossibleTransferToFreeEducation()) {
            freeEducationStatus = true;
        }
        else {
            throw new IllegalStateException("grades not satisfy of transfer to free education");
        }
    }

}
