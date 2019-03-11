package logic;

import constants.LuhnCheckConstants;

public class LuhnAlgos {

    public boolean luhnCheckVisa(String chunk1, String chunk2, String chunk3, String chunk4) {
        int firstChunk = Integer.parseInt(chunk1);
        int secondChunk = Integer.parseInt(chunk2);
        int thirdChunk = Integer.parseInt(chunk3);
        int fourthChunk = Integer.parseInt(chunk4);

        int[] firstFour = getFourDigits(firstChunk);
        int[] secondFour = getFourDigits(secondChunk);
        int[] thirdFour = getFourDigits(thirdChunk);
        int[] fourthFour = getFourDigits(fourthChunk);

        int[] cardNumberInt = new int[LuhnCheckConstants.VISA_SIZE];
        int i = 0;
        for(; i < 4; i++) {
            cardNumberInt[i] = firstFour[i];
        }
        for(; i < 8; i++) {
            cardNumberInt[i] = secondFour[i - 4];
        }
        for(; i < 12; i++) {
            cardNumberInt[i] = thirdFour[i - 8];
        }
        for(; i < 16; i++) {
            cardNumberInt[i] = fourthFour[i - 12];
        }

        int k = 0;
        while(k < cardNumberInt.length) {
            cardNumberInt[k] *= 2;
            k += 2;
        }

        k = 0;
        while(k < cardNumberInt.length) {
            int currNum = cardNumberInt[k];
            int secondDigit;
            int firstDigit;
            if(currNum >= 10) {
                secondDigit = currNum % 10;
                currNum /= 10;
                firstDigit = currNum % 10;
                cardNumberInt[k] = secondDigit + firstDigit;
            }
            k += 2;
        }

        int sum = 0;
        for(int digit : cardNumberInt) {
            sum += digit;
        }

        return (sum % 10 == 0);
    }

    public boolean luhnCheckAmex(String chunk1, String chunk2, String chunk3) {
        int firstChunk = Integer.parseInt(chunk1);
        int secondChunk = Integer.parseInt(chunk2);
        int thirdChunk = Integer.parseInt(chunk3);

        int[] firstFour = getFourDigits(firstChunk);
        int[] secondSix = getNDigits(secondChunk, 6);
        int[] thirdFive = getNDigits(thirdChunk, 5);

        int[] cardNumberInt = new int[LuhnCheckConstants.AMEX_SIZE];
        int i = 0;
        for(; i < 4; i++) {
            cardNumberInt[i] = firstFour[i];
        }
        for(; i < 10; i++) {
            cardNumberInt[i] = secondSix[i - 4];
        }
        for(; i < 15; i++) {
            cardNumberInt[i] = thirdFive[i - 10];
        }

        /* Double every other digit starting with
        the second digit. */
        int k = 1;
        while(k < cardNumberInt.length) {
            cardNumberInt[k] *= 2;
            k += 2;
        }

        k = 1;
        while(k < cardNumberInt.length) {
            int currNum = cardNumberInt[k];
            int secondDigit;
            int firstDigit;
            if(currNum >= 10) {
                secondDigit = currNum % 10;
                currNum /= 10;
                firstDigit = currNum % 10;
                cardNumberInt[k] = secondDigit + firstDigit;
            }
            k += 2;
        }

        int sum = 0;
        for(int digit : cardNumberInt) {
            sum += digit;
        }

        return (sum % 10 == 0);
    }

    private int[] getFourDigits(int chunk) {
        int[] fourDigits = new int[4];
        for(int i = 3; i >= 0; i--) {
            fourDigits[i] = chunk % 10;
            chunk /= 10;
        }
        return fourDigits;
    }

    /**
     * Parses n digits from a number
     * @param chunk the number to be parsed
     * @param n the number of digits
     * @return the array of individual digits
     */
    private int[] getNDigits(int chunk, int n) {
        int[] nDigits = new int[n];
        for(int i = n - 1; i >= 0; i--) {
            nDigits[i] = chunk % 10;
            chunk /= 10;
        }
        return nDigits;
    }
}
