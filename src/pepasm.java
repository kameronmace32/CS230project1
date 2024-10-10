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

        //output should just be a System.out.println();

        String filePath = args[0]; // Get filename from command-line/terminal arguments
        List<String> output = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { //this reader allows to read line by line
            String line;

            while ((line = reader.readLine()) != null) { //continues reading each line while there are still lines to read

                if(line.trim().isEmpty()) continue; //prevents the reader from stopping if there is a word/space that doesn't have any information

                String[] words = line.split(" "); //divides lines by spaces, creating words

                //Sets variables
                String instruction = words[0];
                String addressingMode = "";
                String address;

                if (words.length > 1) {
                    address = words[1].replace(",", ""); //removes the comma so that it doesn't show up in the output
                    if (words.length > 2) {
                        addressingMode = words[2];
                    }


                    String opcode;
                    switch (instruction) {
                        case "LDBA":
                            opcode = ("D");
                            break;
                        case "STBA":
                            opcode = ("F");
                            break;
                        case "STWA":
                            opcode = ("E");
                            break;
                        case "LDWA":
                            opcode = ("C");
                            break;
                        case "ANDA":
                            opcode = ("8");
                            break;
                        case "ADDA":
                            opcode = ("6");
                            break;
                        case "ASLA":
                            opcode = ("0A");
                            output.add(opcode);
                            continue;
                        case "ASRA":
                            opcode = ("0C");
                            output.add(opcode);
                            continue;
                        case "STOP":
                            opcode = ("00");
                            output.add(opcode);
                            continue;
                        case ".END":
                            output.add("zz");
                            continue;
                        default:
                            System.out.println("error");
                            continue;
                    }

                    // Determine the opcode based on addressing mode
                    if (addressingMode.equals("i")) {
                        output.add(opcode + "0"); // Immediate mode
                    } else if (addressingMode.equals("d")) {
                        output.add(opcode + "1"); // Direct mode
                    } else{
                        output.add(opcode); //no addressing mode: ASLA, ASRA, STOP, .END
                    }


                    //Addressing modes
                    if (address.startsWith("0x")) {
                        String hexAddress = address.substring(2);
                        if (hexAddress.length() == 4) {
                            output.add(hexAddress.substring(0, 2)); // High byte
                            output.add(hexAddress.substring(2));    // Low byte
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(String.join(" ", output));
    }
}




//To run code in terminal, do cd "path/to/src" -> javac pepasm.java -> java pepasm program1.pep


//LDBA 0x0048, i ------ would be = D0 00 48 ---- LDBA = D, i = 0, 0x0048 = 00 48
//Program should read LDBA then print first letter 'D' next it should read 0x0048 then put it in a variable next read i and print, then print the variable for 0x0048\

//following code takes the instruction and adds the machine language equivalent to the output array
//reading the hex code address should be easy, given 0x1234, take out 1234 and output it as 12 34,
//an instruction line is like so: <instruction> <address><,>< ><addressing mode>
//Maybe add all outputs to variables and assemble an output line at the end of reading a line