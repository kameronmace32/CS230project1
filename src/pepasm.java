import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class pepasm {
    public static void main(String[] args) {
        if (args.length < 1) { //args is from terminal, this takes program1.pep, program2.pep, etc...
            System.out.println("java pepasm <filename>");
            return;
        }


        String filePath = args[0]; // Get filename from command-line/terminal arguments
        List<Character> output = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { //this reader allows to read line by line
            String line;

            while ((line = reader.readLine()) != null) { //continues reading each line while there are still lines to read
                String[] words = line.split(" "); //divides lines by spaces, creating words
                for (String word : words) { //iterates through each line by the individual instructions that are separated by spaces

                    //following code takes the instruction and adds the machine language equivalent to the output array
                    //reading the hex code address should be easy, given 0x1234, take out 1234 and output it as 12 34,
                    //an instruction line is like so: <instruction> <address><,>< ><addressing mode>
                    //Maybe add all outputs to variables and assemble an output line at the end of reading a line
                    if (word == "LDBA") {
                        output.add('D');
                    } else if (word == "STBA") {
                        output.add('F');
                    } else if (word == "STWA") {
                        output.add('E');
                    } else if (word == "LDWA") {
                        output.add('C');
                    } else if (word == "ANDA") {
                        output.add('8');
                    } else if (word == "ASLA") {
                        output.add('0');
                    } else if (word == "ASRA") {
                        output.add('0');
                    } else if (word == "STOP") {
                        output.add('0');
                    } else if (word == ".END") {
                        output.add('z');
                    }else if (word == ",d") {
                        String addressing = "1 ";
                    }else if (word == ",i") {
                        String addressing = "0 ";
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



//LDBA 0x0048, i ------ would be = D0 00 48 ---- LDBA = D, i = 0, 0x0048 = 00 48
//Program should read LDBA then print first letter 'D' next it should read 0x0048 then put it in a variable next read i and print, then print the variable for 0x0048