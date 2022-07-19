import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class igs_formating_tool {

    public static void main(String[] args) {

        try {
            File file = new File("part.igs"); 
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer igs_global = new StringBuffer();
            StringBuffer igs_directory = new StringBuffer();
            StringBuffer igs_parameter = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.charAt(line.length() - 8) == 'S') {    //Start

                }
                else if(line.charAt(line.length() - 8) == 'G'){ //Global
                    igs_global.append(line);
                    igs_global.append("\n");
                }
                else if (line.charAt(line.length() - 8) == 'D') {   //Directory
                    igs_directory.append(line);
                    igs_directory.append("\n");
                }
                else if(line.charAt(line.length() - 8) == 'P') {    //Parameter Data
                    igs_parameter.append(line);
                    igs_parameter.append("\n");
                }
                else{  //Terminate

                }
            }
            fr.close();
            System.out.println("Contents of File: ");
            System.out.println(igs_global.toString());
            System.out.println(igs_directory.toString());
            System.out.println(igs_parameter.toString());

            try (PrintWriter out = new PrintWriter("igs_global.txt")) {
                out.println(igs_global.toString());
            }
            try (PrintWriter out = new PrintWriter("igs_directory.txt")) {
                out.println(igs_directory.toString());
            }
            try (PrintWriter out = new PrintWriter("igs_parameter.txt")) {
                out.println(igs_parameter.toString());
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
