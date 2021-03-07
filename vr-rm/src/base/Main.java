package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        RM rm = new RM();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("Command lsit:"); // TODO parasyti kokias komadnas bus galima atlikti
            try{ // TODO padaryti main loopa kur bus galima ivedinet komandas
                String arg = reader.readLine();
                System.out.println(arg);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
