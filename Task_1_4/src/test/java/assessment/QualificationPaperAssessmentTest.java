package assessment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QualificationPaperAssessmentTest {

    @Nested
    @DisplayName("Проверка конструктора и валидации оценки")
    class ConstructorValidation {

        @ParameterizedTest
        @ValueSource(ints = {2, 3, 4, 5})
        @DisplayName("Успешное создание объекта с валидными оценками")
        void shouldCreateInstanceWithValidGrades(int validGrade) {
            QualificationPaperAssessment assessment = new QualificationPaperAssessment(validGrade);

            Assertions.assertEquals("Квалификационная работа", assessment.GetName());
            Assertions.assertEquals(BaseAssessment.MAX_SEMESTER, assessment.GetSemester());
            Assertions.assertEquals(validGrade, assessment.GetGrade());
        }

        @Test
        @DisplayName("Бросает IllegalArgumentException, если оценка отрицательная")
        void shouldThrowExceptionWhenGradeIsNegative() {
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new QualificationPaperAssessment(-1)
            );
            Assertions.assertTrue(exception.getMessage().contains("Argument 'grade' must is more zero"));
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 6})
        @DisplayName("Бросает IllegalArgumentException, если оценка вне диапазона [2;5]")
        void shouldThrowExceptionWhenGradeIsOutOfRange(int invalidGrade) {
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new QualificationPaperAssessment(invalidGrade)
            );
            Assertions.assertTrue(exception.getMessage().contains("Argument 'grade' must belong range [2;5]"));
        }
    }

    @Nested
    @DisplayName("Проверка бизнес-логики и переопределенных методов")
    class BusinessLogic {

        @ParameterizedTest
        @ValueSource(ints = {2, 3, 4, 5})
        @DisplayName("GetSatisfyFreeEducation всегда возвращает false независимо от оценки")
        void getSatisfyFreeEducation_ShouldAlwaysReturnFalse(int grade) {
            QualificationPaperAssessment assessment = new QualificationPaperAssessment(grade);
            Assertions.assertFalse(assessment.GetSatisfyFreeEducation());
        }

        @ParameterizedTest
        @ValueSource(ints = {2, 3, 4, 5})
        @DisplayName("GetSatisfyRaiseGrant всегда возвращает false (переопределено из родителя)")
        void getSatisfyRaiseGrant_ShouldAlwaysReturnFalse(int grade) {
            QualificationPaperAssessment assessment = new QualificationPaperAssessment(grade);
            Assertions.assertFalse(assessment.GetSatisfyRaiseGrant());
        }

        @ParameterizedTest
        @ValueSource(ints = {2, 3, 4, 5})
        @DisplayName("GetSatisfyBaseGrant всегда возвращает false (переопределено из родителя)")
        void getSatisfyBaseGrant_ShouldAlwaysReturnFalse(int grade) {
            QualificationPaperAssessment assessment = new QualificationPaperAssessment(grade);
            Assertions.assertFalse(assessment.GetSatisfyBaseGrant());
        }

        @ParameterizedTest
        @ValueSource(ints = {2, 3, 4, 5})
        @DisplayName("GetImpactDiplomaHonor всегда возвращает true (унаследовано от DifferentAssessment)")
        void getImpactDiplomaHonor_ShouldAlwaysReturnTrue(int grade) {
            QualificationPaperAssessment assessment = new QualificationPaperAssessment(grade);
            Assertions.assertTrue(assessment.GetImpactDiplomaHonor());
        }
    }
}
