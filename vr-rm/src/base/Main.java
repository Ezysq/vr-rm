package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        RM rm = new RM();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("Command list:"); // TODO parasyti kokias komandas bus galima atlikti
            try{ // TODO padaryti main loopa kur bus galima ivedinet komandas
                String arg = reader.readLine();
                System.out.println(arg);
                if(arg.substring(0, 4).equals("Load")){
                    rm.loadProgram("E:\\GIT\\vr-rm\\" + arg.substring(5));
                    rm.executeCommand();
                   // System.out.println(arg.substring(5));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
/**
 * TODO padaryti debug mode
 * TODO realizuoti interuptus                                                     -- SONATA
 * TODO realizuoti vartotojo ir supervizoriaus rezimus                            -- SONATA
 * TODO padaryt puslapiavimo mechanizma, nenuoseklia antminciu sueiti
 * TODO atspauzdinti VM RM atmintis
 * TODO realizuoti kazka su kietuoju disku
 * TODO registru perdavimai su bendra atmintimi
 * TODO semaforus
 **/
