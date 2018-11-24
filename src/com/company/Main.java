package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static ArrayList all = new ArrayList(); // Просто все значения из входного файла
    public static HashMap<Integer,ArrayList> graph = new HashMap<>(); // Заполненный граф
    public static int amountOfElements; // Кол-во элементов
    public static int[][] matrix;
    public static int vertex;

    public static void main(String[] args) throws IOException {
        Reader();
        makeList();
        BoruvkaKraskal.bk();
        writeResult();
    }

    public static void Reader(){
        try{
            FileInputStream fstream = new FileInputStream("in.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            amountOfElements = Integer.parseInt(br.readLine());
            String strLine =  br.readLine();
            while (strLine != null){ // Идём по строкам
                Parser(strLine);
                strLine = br.readLine();
            }
            //System.out.println(all);
        }catch (IOException e){
            System.out.println("Ошибка");
        }
    }

    public static void Parser (String str) {
        while (str != "") {
            int num = str.indexOf(" "); // Ищем пробелы
            if (num == -1){
                break;
            }
            String times = str.substring(0, num); // Берём нужную часть
            str = str.substring(num+1); // Избавляемся от точо что уже записали
            all.add(Integer.parseInt(times)); // Добавляем в массив
        }
        all.add(Integer.parseInt(str));

    }

    public static void makeList (){
        for (int i = 0; i < all.size(); i++) { // Идём по массиву со всеми элементами
            ArrayList listV  = new ArrayList(); // Массив со всеми смежными вершинами
            int el = (int) all.get(i);// Элемент-указатель
            if (el == amountOfElements){ // если указатель указывает на последний элемент то прекращаем
                break;
            }
            int el2 = (int) all.get(i+1);// Смотрим разницу между двумя указателями
            for (int j = 0; j < (el2 - el); j=j+2){ // Идём по всем смежным вершинам
                HashMap<Integer,Integer> adjacent = new HashMap<>();
                adjacent.put((int)all.get(el-1+j),(int)all.get(el+j));
                listV.add(adjacent); // Записывем их в общий список
            }
            graph.put(i+1,listV); // Заполняем граф
        }
        vertex = graph.size();
        makeMatrix();
    }

    public static void makeMatrix(){
        matrix = new int[vertex+1][vertex+1];
        for (int x = 0; x < vertex+1; x++){
            for (int y = 0; y < vertex+1; y++){
                matrix[x][y] = -1;
            }
        }
        for (Map.Entry<Integer, ArrayList> time : Main.graph.entrySet()) {
            int x = time.getKey();
            int y;
            for (int j = 0; j < time.getValue().size(); j++){
                HashMap<Integer,Integer> name = (HashMap<Integer, Integer>) time.getValue().get(j);
                for (Map.Entry<Integer, Integer> time2 : name.entrySet()){
                    y = time2.getKey();
                    matrix[x][y] = time2.getValue();
                }
            }
        }
    }

    public static void writeResult () throws IOException {
        File file = new File("out.txt");
        // Создание файла
        file.createNewFile();
        // Создание объекта FileWriter
        FileWriter writer = new FileWriter(file);
        for (Map.Entry<Integer,String> time : BoruvkaKraskal.result.entrySet()){
            writer.write(time.getKey() + " " + time.getValue()+"\n");
        }
        writer.write(String.valueOf(BoruvkaKraskal.sum));
        writer.flush();
        writer.close();
    }
}
