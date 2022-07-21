import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class igs_parameter_splitter {
    static String key = "parca71";

    public static void main(String[] args) {

        try {
            File file = new File("igsdata/DGP/" + key + "_parameter.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer parameters = new StringBuffer();

            String line;
            int lastread = 1;
            while ((line = br.readLine()) != null) {
                String templine = line.split(" ")[0];
                StringBuffer nowreadtemp = new StringBuffer();
                nowreadtemp.append(line.charAt(line.length() - 13)).append(line.charAt(line.length() - 12))
                        .append(line.charAt(line.length() - 11))
                        .append(line.charAt(line.length() - 10)).append(line.charAt(line.length() - 9));
                int nowread = Integer.parseInt(nowreadtemp.toString().trim());
                if (lastread == nowread) {
                    parameters.append(templine);
                } else {
                    String head = parameters.toString().split(",")[0];
                    // System.out.print(funtion_connector+" ");
                    function_mediator(head, parameters.toString(), lastread);
                    // System.out.print("\n\n");
                    // System.out.print(parameters);
                    parameters.setLength(0);
                    lastread = nowread;
                    parameters.append(templine);
                }
            }
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void function_mediator(String head, String parameters, int ID) {
        String[] coordinates = new String[3]; // 0->x 1->y 2->z
        int header = Integer.parseInt(head);
        System.out.print(ID + "\n");
        // System.out.print(header);
        // System.out.print("\n");
        if (header == 100) {
            coordinates[0] = (parameters.split(",")[4]);
            coordinates[1] = (parameters.split(",")[5]);
            coordinates[2] = (parameters.split(",")[1]);
            coordinatecalculator(coordinates, 100, ID);
            coordinates[0] = (parameters.split(",")[6]);
            coordinates[1] = (parameters.split(",")[7]);
            coordinates[2] = (parameters.split(",")[1]);
            coordinatecalculator(coordinates, 100, ID);
        } else if (header == 102) {
            // groups composite material
        } else if (header == 108) {
            coordinates[0] = (parameters.split(",")[6]);
            coordinates[1] = (parameters.split(",")[7]);
            coordinates[2] = (parameters.split(",")[8]);
            coordinatecalculator(coordinates, 108, ID);
        } else if (header == 110) {
            coordinates[0] = (parameters.split(",")[1]);
            coordinates[1] = (parameters.split(",")[2]);
            coordinates[2] = (parameters.split(",")[3]);
            coordinatecalculator(coordinates, 108, ID);
            coordinates[0] = (parameters.split(",")[4]);
            coordinates[1] = (parameters.split(",")[5]);
            coordinates[2] = (parameters.split(",")[6]);
            coordinatecalculator(coordinates, 108, ID);
        } else if (header == 118) {
            // sweeps a pointed area
        } else if (header == 120) {
            // rotates a pointed surface
        } else if (header == 122) {
            coordinates[0] = (parameters.split(",")[2]);
            coordinates[1] = (parameters.split(",")[3]);
            coordinates[2] = (parameters.split(",")[4]);
            coordinatecalculator(coordinates, 122, ID);
        } else if (header == 124) {
            // calculate.
        } else if (header == 126) {
            int K = Integer.parseInt((parameters.split(",")[1]));
            int M = Integer.parseInt((parameters.split(",")[2]));
            int flag1 = Integer.parseInt((parameters.split(",")[3]));
            int flag2 = Integer.parseInt((parameters.split(",")[4]));
            int flag3 = Integer.parseInt((parameters.split(",")[5]));
            int flag4 = Integer.parseInt((parameters.split(",")[6]));

            int temp_a = 10 + 2 * K + M;
            int temp_b = 12 + 5 * K + M;

            if (flag1 == 1 && flag2 == 0 && flag3 == 1 && flag4 == 0) {
                temp_a = 1;
                temp_b = 0;
            }

            for (int i = temp_a; i <= temp_b; i = i + 3) {
                coordinates[0] = (parameters.split(",")[i]);
                coordinates[1] = (parameters.split(",")[i + 1]);
                coordinates[2] = (parameters.split(",")[i + 2]);
                coordinatecalculator(coordinates, 126, ID);
            }
        } else if (header == 128) {
            int K2 = Integer.parseInt((parameters.split(",")[1]));
            int K1 = Integer.parseInt((parameters.split(",")[2]));
            int M1 = Integer.parseInt((parameters.split(",")[3]));
            int M2 = Integer.parseInt((parameters.split(",")[4]));
            int temp_a = 15 + K1 + K2 + K1 + K2 + (1 + K1) * (1 + K2);
            int temp_b = 15 + K1 + K2 + M1 + M2 + (1 + K1) * (1 + K2) + 9 + 3 * (1 + K1) * (1 + K2) - 16;

            if (K2 == 5 && K1 == 1 && M1 == 5 && M2 == 1) {
                temp_a = 1;
                temp_b = 0;
            }

            System.out.print(temp_a);
            System.out.print(temp_b);

            for (int i = temp_a; i <= temp_b; i = i + 3) {
                String get1, get2, get3;
                get1 = (parameters.split(",")[i]);
                get2 = (parameters.split(",")[i + 1]);
                get3 = (parameters.split(",")[i + 2]);
                System.out.print(get1);

                coordinates[0] = get1;
                coordinates[1] = get2;
                coordinates[2] = get3;

                coordinatecalculator(coordinates, 128, ID);
            }
        } else if (header == 142) {
            // associates curve and surface via pointer
        } else if (header == 144) {
            // describes a surface trimmed by a boundary consisting of boundary Curves
        }
    }

    public static void coordinatecalculator(String[] coordinates, int prtype, int ID) {
        StringBuffer coordinateTextWithSpace = new StringBuffer();
        StringBuffer coordinateTextWithComma = new StringBuffer();
        StringBuffer coordinateTextDeBug = new StringBuffer();
        if (Float.parseFloat(coordinates[0]) > 1500) {
            coordinateTextWithSpace.append(coordinates[0]).append(" ").append(coordinates[1]).append(" ")
                    .append(coordinates[2]);
            coordinateTextWithComma.append(coordinates[0]).append(",").append(coordinates[1]).append(",")
                    .append(coordinates[2]);
            coordinateTextDeBug.append(coordinates[0]).append("\t").append(coordinates[1]).append("\t")
                    .append(coordinates[2]);
            try (Writer Space = new BufferedWriter(
                    new FileWriter("igsdata/pointCloud/withSpace/" + key + "_coordinates.dat", true));
                    Writer Comma = new BufferedWriter(
                            new FileWriter("igsdata/pointCloud/withComma/" + key + "_coordinates.txt", true));
                    Writer Bug = new BufferedWriter(
                            new FileWriter("igsdata/pointCloud/bugAnalysis/" + key + "_coordinates.txt", true));
                            
            ) {
                Space.append(coordinateTextWithSpace.toString());
                Space.append("\n");
                Space.close();

                Comma.append(coordinateTextWithComma.toString());
                Comma.append("\n");
                Comma.close();

                Bug.append(coordinateTextDeBug.toString());
                Bug.append(" " + prtype + " " + ID);
                Bug.append("\n");
                Bug.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}