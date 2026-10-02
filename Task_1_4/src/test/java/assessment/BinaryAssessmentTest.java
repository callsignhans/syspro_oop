package assessment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BinaryAssessmentTest {

    @Nested
    @DisplayName("Проверка конструктора и валидации базовых параметров")
    class ConstructorValidation {

        @ParameterizedTest
        @ValueSource(booleans = {true, false})
        @DisplayName("Успешное создание объекта с валидными параметрами")
        void shouldCreateInstanceWithValidParameters(boolean resultValue) {
            BinaryAssessment assessment
                = new BinaryAssessment("Физическая культура", 2, resultValue);

            Assertions.assertEquals("Физическая культура", assessment.GetName());
            Assertions.assertEquals(2, assessment.GetSemester());
            Assertions.assertEquals(resultValue, assessment.result);
        }

        @Test
        @DisplayName("Бросает IllegalArgumentException при пустом имени")
        void shouldThrowExceptionWhenNameIsEmpty() {
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new BinaryAssessment("", 1, true)
            );
            Assertions.assertTrue(exception.getMessage().contains("Name must isn't empty"));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1, 9})
        @DisplayName("Бросает IllegalArgumentException при невалидном семестре")
        void shouldThrowExceptionWhenSemesterIsInvalid(int invalidSemester) {
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new BinaryAssessment("Культурология", invalidSemester, true)
            );
        }
    }

    @Nested
    @DisplayName("Проверка бизнес-логики в зависимости от результата (зачтено/не зачтено)")
    class BusinessLogic {

        @Test
        @DisplayName("Все методы удовлетворения условий возвращают true, если результат true")
        void shouldReturnTrueWhenResultIsTrue() {
            BinaryAssessment assessment
                = new BinaryAssessment("Информатика", 1, true);

            Assertions.assertTrue(assessment.GetSatisfyFreeEducation());
            Assertions.assertTrue(assessment.GetSatisfyRaiseGrant());
            Assertions.assertTrue(assessment.GetSatisfyBaseGrant());
        }

        @Test
        @DisplayName("Все методы удовлетворения условий возвращают false, если результат false")
        void shouldReturnFalseWhenResultIsFalse() {
            BinaryAssessment assessment
                = new BinaryAssessment("Информатика", 1, false);

            Assertions.assertFalse(assessment.GetSatisfyFreeEducation());
            Assertions.assertFalse(assessment.GetSatisfyRaiseGrant());
            Assertions.assertFalse(assessment.GetSatisfyBaseGrant());
        }

        @ParameterizedTest
        @ValueSource(booleans = {true, false})
        @DisplayName("GetImpactDiplomaHonor всегда возвращает false независимо от результата")
        void getImpactDiplomaHonor_ShouldAlwaysReturnFalse(boolean resultValue) {
            BinaryAssessment assessment
                = new BinaryAssessment("Информатика", 1, resultValue);

            Assertions.assertFalse(assessment.GetImpactDiplomaHonor());
        }
    }
}
