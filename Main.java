import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.print(">> ");
        Scanner scanner = new Scanner(System.in);

        String str = scanner.nextLine();
        scanner.close();

        try {
            String result = calc(str);
            System.out.println(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String calc(String input) {
        boolean isRomanNumerals;

        final String regexArabicNumerals = "^\s*([\\d]+)?\s*([\\+\\-\\*\\/])\s*([\\d]+)\s*$";
        final String regexRomanNumerals = "^\s*([IVX]+)?\s*([\\+\\-\\*\\/])\s*([IVX]+)\s*$";

        if (input.matches(regexArabicNumerals)) { // Arabic
            isRomanNumerals = false;
        } else if (input.matches(regexRomanNumerals)) { // Roman
            isRomanNumerals = true;
        } else {
            throw new RuntimeException();
        }

        Matcher matcher;

        matcher = (isRomanNumerals) ? Pattern.compile(regexRomanNumerals).matcher(input) :
                                      Pattern.compile(regexArabicNumerals).matcher(input);

        String[] strParams = new String[3];
        while (matcher.find()) {
            strParams[0] = matcher.group(1); // first operand
            strParams[1] = matcher.group(2); // operation
            strParams[2] = matcher.group(3); // second operand
        }

        int a, b;

        if (isRomanNumerals) {
            a = romanStringToInt(strParams[0]);
            b = romanStringToInt(strParams[2]);
        } else {
            a = Integer.valueOf(strParams[0]);
            b = Integer.valueOf(strParams[2]);
        }

        if ((a < 1 || a > 10) || (b < 1 || b > 10)) {
            throw new RuntimeException();
        }

        int result = 0;
        switch (strParams[1]) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
        }

        if (isRomanNumerals && result < 1) {
            throw new RuntimeException();
        }

        return (isRomanNumerals) ? intToRomanString(result) :
                                   Integer.toString(result);
    }

    enum RomanNumeral {
        I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9),
        X(10), XI(11), XII(12), XIII(13), XIV(14), XV(15), XVI(16), XVII(17), XVIII(18), XIX(19),
        XX(20), XXI(21), XXIV(24), XXV(25), XXVII(27), XXVIII(28),
        XXX(30), XXXII(32), XXXV(35), XXXVI(36),
        XL(40), XLII(42), XLV(45), XLVIII(48), XLIX(49),
        L(50), LIV(54), LVI(56),
        LX(60), LXIII(63), LXIV(64),
        LXX(70), LXXII(72),
        LXXX(80), LXXXI(81),
        XC(90), C(100);

        private int arabicNumeral;

        RomanNumeral(int numeral) {
            this.arabicNumeral = numeral;
        }

        int getArabicNumeral() {
            return arabicNumeral;
        }
    }

    static int romanStringToInt(String numeral) {
        return RomanNumeral.valueOf(numeral).getArabicNumeral();
    }

    static String intToRomanString(int numeral) {
        for (RomanNumeral romanNumeral : RomanNumeral.values()) {
            if (romanNumeral.getArabicNumeral() == numeral) {
                return romanNumeral.toString();
            }
        }

        throw new RuntimeException();
    }
}
