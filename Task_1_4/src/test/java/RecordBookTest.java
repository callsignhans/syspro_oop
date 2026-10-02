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

            Assertions.assertFalse(recordBook.GetStatusFreeEducation());
            Assertions.assertEquals(0.0, recordBook.GetMediumGrade());
        }

        @Test
        @DisplayName("Конструктор со списком оценок корректно их переносит")
        void shouldInitializeWithAssessments() {
            ArrayList<Assessment> list = new ArrayList<>(Arrays.asList(
                    new ExamAssessment("Математика", 1, 4),
                    new ExamAssessment("История", 1, 5)
            ));
            RecordBook recordBook = new RecordBook(102, true, list);

            Assertions.assertTrue(recordBook.GetStatusFreeEducation());
            Assertions.assertEquals(4.5, recordBook.GetMediumGrade());
        }

        @Test
        @DisplayName("AddAssessment выбрасывает NullPointerException, если передана null-ссылка")
        void shouldThrowExceptionWhenAddNullAssessment() {
            RecordBook recordBook = new RecordBook(103, false);

            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> recordBook.AddAssessment(null)
            );
            Assertions.assertTrue(exception.getMessage().contains("Argument 'newGrade' must isn't null"));
        }

    }

    @Nested
    @DisplayName("Проверка логики перевода на бюджет (TransferToFreeEducation)")
    class NewTransferToFreeEducationTests {

        @Test
        @DisplayName("Успешный перевод на бюджет, если условия выполнены")
        void shouldTransferToFreeEducationWhenConditionsAreMet() {
            RecordBook recordBook = new RecordBook(104, false);

            recordBook.AddAssessment(new ExamAssessment("Программирование", 1, 4));
            recordBook.AddAssessment(new DifferentCreditAssessment("Практика", 1, 4));

            Assertions.assertDoesNotThrow(() -> recordBook.TransferToFreeEducation());

            Assertions.assertTrue(recordBook.GetStatusFreeEducation());
        }

        @Test
        @DisplayName("Выбрасывает IllegalStateException, если условия перевода не выполнены")
        void shouldThrowExceptionWhenConditionsAreNotMet() {
            RecordBook recordBook = new RecordBook(105, false);

            // Добавляем плохую оценку (тройку за экзамен), из-за которой перевод невозможен
            recordBook.AddAssessment(new ExamAssessment("Высшая математика", 1, 3));

            // Проверяем, что выбрасывается правильное исключение с ожидаемым текстом
            IllegalStateException exception = Assertions.assertThrows(
                    IllegalStateException.class,
                    () -> recordBook.TransferToFreeEducation()
            );

            Assertions.assertTrue(exception.getMessage().contains("grades not satisfy of transfer to free education"));
            // Статус должен остаться прежним (false)
            Assertions.assertFalse(recordBook.GetStatusFreeEducation());
        }

        @Test
        @DisplayName("Успешный перевод, если студент уже находится на бесплатном обучении")
        void shouldDoNothingButStayTrueIfAlreadyFreeEducation() {
            // Если студент уже на бюджете, GetPossibleTransferToFreeEducation() всегда возвращает true
            RecordBook recordBook = new RecordBook(106, true);

            Assertions.assertDoesNotThrow(() -> recordBook.TransferToFreeEducation());
            Assertions.assertTrue(recordBook.GetStatusFreeEducation());
        }
    }


    @Nested
    @DisplayName("Проверка подсчета среднего балла (GetMediumGrade)")
    class MediumGradeTests {

        @Test
        @DisplayName("Возвращает 0, если в зачетке нет оценок с баллами")
        void shouldReturnZeroForEmptyOrBinaryOnly() {
            RecordBook recordBook = new RecordBook(1, true);
            recordBook.AddAssessment(new BinaryAssessment("Физкультура", 1, true));

            Assertions.assertEquals(0.0, recordBook.GetMediumGrade());
        }

        @Test
        @DisplayName("Если предмет сдавался несколько раз, учитывается только оценка за последний семестр")
        void shouldTakeOnlyLatestSemesterForSameSubject() {
            RecordBook recordBook = new RecordBook(1, true);
            // Математика в 1 семестре на 3, во 2 семестре на 5
            recordBook.AddAssessment(new ExamAssessment("Математика", 1, 3));
            recordBook.AddAssessment(new ExamAssessment("Математика", 2, 5));
            recordBook.AddAssessment(new ExamAssessment("Физика", 1, 4));

            // Средний балл должен считаться по Математике(5) и Физике(4) -> (5 + 4) / 2 = 4.5
            Assertions.assertEquals(4.5, recordBook.GetMediumGrade());
        }
    }

    @Nested
    @DisplayName("Проверка возможности перевода на бюджет (GetPossibleTransferToFreeEducation)")
    class TransferTests {

        @Test
        @DisplayName("Возвращает true, если студент уже учится на бюджете")
        void shouldReturnTrueIfAlreadyFreeEducation() {
            RecordBook recordBook = new RecordBook(1, true);
            Assertions.assertTrue(recordBook.GetPossibleTransferToFreeEducation());
        }

        @Test
        @DisplayName("Возвращает false, если у платного студента вообще нет оценок")
        void shouldReturnFalseIfNoAssessments() {
            RecordBook recordBook = new RecordBook(1, false);
            Assertions.assertFalse(recordBook.GetPossibleTransferToFreeEducation());
        }

        @Test
        @DisplayName("Возвращает true, если за последние два семестра все оценки удовлетворяют условиям")
        void shouldReturnTrueIfLastTwoSemestersAreSatisfactory() {
            RecordBook recordBook = new RecordBook(1, false);
            recordBook.AddAssessment(new ExamAssessment("Программирование", 3, 4)); // удовлетворяет (>=4)
            recordBook.AddAssessment(new DifferentCreditAssessment("Практика", 2, 3)); // удовлетворяет (>=3)
            recordBook.AddAssessment(new ExamAssessment("История", 1, 3)); // 1 семестр игнорируется, хоть там и тройка за экзамен

            Assertions.assertTrue(recordBook.GetPossibleTransferToFreeEducation());
        }

        @Test
        @DisplayName("Возвращает false, если хотя бы одна оценка за последние два семестра плохая")
        void shouldReturnFalseIfAnyLastTwoSemestersUnsatisfactory() {
            RecordBook recordBook = new RecordBook(1, false);
            recordBook.AddAssessment(new ExamAssessment("Программирование", 3, 3)); // экзамен на 3 НЕ удовлетворяет условиям бюджета
            recordBook.AddAssessment(new DifferentCreditAssessment("Практика", 2, 4));

            Assertions.assertFalse(recordBook.GetPossibleTransferToFreeEducation());
        }
    }

    @Nested
    @DisplayName("Проверка условий диплома с отличием (GetStatusDiplomaHonor)")
    class DiplomaHonorTests {

        @Test
        @DisplayName("Возвращает true при выполнении всех условий (балл > 4.75, квал. работа = 5, нет троек)")
        void shouldReturnTrueWhenAllConditionsMet() {
            RecordBook recordBook = new RecordBook(1, true);
            recordBook.AddAssessment(new ExamAssessment("Предмет 1", 1, 5));
            recordBook.AddAssessment(new ExamAssessment("Предмет 2", 1, 5));
            recordBook.AddAssessment(new ExamAssessment("Предмет 3", 1, 5));
            recordBook.AddAssessment(new ExamAssessment("Предмет 4", 1, 4));

            recordBook.AddAssessment(new QualificationPaperAssessment(5)); // Квалификационная работа на 5
            recordBook.AddAssessment(new BinaryAssessment("Физкультура", 1, true)); // Зачет не влияет на тройки

            Assertions.assertTrue(recordBook.GetStatusDiplomaHonor());
        }

        @Test
        @DisplayName("Возвращает false, если средний балл ниже 4.75")
        void shouldReturnFalseWhenMediumGradeIsLow() {
            RecordBook recordBook = new RecordBook(1, true);
            recordBook.AddAssessment(new ExamAssessment("Предмет 1", 1, 4));
            recordBook.AddAssessment(new ExamAssessment("Предмет 2", 1, 5));
            recordBook.AddAssessment(new QualificationPaperAssessment(5));

            Assertions.assertFalse(recordBook.GetStatusDiplomaHonor());
        }

        @Test
        @DisplayName("Возвращает false, если за квалификационную работу оценка ниже 5")
        void shouldReturnFalseWhenQualificationPaperIsNotFive() {
            RecordBook recordBook = new RecordBook(1, true);
            recordBook.AddAssessment(new ExamAssessment("Предмет 1", 1, 5));
            recordBook.AddAssessment(new QualificationPaperAssessment(4)); // Не отлично

            Assertions.assertFalse(recordBook.GetStatusDiplomaHonor());
        }

        @Test
        @DisplayName("Возвращает false, если есть хотя бы одна тройка по влияющему на диплом предмету")
        void shouldReturnFalseWhenHasThree() {
            RecordBook recordBook = new RecordBook(1, true);
            // Наберем много пятерок для высокого среднего балла
            for (int i = 0; i < 10; i++) {
                recordBook.AddAssessment(new ExamAssessment("Предмет " + i, 1, 5));
            }
            recordBook.AddAssessment(new ExamAssessment("Проблемный предмет", 1, 3)); // Тройка!
            recordBook.AddAssessment(new QualificationPaperAssessment(5));

            Assertions.assertFalse(recordBook.GetStatusDiplomaHonor());
        }
    }

    @Nested
    @DisplayName("Проверка условий обычной и повышенной стипендии (Base/Raise Grant)")
    class GrantTests {

        @Test
        @DisplayName("Обычная стипендия выдается, если все предметы текущего семестра закрыты на базовый грант")
        void testGetStatusBaseGrant() {
            RecordBook recordBook = new RecordBook(1, true);
            recordBook.AddAssessment(new ExamAssessment("Математика", 2, 4)); // удовлетворяет
            recordBook.AddAssessment(new DifferentCreditAssessment("История", 2, 4)); // удовлетворяет
            recordBook.AddAssessment(new ExamAssessment("Физика", 1, 3)); // прошлый семестр игнорируется

            Assertions.assertTrue(recordBook.GetStatusBaseGrand());

            recordBook.AddAssessment(new ExamAssessment("Химия", 2, 3));
            Assertions.assertFalse(recordBook.GetStatusBaseGrand());
        }

        @Test
        @DisplayName("Повышенная стипендия выдается, если все предметы текущего семестра закрыты на повышенный грант (только 5)")
        void testGetStatusRaiseGrant() {
            RecordBook recordBook = new RecordBook(1, true);
            recordBook.AddAssessment(new ExamAssessment("Математика", 2, 5)); // удовлетворяет
            recordBook.AddAssessment(new DifferentCreditAssessment("История", 2, 5)); // удовлетворяет
            Assertions.assertTrue(recordBook.GetStatusRaiseGrand());

            recordBook.AddAssessment(new ExamAssessment("Химия", 2, 4));
            Assertions.assertFalse(recordBook.GetStatusRaiseGrand());
        }
    }
}
