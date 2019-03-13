/**
 * Auto Generated Java Class.
 */
import java.util.Random;
import java.util.Scanner;

public class BullsAndCows {
    public static void main (String[] args) {
        // the input 123 generates the digits 2695
        playBullsAndCows(123);
    }

    public static boolean contains(int[] y, int number){
        boolean findContains = false;
        for(int i = 0; i < y.length; i++) {
            if (number == y[i]) {
                findContains = true;
            }
        }
        return findContains;
    }

    public static int[] generateSecretDigits(int seed) {
        int arrayLength = 4;
        int[] array = new int [arrayLength];
        Random arrayMade = new Random (seed);
        for(int i = 0; i < array.length; i++){
            int randomNumber = arrayMade.nextInt(10);
            if (contains(array, randomNumber)){
               randomNumber = arrayMade.nextInt(10);
            }
            array[i] = randomNumber;
        }
        return array;
    }

    public static int[] extractDigits(int integer){
        if(integer<0){
            integer = integer*-1;
        }
        int inputLength = Integer.toString(integer).length();
        if(inputLength<4){
            int[] array = new int[4];
            for (int i = 3; i>=0; i--){
                array[i] = integer % 10;
                integer = integer/10;
            }
            return array;
        }
        else{
            int[] array = new int[inputLength];
            for (int i = inputLength-1; i>=0; i--){
                array[i] = integer % 10;
                integer = integer/10;
            }
            return array;
        }
    }

    public static int getNumOfBulls(int[] secretNumber, int[] numberGuessed){
        // In 1f-4 if the array size is >4, there is only a print message
        //However for this part we are required to trow an exception that exits the program
        if(secretNumber.length != numberGuessed.length){
            throw new IllegalArgumentException("The lengths of the secret number and the number you guessed are different. Your guess should have the following number of digits: "+ secretNumber.length);
        }
        else {
            int bulls = 0;
            for (int i = 0; i <secretNumber.length; i++){
                if(secretNumber[i] == numberGuessed[i]){
                    bulls++;
                }
            }
            return bulls;
        }
    }

    public static int getNumOfCows(int[] secretNumber, int[] numberGuessed) {
        if(secretNumber.length != numberGuessed.length){
            throw new IllegalArgumentException("The lengths of the secret number and the number you guessed are different. Your guess should have the following number of digits: "+ secretNumber.length);
        }
        else {
            int cows = 0;
            // Not sure if a number is a bull, it should be excluded from search or not
            /*for (int i = 0; i <secretNumber.length; i++){
                if(secretNumber[i] == numberGuessed[i]){
                    secretNumber[i] = -1;
                    numberGuessed[i] = -2;
                }
            }*/
            for (int x = 0; x <secretNumber.length; x++){
                for (int y = 0; y <numberGuessed.length; y++){
                    if((secretNumber[x] == numberGuessed[y]) && (x != y)){
                        cows++;
                    }
                }
            }
            return cows;
        }
    }

    public static void playBullsAndCows(int input){
        long startTime = System.currentTimeMillis();
        long endTime;
        Scanner sc = new Scanner(System.in);
        int bulls = 0;
        int[] secretNumber = generateSecretDigits(input);
        System.out.println("Welcome to the game Bulls and Cows");
        int guess = 1;
        while(guess > 0){
            if (guess>5){
                System.out.println("Might be time for you to five up. Do you want to quit? (y/n)");
                sc.nextLine();
                String action = sc.nextLine();
                if (action.equals("y")){
                    endTime = System.currentTimeMillis();
                    System.out.println("You gave up after "+guess+" tries and spent " + (endTime-startTime)/1000 + " seconds of your precious time");
                    System.exit(0);
                }
            }
            System.out.println("Guess #:"+guess+" Enter a four digit number: ");
            int yourGuess = sc.nextInt();
            int[] extractedArray = extractDigits(yourGuess);
            if((yourGuess<0) || (extractedArray.length>4)){
                System.out.println("Oops!!! You wasted a guess. Please enter a positive integer with at most four digits.");
            }
            else{
                System.out.println("Bulls:" + getNumOfBulls(secretNumber, extractedArray));
                System.out.println("Cows: " + getNumOfCows(secretNumber, extractedArray));
            }
            //The following try catch block prints the IllegalArgumentException and the program does not exit
            try{
                bulls = getNumOfBulls(secretNumber, extractedArray);
            }
            catch (Exception e){
                System.out.println(e);
            }
            if(bulls == 4){
                break;
            }
            guess++;
        }
        endTime = System.currentTimeMillis();
        System.out.println("Congratulations!!! You cracked the code in "+guess+" tries and in just " + (endTime - startTime)/1000 + " seconds");
    }
}

