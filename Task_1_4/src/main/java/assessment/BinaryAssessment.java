package assessment;

public class BinaryAssessment extends BaseAssessment {

    boolean result;

    public BinaryAssessment(String name, int semester, boolean result) {
        super(name, semester);
        this.result = result;
    }

    @Override
    public boolean getImpactDiplomaHonor() {
        return false;
    }

    @Override
    public boolean getSatisfyFreeEducation() {
        return result;
    }

    @Override
    public boolean getSatisfyRaiseGrant() {
        return result;
    }

    @Override
    public boolean getSatisfyBaseGrant() {
        return result;
    }

}
