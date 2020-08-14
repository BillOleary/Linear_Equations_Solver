package solver.EquationSolver.ComplexValue;

public class ComplexMaths {

    //enum class for complex 0 and 1 - TO be used for testing in the equals() method
    public enum COMPLEX_CONSTANT {
        ZERO (0d, 0d),
        ONE (1d, 0d);

        Double a;
        Double b;

        COMPLEX_CONSTANT(Double a, Double b) {
            this.a = a;
            this.b = b;
        }

        public final ComplexNumber zero() {
            return new ComplexNumber(this.a, this.b);
        }

        public final ComplexNumber one() {
            return new ComplexNumber(this.a, this.b);
        }

    };

    //Empty Constructor
    public ComplexMaths() {

    }

    public static ComplexNumber add(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.getReal() + b.getReal(), a.getImaginary() + b.getImaginary());
    }

    public static ComplexNumber subtract(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.getReal() - b.getReal(), a.getImaginary() - b.getImaginary());
    }

    public static ComplexNumber multiply(ComplexNumber a, ComplexNumber b) {
        double realAccumulator;
        double imaginaryAccumulator;
        realAccumulator         =   a.getReal() * b.getReal() -
                                    a.getImaginary() * b.getImaginary();
        imaginaryAccumulator    =   a.getReal() * b.getImaginary() +
                                    a.getImaginary() * b.getReal();

        return new ComplexNumber(realAccumulator, imaginaryAccumulator);
    }

    public static ComplexNumber divide(ComplexNumber a, ComplexNumber b) {

        if (a.equals(b)) {
            return ComplexNumber.complexNumberOne();
        } else {
            ComplexNumber bConjugate = conjugate(b);
            ComplexNumber numerator = multiply(a, bConjugate);
            ComplexNumber denominator = multiply(b, bConjugate);
            return new ComplexNumber(numerator.getReal() / denominator.getReal(),
                    numerator.getImaginary() / denominator.getReal());
        }
    }

    public static ComplexNumber conjugate(ComplexNumber number) {
        return new ComplexNumber(number.getReal(), -number.getImaginary());
    }

    public static ComplexNumber negate(ComplexNumber number) {
        return new ComplexNumber(-1 * number.getReal(), -1 * number.getImaginary());
    }
}
