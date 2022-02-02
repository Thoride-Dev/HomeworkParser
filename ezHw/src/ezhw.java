import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.*;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

/**
 * S
 *
 * @author meme man
 */
public final class ezhw {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private ezhw() {
        // no code needed here

    }
    
    public static ArrayList<String> getAnswers(String inputString, boolean rawValue)
    {
        ArrayList<String> answers = new ArrayList<String>();
        for (int i = 0; inputString.contains("\\answer"); i++) //while there are still answers
        {

            if(rawValue)
            {
                inputString = inputString.substring(inputString.indexOf("\\answer")-1);

                if(inputString.charAt(0) != '{')
                {
                    inputString = inputString.substring(inputString.indexOf("{"));
                }
            }
            else //just answer
            {
                inputString = inputString.substring(inputString.indexOf("answer"));
                inputString = inputString.substring(inputString.indexOf("{"));

            }

            int innerBracketCount = 0;
            int closerBracketCount = 0;
            String workingString = inputString;


            int startIndex = workingString.indexOf("{");
            int endIndex = workingString.indexOf("}");

            if(startIndex == -1)
            {
                startIndex = 1000000000;
            }
            if(endIndex == -1)
            {
                endIndex = 1000000000;
            }

            //System.out.println("testW");
            if(startIndex < endIndex) //if { is next
            {
                innerBracketCount++;
                workingString = workingString.substring(workingString.indexOf("{") + 1);
            }
            else if(endIndex < startIndex) // if } is next
            {
                closerBracketCount++;
                workingString = workingString.substring(workingString.indexOf("}") + 1);
            }
            



            for(int g = 0; (innerBracketCount != closerBracketCount); g++)
            {

                startIndex = workingString.indexOf("{");
                endIndex = workingString.indexOf("}");

                if(startIndex == -1)
                {
                    startIndex = 1000000000;
                }
                if(endIndex == -1)
                {
                    endIndex = 1000000000;
                }

                if(startIndex < endIndex) //if { is next
                {
                    innerBracketCount++;
                    workingString = workingString.substring(workingString.indexOf("{") + 1);
                }
                else if(endIndex < startIndex) // if } is next
                {
                    closerBracketCount++;
                    workingString = workingString.substring(workingString.indexOf("}") + 1);
                }

            }

            if(rawValue)
            {
                answers.add(inputString.substring(0, inputString.length() - workingString.length()));
            }
            else
            {
                if(inputString.substring(1).charAt(0) == ' ')
                {
                    answers.add(inputString.substring(2, inputString.length() - workingString.length()-1));
                }
                else
                {
                    answers.add(inputString.substring(1, inputString.length() - workingString.length()-1));
                }

            }

            inputString = workingString;
        }
        return answers;
    }
    

    public static String simplify(String input) {
        String output = "";

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '\\') {
                if (input.charAt(i + 1) == 'p') {
                    output += "pi";
                    i += 2;
                } else if (input.charAt(i + 1) == 'l') {
                    if(input.charAt(i + 2) == 'e') {
                        output += "";
                        i += 4; 
                    }else if(input.charAt(i + 2) == 'n') {
                        output += "ln";
                        i += 2;
                    }
                    
                } else if (input.charAt(i + 1) == 'r') {
                    output += "";
                    i += 5;
                } else if (input.charAt(i + 1) == 'f') { //fraction function
                    output += "";
                    i += 6;
                    int x = input.indexOf("}{", i);
                    int j;
                    for (j = i; j < x; j++) {
                        char b = input.charAt(j);
                        if (b == '{') {
                            output += "(";
                        } else if (b == '}') {
                            output += ")";
                        }else{
                            output += b;
                        }
                    }
                    output += ")/";
                    i = j;
                    /**
                     * int j = i; do { j++; if (input.charAt(j) == '{') { output
                     * += "("; }else if (input.charAt(j) == '}') {
                     * if(input.charAt(j + 1) == '{') { output += "/"; }else {
                     * output += ")"; }
                     * 
                     * }else { output += input.charAt(j); }
                     * 
                     * 
                     * 
                     * 
                     * 
                     * } while (input.charAt(j) != '}'); i+=j;
                     **/

                }
            } else if (c == ' ') {
                output += "";
            } else if (c == '{') {
                output += "(";
            } else if (c == '}') {
                output += ")";
            } else {
                output += c;
            }

        }

        return output;

    }
    
    public static String compileAnswers(ArrayList<String> str) {
        String answers = "";
        for (String answ : str) {
            answers += simplify(answ) + "\n";
        }
        
        return answers;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        File selectedFile;
        String pathString = "";
        String text = "";
        /**
        File directory = new File("C:\\Users\\Lego\\Downloads\\HomeworkFiles");
        fileChooser.setCurrentDirectory(directory);
        Action details = fileChooser.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);
        
        
        
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            Path path = selectedFile.toPath();
            pathString = path.toString();
        }
        **/
        File directory = new File("C:\\Users\\Lego\\Downloads\\HomeworkFiles");
        File[] files = directory.listFiles();
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null)
        {
            for (File file : files)
            {
                if (file.lastModified() > lastModifiedTime)
                {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }
        pathString = chosenFile.toPath().toString();
        //System.out.println(pathString);
        
        SimpleReader in = new SimpleReader1L(pathString);
        while(!in.atEOS()) {
            text = in.nextLine();
           
        }
        
        System.out.println(compileAnswers(getAnswers(text, false)));
    }

}
