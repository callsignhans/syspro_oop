package assessment;

public class BinaryAssessment extends BaseAssessment {

    boolean result;

    BinaryAssessment(String name, int semester, boolean result) {
        super(name, semester);
        this.result = result;
    }

    @Override
    public boolean GetImpactDiplomaHonor() {
        return false;
    }

    @Override
    public boolean GetSatisfyFreeStudy() {
        return result;
    }

    @Override
    public boolean GetSatisfyRaiseGrant() {
        return result;
    }

    @Override
    public boolean GetSatisfyBaseGrant() {
        return result;
    }

}
