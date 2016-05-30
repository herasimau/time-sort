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

 @author herasimau date 29/05/2016

 **/


public class Main  {

    private final static String ERROR_HOURS = "Error in time format hours";
    private final static String ERROR_MINUTES = "Error in time format minutes";
    private final static String ERROR_SECONDS = "Error in time format seconds";
    private final static String ERROR_LENGTH = "Error in time format length";
    private final static String FILE_PATH = "D:/input.txt";
    private final static String OUTPUT_PATH = "D:/output.txt";
    public static boolean timeError;

    public static void main(String[] args) throws IOException, UncheckedIOException {

        generateRandomData(FILE_PATH); //Генерируем исходную информацию для последующей сортировки
        long startTime = System.currentTimeMillis(); // замеряем время, чтобы потом подсчитать сколько времени затратила программа на сортировку
        ArrayList<int[]> input = convertInputData(FILE_PATH); //конвертируем исходные данные в лист массивов
        if(!timeError){ // если исходные данные соответствуют условию продолжаем работу
            HashMap<Integer, int[]> sortedData = sortData(input); //преобразовываем лист к мапе, вида (ключ-время в секундах, значение-массив чисел)
            writeSortedData(sortedData,OUTPUT_PATH);//сохраняем в файл отсортированную мапу по ключу.
            long endTime   = System.currentTimeMillis();
            long totalTime = (endTime - startTime);
            System.out.println("Data sort took "+totalTime+" milliseconds.");// время выполнения программы
        }
        else {
            System.out.println("Program error during converting data.");
        }

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
                            if(strArray.length>3){
                                System.out.println(ERROR_LENGTH);
                                timeError = true;
                                return;
                            }
                            int[] intArray = new int[strArray.length];
                            if(checkHH(Integer.parseInt(strArray[0]))) {
                                intArray[0] = Integer.parseInt(strArray[0]);
                                if(checkMMandSS(Integer.parseInt(strArray[1]))){
                                    intArray[1] = Integer.parseInt(strArray[1]);
                                    if(checkMMandSS(Integer.parseInt(strArray[2]))){
                                        intArray[2] =  Integer.parseInt(strArray[2]);
                                    }
                                    else {
                                        System.out.println(ERROR_SECONDS);
                                        stream.close();
                                        timeError = true;
                                        return;
                                    }
                                }
                                else {
                                    System.out.println(ERROR_MINUTES);
                                    stream.close();
                                    timeError = true;
                                    return;
                                }
                            }
                    else {
                                System.out.println(ERROR_HOURS);
                                stream.close();
                                timeError = true;
                                return;
                            }


                            input.add(intArray);



                        }

                );
                if(!timeError){
                System.out.println("Data converted successfully.");
                }
            } catch (UncheckedIOException e) {

            }
        } catch (IOException e) {

        }

        return input;
    }

    public static void  generateRandomData(String outputPath){
        try {
            System.out.println("Start generating random data...");
            FileWriter  fstream = new FileWriter(outputPath);
            BufferedWriter out = new BufferedWriter(fstream);

            int rangeHH = 24;
            int rangeSSandMM = 60;

            for (int i = 0; i < 36000; i++) {

                out.write((int)(Math.random() * rangeHH)+" : "+(int)(Math.random() * rangeSSandMM)+" : "+(int)(Math.random() * rangeSSandMM)+ System.lineSeparator());

            }
            System.out.println("Random data created.");
            out.close();


        } catch (IOException e) {
            System.out.println("Error during generating random data.");
        }
    }

    public static boolean checkHH(int hours){
        return hours<24?true:false;
    }

    public static boolean checkMMandSS(int time){
        return time<60?true:false;
    }


}
