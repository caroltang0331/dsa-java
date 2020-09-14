package edu.emory.cs.algebraic;

import java.sql.SQLOutput;
import java.util.Arrays;

/** @author Jinho D. Choi */
public class LongIntegerQuiz extends LongInteger {
    public LongIntegerQuiz(LongInteger n) { super(n); }

    public LongIntegerQuiz(String n) { super(n); }

    @Override
    protected void addDifferentSign(LongInteger n) {
        //set the sign of the result, assume the same as the original if absolute value is the same
        if (compareAbs(n) == 0) sign = sign;
                else {
                Boolean a = (isPositive() && (compareAbs(n) > 0));
                Boolean b = (isNegative() && (compareAbs(n) < 0));
                sign = (a || b) ? Sign.POSITIVE : Sign.NEGATIVE;
                }
        //define the one with greater absolute value as larger, and copy that to result array
        int m = Math.max(digits.length, n.digits.length);
        byte[] result = new byte[m];
        byte[] larger = compareAbs(n) > 0 ? digits : n.digits;
        byte[] smaller = larger == digits ? n.digits : digits;
        System.arraycopy(larger, 0, result, 0, larger.length);

        //subtract the one with smaller absolute value
        for (int i = 0; i < smaller.length; i++) {
            result[i] -= smaller[i];
            if (result[i] < 0) {
                result[i] += 10;
                result[i + 1] -= 1;
            }
        }
        //assign result to digits then take out the extra zeros if there's any
        digits = result;
        for (int a = m - 1 ; a >= 0; a--) {
            if (result[a] != 0) break;
            digits = a == 0 ? Arrays.copyOf(result, 1) : Arrays.copyOf(result, a);
        }





    }


}