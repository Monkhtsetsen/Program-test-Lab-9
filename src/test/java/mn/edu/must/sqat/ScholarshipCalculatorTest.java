package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
