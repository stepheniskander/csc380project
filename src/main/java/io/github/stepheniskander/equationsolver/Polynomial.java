package io.github.stepheniskander.equationsolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;
/**
 * A polynomial in Q[x]
 */
public class Polynomial {
    private RationalNumber[] coefficients;
    private RationalNumber[] powers;
    public Polynomial(RationalNumber[] coefficients, RationalNumber[] powers) throws IllegalArgumentException {
        this.coefficients = coefficients;
        this.powers = powers;
    }
    public Polynomial(String input){
        List<String> fixedSplit = Arrays.asList(input.split("\\+"));
        ArrayList<String> split = new ArrayList<>();
        split.addAll(fixedSplit);
        Pattern negCheck = Pattern.compile("(.+) ?- ?(.+)");
        Pattern termSplitter = Pattern.compile(" *([\\-0-9\\/]*)\\*?([xX]\\^(.+)|[xX]|) *");
        for(int i = 0; i<split.size(); i++){
            String s = split.get(i);
            Matcher negMatch = negCheck.matcher(s);
            if(negMatch.matches()){
                split.set(split.indexOf(s),negMatch.group(1));
                String temp = "-" + negMatch.group(2);
                split.add(temp);
            }
        }
        RationalNumber[] coefficients = new RationalNumber[split.size()];
        RationalNumber[] powers = new RationalNumber[split.size()];
        for(int i = 0; i < split.size(); i++ ){
            String s = split.get(i);
            Matcher termMatcher = termSplitter.matcher(s);
            if(termMatcher.matches()){
                if (termMatcher.group(1)==null &&  termMatcher.group(3)==null){
                    coefficients[i] = RationalNumber.ONE;
                    powers[i] = RationalNumber.ONE;
                }else
                if(termMatcher.group(2)==null && termMatcher.group(3)==null){
                    coefficients[i] = new RationalNumber(termMatcher.group(1));
                    powers[i] = RationalNumber.ZERO;
                }else if(termMatcher.group(3)==null){
                    coefficients[i] = new RationalNumber(termMatcher.group(1));
                    powers[i] = RationalNumber.ONE;
                }else{
                    coefficients[i] = new RationalNumber(termMatcher.group(1));
                    powers[i] = new RationalNumber(termMatcher.group(3));

                }
            }

        }

        this.coefficients = coefficients;
        this.powers = powers;

    }
    public RationalNumber[] getCoefficients(){
        return this.coefficients;
    }
    public RationalNumber[] getPowers(){
        return this.powers;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for(int i = 0;i < coefficients.length - 1;i++ ) {
            if(!coefficients[i].equals(RationalNumber.ZERO)) {
                if(!coefficients[i].equals(RationalNumber.ONE)) {
                    result.append(coefficients[i]);
                }

                result.append("x");
                if(!powers[i].equals(RationalNumber.ONE)){
                    result.append("^").append(powers[i]);
                }

                if(coefficients[i + 1].compareTo(RationalNumber.ZERO) > 0) {
                    result.append("+");
                }
            }
        }

        if(!coefficients[coefficients.length-1].equals(RationalNumber.ZERO)) {
            result.append(coefficients[coefficients.length-1]);
            result.append("x");
            if(!powers[coefficients.length-1].equals(RationalNumber.ONE)){
                result.append("^").append(powers[coefficients.length-1]);
            }
        }

        return result.toString();
    }
}
