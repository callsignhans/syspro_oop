package assessment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ExamAssessmentTest {

    @Nested
    @DisplayName("Проверка конструктора и валидации параметров")
    class ConstructorValidation {

        @Test
        @DisplayName("Успешное создание объекта с валидными параметрами")
        void shouldCreateInstanceWithValidParameters() {
            ExamAssessment assessment = new ExamAssessment("Математический анализ", 3, 5);

            Assertions.assertEquals("Математический анализ", assessment.GetName());
            Assertions.assertEquals(3, assessment.GetSemester());
            Assertions.assertEquals(5, assessment.GetGrade());
        }

        @Test
        @DisplayName("Бросает IllegalArgumentException при пустом имени")
        void shouldThrowExceptionWhenNameIsEmpty() {
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new ExamAssessment("", 1, 4)
            );
            Assertions.assertTrue(exception.getMessage().contains("Name must isn't empty"));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1, 9})
        @DisplayName("Бросает IllegalArgumentException при невалидном семестре")
        void shouldThrowExceptionWhenSemesterIsInvalid(int invalidSemester) {
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new ExamAssessment("Физика", invalidSemester, 4)
            );
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 6, -3})
        @DisplayName("Бросает IllegalArgumentException при невалидной оценке")
        void shouldThrowExceptionWhenGradeIsInvalid(int invalidGrade) {
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new ExamAssessment("Физика", 1, invalidGrade)
            );
        }
    }

    @Nested
    @DisplayName("Проверка бизнес-логики (стипендии и оценки)")
    class BusinessLogic {

        @ParameterizedTest
        @CsvSource({
            "2, false",
            "3, false",
            "4, true",
            "5, true"
        })
        @DisplayName("GetSatisfyFreeEducation возвращает true только для оценок >= 4")
        void testGetSatisfyFreeEducation(int grade, boolean expectedResult) {
            ExamAssessment assessment = new ExamAssessment("История", 1, grade);
            Assertions.assertEquals(expectedResult, assessment.GetSatisfyFreeEducation());
        }

        @ParameterizedTest
        @CsvSource({
            "2, false",
            "3, false",
            "4, false",
            "5, true"
        })
        @DisplayName("GetSatisfyRaiseGrant возвращает true только для оценки 5" +
            " (наследование от DifferentAssessment)")
        void testGetSatisfyRaiseGrant(int grade, boolean expectedResult) {
            ExamAssessment assessment = new ExamAssessment("История", 1, grade);
            Assertions.assertEquals(expectedResult, assessment.GetSatisfyRaiseGrant());
        }

        @ParameterizedTest
        @CsvSource({
            "2, false",
            "3, false",
            "4, true",
            "5, true"
        })
        @DisplayName("GetSatisfyBaseGrant возвращает true для оценок >= 4" +
            " (наследование от DifferentAssessment)")
        void testGetSatisfyBaseGrant(int grade, boolean expectedResult) {
            ExamAssessment assessment = new ExamAssessment("История", 1, grade);
            Assertions.assertEquals(expectedResult, assessment.GetSatisfyBaseGrant());
        }

        @Test
        @DisplayName("GetImpactDiplomaHonor всегда возвращает true" +
            " (наследование от DifferentAssessment)")
        void getImpactDiplomaHonor_ShouldReturnTrue() {
            ExamAssessment assessment = new ExamAssessment("История", 1, 4);
            Assertions.assertTrue(assessment.GetImpactDiplomaHonor());
        }
    }
}
