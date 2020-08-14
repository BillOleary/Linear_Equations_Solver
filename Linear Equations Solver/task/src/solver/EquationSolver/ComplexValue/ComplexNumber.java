package solver.EquationSolver.ComplexValue;

import solver.EquationSolver.MultiVariableSolver;

public class ComplexNumber extends Number implements Comparable<ComplexNumber> {

    private final double real;
    private final double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return this.real;
    }

    public double getImaginary() {
        return this.imaginary;
    }

    @Override
    public int compareTo(ComplexNumber o) {
        if (this.real == o.real && this.imaginary == o.imaginary)
            return 0;
        else {
            if (this.real < o.real && this.imaginary < o.imaginary) {
                return -1;
            }
            return 1;
        }
    }

    @Override
    public String toString() {
        String realPart = Double.toString(real).matches(MultiVariableSolver.DIGIT_MATCH) ?
                            String.format("%d", (int) real) :
                            String.format("%.4f", real);
        String imaginaryPart = Double.toString(imaginary).matches(MultiVariableSolver.DIGIT_MATCH) ?
                String.format("%d", (int) imaginary) :
                String.format("%.4f", imaginary);

        return  (realPart.matches("0") ?
                "" :
                realPart) +
                (imaginary > 0 ?
                        "+" + imaginaryPart + "i" :
                        imaginary == 0 ?
                                "" :
                                imaginaryPart + "i");
    }


    @Override
    public int intValue() {
        return (int) this.getReal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplexNumber)) return false;

        ComplexNumber that = (ComplexNumber) o;

        if (Double.compare(that.getReal(), getReal()) != 0) return false;
        return Double.compare(that.getImaginary(), getImaginary()) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getReal());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getImaginary());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public static final ComplexNumber complexNumberZero() {
        return new ComplexNumber(0, 0);
    }

    public static final ComplexNumber complexNumberOne() {
        return new ComplexNumber(1, 0);
    }


    @Override
    public long longValue() {
        return (long)this.getReal();
    }

    @Override
    public float floatValue() {
        return (float)this.getReal();
    }

    @Override
    public double doubleValue() {
        return this.getReal();
    }
}
