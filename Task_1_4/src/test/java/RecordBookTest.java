import assessment.Assessment;
import assessment.ExamAssessment;
import assessment.DifferentCreditAssessment;
import assessment.BinaryAssessment;
import assessment.QualificationPaperAssessment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class RecordBookTest {

    @Nested
    @DisplayName("Проверка конструкторов и базовых методов")
    class BaseTests {

        @Test
        @DisplayName("Конструктор без списка оценок инициализирует пустую зачетку")
        void shouldInitializeEmptyRecordBook() {
            RecordBook recordBook = new RecordBook(101, false);

            Assertions.assertFalse(recordBook.getStatusFreeEducation());
            Assertions.assertEquals(0.0, recordBook.getMediumGrade());
        }

        @Test
        @DisplayName("Конструктор со списком оценок корректно их переносит")
        void shouldInitializeWithAssessments() {
            ArrayList<Assessment> list = new ArrayList<>(Arrays.asList(
                    new ExamAssessment("Математика", 1, 4),
                    new ExamAssessment("История", 1, 5)
            ));
            RecordBook recordBook = new RecordBook(102, true, list);

            Assertions.assertTrue(recordBook.getStatusFreeEducation());
            Assertions.assertEquals(4.5, recordBook.getMediumGrade());
        }

        @Test
        @DisplayName("AddAssessment выбрасывает NullPointerException, если передана null-ссылка")
        void shouldThrowExceptionWhenAddNullAssessment() {
            RecordBook recordBook = new RecordBook(103, false);

            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> recordBook.addAssessment(null)
            );
            Assertions.assertTrue(exception.getMessage()
                .contains("Argument 'newGrade' must isn't null"));
        }

    }

    @Nested
    @DisplayName("Проверка логики перевода на бюджет (TransferToFreeEducation)")
    class NewTransferToFreeEducationTests {

        @Test
        @DisplayName("Успешный перевод на бюджет, если условия выполнены")
        void shouldTransferToFreeEducationWhenConditionsAreMet() {
            RecordBook recordBook = new RecordBook(104, false);

            recordBook.addAssessment(new ExamAssessment("Программирование", 1, 4));
            recordBook.addAssessment(new DifferentCreditAssessment("Практика", 1, 4));

            Assertions.assertDoesNotThrow(() -> recordBook.transferToFreeEducation());

            Assertions.assertTrue(recordBook.getStatusFreeEducation());
        }

        @Test
        @DisplayName("Выбрасывает IllegalStateException, если условия перевода не выполнены")
        void shouldThrowExceptionWhenConditionsAreNotMet() {
            RecordBook recordBook = new RecordBook(105, false);

            // Добавляем плохую оценку (тройку за экзамен), из-за которой перевод невозможен
            recordBook.addAssessment(new ExamAssessment("Высшая математика", 1, 3));

            // Проверяем, что выбрасывается правильное исключение с ожидаемым текстом
            IllegalStateException exception = Assertions.assertThrows(
                    IllegalStateException.class,
                    () -> recordBook.transferToFreeEducation()
            );

            Assertions.assertTrue(exception.getMessage()
                .contains("grades not satisfy of transfer to free education"));
            // Статус должен остаться прежним (false)
            Assertions.assertFalse(recordBook.getStatusFreeEducation());
        }

        @Test
        @DisplayName("Успешный перевод, если студент уже находится на бесплатном обучении")
        void shouldDoNothingButStayTrueIfAlreadyFreeEducation() {
            // Если студент уже на бюджете, GetPossibleTransferToFreeEducation() всегда возвращает true
            RecordBook recordBook = new RecordBook(106, true);

            Assertions.assertDoesNotThrow(() -> recordBook.transferToFreeEducation());
            Assertions.assertTrue(recordBook.getStatusFreeEducation());
        }
    }


    @Nested
    @DisplayName("Проверка подсчета среднего балла (GetMediumGrade)")
    class MediumGradeTests {

        @Test
        @DisplayName("Возвращает 0, если в зачетке нет оценок с баллами")
        void shouldReturnZeroForEmptyOrBinaryOnly() {
            RecordBook recordBook = new RecordBook(1, true);
            recordBook.addAssessment(new BinaryAssessment("Физкультура", 1, true));

            Assertions.assertEquals(0.0, recordBook.getMediumGrade());
        }

        @Test
        @DisplayName("Если предмет сдавался несколько раз, "
            + "учитывается только оценка за последний семестр")
        void shouldTakeOnlyLatestSemesterForSameSubject() {
            RecordBook recordBook = new RecordBook(1, true);
            // Математика в 1 семестре на 3, во 2 семестре на 5
            recordBook.addAssessment(new ExamAssessment("Математика", 1, 3));
            recordBook.addAssessment(new ExamAssessment("Математика", 2, 5));
            recordBook.addAssessment(new ExamAssessment("Физика", 1, 4));

            // Средний балл должен считаться по Математике(5) и Физике(4) -> (5 + 4) / 2 = 4.5
            Assertions.assertEquals(4.5, recordBook.getMediumGrade());
        }
    }

    @Nested
    @DisplayName("Проверка возможности перевода на бюджет (GetPossibleTransferToFreeEducation)")
    class TransferTests {

        @Test
        @DisplayName("Возвращает true, если студент уже учится на бюджете")
        void shouldReturnTrueIfAlreadyFreeEducation() {
            RecordBook recordBook = new RecordBook(1, true);
            Assertions.assertTrue(recordBook.getPossibleTransferToFreeEducation());
        }

        @Test
        @DisplayName("Возвращает false, если у платного студента вообще нет оценок")
        void shouldReturnFalseIfNoAssessments() {
            RecordBook recordBook = new RecordBook(1, false);
            Assertions.assertFalse(recordBook.getPossibleTransferToFreeEducation());
        }

        @Test
        @DisplayName("Возвращает true, если за последние два семестра"
            +  " все оценки удовлетворяют условиям")
        void shouldReturnTrueIfLastTwoSemestersAreSatisfactory() {
            RecordBook recordBook = new RecordBook(1, false);
            recordBook.addAssessment(new ExamAssessment("Программирование", 3, 4));
            recordBook.addAssessment(new DifferentCreditAssessment("Практика", 2, 3));
            recordBook.addAssessment(new ExamAssessment("История", 1, 3));

            Assertions.assertTrue(recordBook.getPossibleTransferToFreeEducation());
        }

        @Test
        @DisplayName("Возвращает false, если хотя бы одна оценка"
            + " за последние два семестра плохая")
        void shouldReturnFalseIfAnyLastTwoSemestersUnsatisfactory() {
            RecordBook recordBook = new RecordBook(1, false);
            recordBook.addAssessment(new ExamAssessment("Программирование", 3, 3));
            recordBook.addAssessment(new DifferentCreditAssessment("Практика", 2, 4));

            Assertions.assertFalse(recordBook.getPossibleTransferToFreeEducation());
        }
    }

    @Nested
    @DisplayName("Проверка условий диплома с отличием (GetStatusDiplomaHonor)")
    class DiplomaHonorTests {

        @Test
        @DisplayName("Возвращает true при выполнении всех условий"
            + " (балл > 4.75, квал. работа = 5, нет троек)")
        void shouldReturnTrueWhenAllConditionsMet() {
            RecordBook recordBook = new RecordBook(1, true);
            recordBook.addAssessment(new ExamAssessment("Предмет 1", 1, 5));
            recordBook.addAssessment(new ExamAssessment("Предмет 2", 1, 5));
            recordBook.addAssessment(new ExamAssessment("Предмет 3", 1, 5));
            recordBook.addAssessment(new ExamAssessment("Предмет 4", 1, 4));

            recordBook.addAssessment(new QualificationPaperAssessment(5));
            recordBook.addAssessment(new BinaryAssessment("Физкультура", 1, true));

            Assertions.assertTrue(recordBook.getStatusDiplomaHonor());
        }

        @Test
        @DisplayName("Возвращает false, если средний балл ниже 4.75")
        void shouldReturnFalseWhenMediumGradeIsLow() {
            RecordBook recordBook = new RecordBook(1, true);
            recordBook.addAssessment(new ExamAssessment("Предмет 1", 1, 4));
            recordBook.addAssessment(new ExamAssessment("Предмет 2", 1, 5));
            recordBook.addAssessment(new QualificationPaperAssessment(5));

            Assertions.assertFalse(recordBook.getStatusDiplomaHonor());
        }

        @Test
        @DisplayName("Возвращает false, если за квалификационную работу оценка ниже 5")
        void shouldReturnFalseWhenQualificationPaperIsNotFive() {
            RecordBook recordBook = new RecordBook(1, true);
            recordBook.addAssessment(new ExamAssessment("Предмет 1", 1, 5));
            recordBook.addAssessment(new QualificationPaperAssessment(4)); // Не отлично

            Assertions.assertFalse(recordBook.getStatusDiplomaHonor());
        }

        @Test
        @DisplayName("Возвращает false, если есть хотя бы одна тройка"
            + " по влияющему на диплом предмету")
        void shouldReturnFalseWhenHasThree() {
            RecordBook recordBook = new RecordBook(1, true);
            // Наберем много пятерок для высокого среднего балла
            for (int i = 0; i < 10; i++) {
                recordBook.addAssessment(new ExamAssessment("Предмет " + i, 1, 5));
            }
            recordBook.addAssessment(new ExamAssessment("Проблемный предмет", 1, 3));
            recordBook.addAssessment(new QualificationPaperAssessment(5));

            Assertions.assertFalse(recordBook.getStatusDiplomaHonor());
        }
    }

    @Nested
    @DisplayName("Проверка условий обычной и повышенной стипендии (Base/Raise Grant)")
    class GrantTests {

        @Test
        @DisplayName("Обычная стипендия выдается, если все предметы текущего семестра"
            + " закрыты на базовый грант")
        void testGetStatusBaseGrant() {
            RecordBook recordBook = new RecordBook(1, true);
            recordBook.addAssessment(new ExamAssessment("Математика", 2, 4));
            recordBook.addAssessment(new DifferentCreditAssessment("История", 2, 4));
            recordBook.addAssessment(new ExamAssessment("Физика", 1, 3));

            Assertions.assertTrue(recordBook.getStatusBaseGrand());

            recordBook.addAssessment(new ExamAssessment("Химия", 2, 3));
            Assertions.assertFalse(recordBook.getStatusBaseGrand());
        }

        @Test
        @DisplayName("Повышенная стипендия выдается, если все предметы текущего семестра"
            + " закрыты на только 5)")
        void testGetStatusRaiseGrant() {
            RecordBook recordBook = new RecordBook(1, true);
            recordBook.addAssessment(new ExamAssessment("Математика", 2, 5));
            recordBook.addAssessment(new DifferentCreditAssessment("История", 2, 5));
            Assertions.assertTrue(recordBook.getStatusRaiseGrand());

            recordBook.addAssessment(new ExamAssessment("Химия", 2, 4));
            Assertions.assertFalse(recordBook.getStatusRaiseGrand());
        }
    }
}
