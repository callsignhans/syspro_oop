package assessment;

/**
 * Describe base interface assessment.
 */
public interface Assessment {

    /**
     *  It describes impact of academic course to have diploma with honor.
     * @return true - impact, false - don't impact
     */
    boolean getImpactDiplomaHonor();

    /**
     *  It satisfies of grade academic course to free model study.
     * @return true - satisfy, false - don't satisfy
     */
    boolean getSatisfyFreeEducation();

    /**
     *  It satisfies of grade academic course to raise grant.
     * @return true - satisfy, false - don't satisfy
     */
    boolean getSatisfyRaiseGrant();

    /**
     *  It satisfies of grade academic course to base grant.
     * @return true - satisfy, false - don't satisfy
     */
    boolean getSatisfyBaseGrant();

    /**
     * Return semester of grade.
     * @return number semester
     */
    int getSemester();

    /**
     * Return name of grade.
     * @return name of grade
     */
    String getName();

}
