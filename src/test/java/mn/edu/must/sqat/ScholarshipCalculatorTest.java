package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScholarshipCalculatorTest {

    private ScholarshipCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new ScholarshipCalculator();
    }

    @Test
    void letterGrade_score95_returnsA() {
        assertEquals("A", calculator.letterGrade(95));
    }

    @Test
    void letterGrade_score85_returnsB() {
        assertEquals("B", calculator.letterGrade(85));
    }

    @Test
    void gpa_scores95and85_returns3_5() {
        assertEquals(3.5, calculator.gpa(new double[] {95, 85}), 0.001);
    }

    @Test
    void scholarship_gpa3_9_credits16_noViolation_returns100() {
        assertEquals(100, calculator.scholarship(3.9, 16, false));
    }

    @Test
    void scholarship_gpa3_5_credits13_noViolation_returns70() {
        assertEquals(70, calculator.scholarship(3.5, 13, false));
    }

    @Test
    void letterGrade_scoreNegative_throws() {
        assertThrows(IllegalArgumentException.class, () -> calculator.letterGrade(-1));
    }

    @Test
    void letterGrade_scoreAbove100_throws() {
        assertThrows(IllegalArgumentException.class, () -> calculator.letterGrade(101));
    }

    @Test
    void letterGrade_score75_returnsC() {
        assertEquals("C", calculator.letterGrade(75));
    }

    @Test
    void letterGrade_score65_returnsD() {
        assertEquals("D", calculator.letterGrade(65));
    }

    @Test
    void letterGrade_score50_returnsF() {
        assertEquals("F", calculator.letterGrade(50));
    }

    @Test
    void letterGrade_score60_returnsD() {
        assertEquals("D", calculator.letterGrade(60));
    }

    @Test
    void letterGrade_score59_99_returnsF() {
        assertEquals("F", calculator.letterGrade(59.99));
    }

    @Test
    void letterGrade_score69_99_returnsD() {
        assertEquals("D", calculator.letterGrade(69.99));
    }

    @Test
    void letterGrade_score0_returnsF() {
        assertEquals("F", calculator.letterGrade(0));
    }

    @Test
    void letterGrade_score100_returnsA() {
        assertEquals("A", calculator.letterGrade(100));
    }

    @Test
    void letterGrade_score89_99_returnsB() {
        assertEquals("B", calculator.letterGrade(89.99));
    }

    @Test
    void letterGrade_score90_returnsA() {
        assertEquals("A", calculator.letterGrade(90));
    }

    @Test
    void gpa_null_throws() {
        assertThrows(IllegalArgumentException.class, () -> calculator.gpa(null));
    }

    @Test
    void gpa_empty_throws() {
        assertThrows(IllegalArgumentException.class, () -> calculator.gpa(new double[] {}));
    }

    @Test
    void gpa_score75_returns2() {
        assertEquals(2.0, calculator.gpa(new double[] {75}), 0.001);
    }

    @Test
    void gpa_score65_returns1() {
        assertEquals(1.0, calculator.gpa(new double[] {65}), 0.001);
    }

    @Test
    void gpa_score50_returns0() {
        assertEquals(0.0, calculator.gpa(new double[] {50}), 0.001);
    }

    @Test
    void gpa_score95_returns4() {
        assertEquals(4.0, calculator.gpa(new double[] {95}), 0.001);
    }

    @Test
    void gpa_scores95_85_75_65_returns2_5() {
        assertEquals(2.5, calculator.gpa(new double[] {95, 85, 75, 65}), 0.001);
    }

    @Test
    void scholarship_gpaNegative_throws() {
        assertThrows(IllegalArgumentException.class, () -> calculator.scholarship(-0.1, 15, false));
    }

    @Test
    void scholarship_gpaAbove4_throws() {
        assertThrows(IllegalArgumentException.class, () -> calculator.scholarship(4.1, 15, false));
    }

    @Test
    void scholarship_creditsNegative_throws() {
        assertThrows(IllegalArgumentException.class, () -> calculator.scholarship(3.0, -1, false));
    }

    @Test
    void scholarship_hasViolation_returns0() {
        assertEquals(0, calculator.scholarship(3.9, 16, true));
    }

    @Test
    void scholarship_gpa3_9_credits14_returns70() {
        assertEquals(70, calculator.scholarship(3.9, 14, false));
    }

    @Test
    void scholarship_gpa3_5_credits11_returns50() {
        assertEquals(50, calculator.scholarship(3.5, 11, false));
    }

    @Test
    void scholarship_gpa3_2_credits16_returns50() {
        assertEquals(50, calculator.scholarship(3.2, 16, false));
    }

    @Test
    void scholarship_gpa2_9_credits16_returns0() {
        assertEquals(0, calculator.scholarship(2.9, 16, false));
    }
}
