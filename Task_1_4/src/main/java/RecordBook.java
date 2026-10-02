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

    Double GetMediumGrade() {
        var result = assessments.stream().filter(obj -> obj instanceof DifferentAssessment)
                .collect(Collectors.toMap(Assessment::GetName, obj -> obj,
                (obj1, obj2) -> obj1.GetSemester() < obj2.GetSemester() ? obj2 : obj1)).values();
        return result.stream().mapToInt(obj -> ((DifferentAssessment)obj).GetGrade()).average()
                .orElse(0);
    }

    boolean GetStatusFreeEducation() {
        return freeEducationStatus;
    }

    boolean GetPossibleTransferToFreeEducation() {
        if (freeEducationStatus) {
            return true;
        }
        var nowSemester = assessments.stream().mapToInt(Assessment::GetSemester).max().orElse(0);
        if (nowSemester == 0) {
            return false;
        }
        return assessments.stream().filter(obj -> (obj.GetSemester() == nowSemester ||
                obj.GetSemester() == nowSemester - 1)).allMatch(Assessment::GetSatisfyFreeEducation);
    }

    boolean GetStatusDiplomaHonor() {
        var satisfyMediumGrade = this.GetMediumGrade() >= 4.75;
        var gradeOfQualificationPaper = assessments.stream()
                .filter(obj -> obj instanceof QualificationPaperAssessment)
                .mapToInt(obj -> ((QualificationPaperAssessment)obj).GetGrade())
                .allMatch(obj -> obj == 5);
        var haveNotThree = assessments.stream().filter(Assessment::GetImpactDiplomaHonor)
                .allMatch( obj -> ((DifferentAssessment)obj).GetGrade() > 3);
        return satisfyMediumGrade && gradeOfQualificationPaper && haveNotThree;
    }

    boolean GetStatusBaseGrand() {
        var nowSemester = assessments.stream().mapToInt(Assessment::GetSemester).max().orElse(0);
        return assessments.stream().filter(obj -> obj.GetSemester() == nowSemester)
                .allMatch(Assessment::GetSatisfyBaseGrant);
    }

    boolean GetStatusRaiseGrand() {
        var nowSemester = assessments.stream().mapToInt(Assessment::GetSemester).max().orElse(0);
        return assessments.stream().filter(obj -> obj.GetSemester() == nowSemester)
                .allMatch(Assessment::GetSatisfyRaiseGrant);
    }

    void AddAssessment(Assessment newGrade) {
        if (newGrade == null) {
            throw new NullPointerException("Argument 'newGrade' must isn't null");
        }
        assessments.add(newGrade);
    }

    void TransferToFreeEducation() {
        if (GetPossibleTransferToFreeEducation()) {
            freeEducationStatus = true;
        }
        else {
            throw new IllegalStateException("grades not satisfy of transfer to free education");
        }
    }

}
