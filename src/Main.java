import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


/**

 Решение задачи : http://vk.com/java_problems?w=wall-60560229_939

 Дано определенное количество различных состояний цифровых часов в формате чч::мм::cc
 Необходимо вывести их на экран таким образом, чтобы каждое новое состояние было больше (позже), чем предыдущее.

 Тест
 Входные данные
 Кол-во интервалов: 4
 1) 10 : 20 : 30
 2) 7 : 30 : 00
 3) 23 : 59 : 59
 4) 13 : 30 : 30

 Выходные данные
 7 : 30 : 00
 10 : 20 : 30
 13 : 30 : 30
 23 : 59 : 59

 **/


public class Main  {

    private final static String TIME_ERROR = "Error in time format";
    private final static String FILE_PATH = "D:/input.txt";
    private final static String OUTPUT_PATH = "D:/output.txt";


    public static void main(String[] args) throws IOException, UncheckedIOException {

        long startTime = System.currentTimeMillis();
        ArrayList<int[]> input = convertInputData(FILE_PATH);
        HashMap<Integer, int[]> sortedData = sortData(input);
        writeSortedData(sortedData,OUTPUT_PATH);
        long endTime   = System.currentTimeMillis();
        long totalTime = (endTime - startTime);
        System.out.println("Data sort took "+totalTime+" milliseconds.");

    }

    public static void writeSortedData(HashMap<Integer, int[]> data,String outputPath){





        try {
            System.out.println("Start writing sorted data in file...");
            FileWriter  fstream = new FileWriter(outputPath);
            BufferedWriter out = new BufferedWriter(fstream);

            List sortedKeys=new ArrayList(data.keySet());
            Collections.sort(sortedKeys);

            for (int i = 0; i < sortedKeys.size(); i++) {
                 int[] temp = data.get(sortedKeys.get(i));
                 out.write(temp[0]+" : "+temp[1]+" : "+temp[2]+ System.lineSeparator());

            }

            out.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Write sorted data in file completed successfully.");

    }

    public static HashMap<Integer, int[]> sortData(ArrayList<int[]> data){
        HashMap<Integer, int[]> output = new HashMap<>();
        System.out.println("Start sorting data...");


        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).length==0&&data.get(i)==null){
                System.out.println("Error during parsing data. Array with data can't be empty or null..");
            }
            {
                int seconds = data.get(i)[0]*3600+data.get(i)[1]*60+data.get(i)[2];
                output.put(seconds,data.get(i));
            }
        }


        System.out.println("Data sorted successfully.");
        return output;
    }

    public static ArrayList<int[]> convertInputData(String filePath){

        ArrayList<int[]> input = new ArrayList<>();
        try {
            try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

                System.out.println("Start data convert...");
                stream.forEach(s -> {

                            String[] strArray = s.split(" : ");
                            int[] intArray = new int[strArray.length];
                            for(int i = 0; i < strArray.length; i++) {
                                if(checkTime(Integer.parseInt(strArray[i]))){
                                    intArray[i] = Integer.parseInt(strArray[i]);
                                }
                                else {
                                    System.out.println(TIME_ERROR);
                                    stream.close();
                                }
                            }
                            input.add(intArray);



                        }

                );

                System.out.println("Data converted successfully.");
            } catch (UncheckedIOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    public static boolean checkTime(int time){
        return time<=60?true:false;

    }

}
