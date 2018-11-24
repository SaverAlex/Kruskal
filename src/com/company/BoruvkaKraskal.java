package com.company;

import java.util.*;

import static com.company.Main.matrix;
import static com.company.Main.vertex;

public class BoruvkaKraskal {

    static HashMap<Integer,ArrayList> used = new HashMap<>();
    public static HashMap<Integer,String> result = new HashMap<>();
    static boolean stoper = true;
    public static int sum = 0;
    static int count = 1;


    public static void bk() {
        String zero = "";
        for (int i = 1; i <= vertex; i++){
            result.put(i,zero);
        }
        while (stoper) {
            process();
        }
        sortedHashMap();
    }

    public static void process () {
        int min = 10000;
        int x = -1;
        int y = -1;
        for (int i = 1; i <= vertex; i++){
            for (int j = 1 + i; j <= vertex; j++){
                if (matrix[i][j] != -1 && (matrix[i][j]<min)) {
                    min = matrix[i][j];
                    x = i;
                    y = j;
                }
            }
        }
        if (min == 10000){
            stoper = false;
            return;
        }
        arrangementVertex(min,x,y);

    }

    public static void arrangementVertex (int ver, int x, int y) {
        if (checkUsedVertexUpdate(x,y)){ // Проверяем наличие в  UsedVertex
            sum = sum + ver;
            String times = result.get(x) + y + " ";
            result.put(x,times);
            times = result.get(y) + x + " ";
            result.put(y,times);
        }
        Main.matrix[x][y] = -1; // Запомнить использованные значения из матрицы

    }


    public static boolean checkUsedVertexUpdate(int x, int y) {
        int one = setUpdate(x);
        int two = setUpdate(y);
        if ((one == two) && (one != -1)){
            return false;
        }
        if ((one == two) && (one == -1)){
            ArrayList times = new ArrayList();
            times.add(x);
            times.add(y);
            used.put(count,times);
            count++;
            return true;
        }
        if (one != two){
            if ((one != -1) && (two != -1)){
                ArrayList oneAr = used.get(one);
                ArrayList twoAr = used.get(two);
                for (int i = 0; i < twoAr.size(); i++){
                    oneAr.add(twoAr.get(i));
                }
                used.remove(two);
                return true;
            }
            if ((one == -1) && (two != -1)){
                used.get(two).add(x);
                return true;
            }

            if ((one != -1) && (two == -1)){
                used.get(one).add(y);
                return true;
            }
        }
        return false;
    }

    private static int setUpdate (int value){
        for (Map.Entry<Integer,ArrayList> time : used.entrySet()){
            ArrayList name = time.getValue();
            for (int j = 0; j < name.size(); j++){ // Нулевой элемент это название компонент связности
                if (value == (int)name.get(j)){
                    return time.getKey();
                }
            }
        }
        return -1;
    }

    public static void sortedHashMap () {
        for (Map.Entry<Integer,String> time : result.entrySet()){
            String proc = time.getValue();
            int ind = proc.indexOf(" ");
            ArrayList sorted = new ArrayList();
            while (ind != -1){
                sorted.add(Integer.parseInt(proc.substring(0,ind)));
                proc = proc.substring(ind+1);
                ind = proc.indexOf(" ");
            }
            String resultS = "";
            sorted.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            for (int i = 0; i < sorted.size(); i++){
                resultS = resultS + sorted.get(i) + " ";
            }
            resultS = resultS + 0;
            result.put(time.getKey(),resultS);
        }
    }

}

