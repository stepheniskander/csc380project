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
    private RationalNumber fractionConstructor(String s){
        Pattern check = Pattern.compile("(\\-?[0-9]+)/([0-9]+)");
        Matcher match = check.matcher(s);
        if(match.matches()){
            return new RationalNumber(match.group(1),match.group(2));
        }else{
            return new RationalNumber(s);
        }
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
                if(!s.matches("^\\-?([0-9/])*[xX]\\^\\-([0-9/])+$")) {
                    split.set(split.indexOf(s), s.split("-",2)[0]);
                    String temp = "-" + s.split("-",2)[1];
                    split.add(temp);
                }
            }
        }
        RationalNumber[] coefficients = new RationalNumber[split.size()];
        RationalNumber[] powers = new RationalNumber[split.size()];
        for(int i = 0; i < split.size(); i++ ){
            String s = split.get(i);
            Matcher termMatcher = termSplitter.matcher(s);
            termMatcher.matches();
            if(termMatcher.matches()){
                if(!s.contains("x")){
                    powers[i] = RationalNumber.ZERO;
                    coefficients[i] = fractionConstructor(termMatcher.group(1));

                }else if (s.equals("x")){
                    coefficients[i] = RationalNumber.ONE;
                    powers[i] = RationalNumber.ONE;

                }else if(s.equals("-x")){
                    coefficients[i]= new RationalNumber("-1");
                    powers[i] = RationalNumber.ONE;
                }
                else if(termMatcher.group(2)==null && termMatcher.group(3)==null){
                    coefficients[i] = fractionConstructor(termMatcher.group(1));
                    powers[i] = RationalNumber.ZERO;

                }else if(termMatcher.group(3)==null){
                    coefficients[i] = fractionConstructor(termMatcher.group(1));
                    powers[i] = RationalNumber.ONE;

                }else if(termMatcher.group(1)==null ||termMatcher.group(1).length()==0 ){
                    coefficients[i] =  RationalNumber.ONE;
                    powers[i] = fractionConstructor(termMatcher.group(3));

                }else if(termMatcher.group(1).equals("-")){
                    coefficients[i] = new RationalNumber("-1");
                    powers[i] = fractionConstructor(termMatcher.group(3));

                }else{
                    coefficients[i] = fractionConstructor(termMatcher.group(1));
                    powers[i] = fractionConstructor(termMatcher.group(3));
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
                if(!powers[i].equals(RationalNumber.ZERO)) {

                    result.append("x");
                    if (!powers[i].equals(RationalNumber.ONE)) {
                        result.append("^").append(powers[i]);
                    }

                    result.append("+");
                }
            }
        }

        if(!coefficients[coefficients.length-1].equals(RationalNumber.ZERO)) {
            result.append(coefficients[coefficients.length-1]);
            if(!powers[coefficients.length-1].equals(RationalNumber.ZERO)) {
                result.append("x");
                if (!powers[coefficients.length - 1].equals(RationalNumber.ONE)) {
                    result.append("^").append(powers[coefficients.length - 1]);
                }
            }
        }
        if(result.charAt(result.length()-1) == '+'){
            return result.substring(0, result.length()-2);
        }

        return result.toString();
    }
}
