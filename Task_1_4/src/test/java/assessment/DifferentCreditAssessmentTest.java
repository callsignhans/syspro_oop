package assessment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class DifferentCreditAssessmentTest {

    @Nested
    @DisplayName("Проверка конструктора и валидации параметров")
    class ConstructorValidation {

        @Test
        @DisplayName("Успешное создание объекта с валидными параметрами")
        void shouldCreateInstanceWithValidParameters() {
            DifferentCreditAssessment assessment =
                new DifferentCreditAssessment("Дифференцированный зачет", 4, 4);

            Assertions.assertEquals("Дифференцированный зачет", assessment.GetName());
            Assertions.assertEquals(4, assessment.GetSemester());
            Assertions.assertEquals(4, assessment.GetGrade());
        }

        @Test
        @DisplayName("Бросает IllegalArgumentException при пустом имени")
        void shouldThrowExceptionWhenNameIsEmpty() {
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new DifferentCreditAssessment("", 2, 3)
            );
            Assertions.assertTrue(exception.getMessage().contains("Name must isn't empty"));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -2, 10})
        @DisplayName("Бросает IllegalArgumentException при невалидном семестре")
        void shouldThrowExceptionWhenSemesterIsInvalid(int invalidSemester) {
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new DifferentCreditAssessment("Философия", invalidSemester, 3)
            );
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 7, -5})
        @DisplayName("Бросает IllegalArgumentException при невалидной оценке")
        void shouldThrowExceptionWhenGradeIsInvalid(int invalidGrade) {
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new DifferentCreditAssessment("Философия", 2, invalidGrade)
            );
        }
    }

    @Nested
    @DisplayName("Проверка бизнес-логики (удовлетворение условиям обучения и стипендий)")
    class BusinessLogic {

        @ParameterizedTest
        @CsvSource({
            "2, false",
            "3, true",
            "4, true",
            "5, true"
        })
        @DisplayName("GetSatisfyFreeEducation возвращает true для оценок >= 3")
        void testGetSatisfyFreeEducation(int grade, boolean expectedResult) {
            DifferentCreditAssessment assessment = new DifferentCreditAssessment("Практика", 2, grade);
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
            DifferentCreditAssessment assessment = new DifferentCreditAssessment("Практика", 2, grade);
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
            DifferentCreditAssessment assessment = new DifferentCreditAssessment("Практика", 2, grade);
            Assertions.assertEquals(expectedResult, assessment.GetSatisfyBaseGrant());
        }

        @Test
        @DisplayName("GetImpactDiplomaHonor всегда возвращает true" +
            " (наследование от DifferentAssessment)")
        void getImpactDiplomaHonor_ShouldReturnTrue() {
            DifferentCreditAssessment assessment = new DifferentCreditAssessment("Практика", 2, 4);
            Assertions.assertTrue(assessment.GetImpactDiplomaHonor());
        }
    }
}
