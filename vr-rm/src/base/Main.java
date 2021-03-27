package base;

import RM.RM;

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
                if(arg.startsWith("Load")){
                    if(rm.loadProgram("E:\\GIT\\vr-rm\\" + arg.substring(5)) == -1){
                        System.out.println("Nepavyko prideti VM");
                        continue;
                    }
                   // System.out.println(arg.substring(5));
                }
                else if(arg.startsWith("Run")){
                    rm.executeProgram();
                }
                else if(arg.startsWith("Debug")){
                    rm.debugProgram();
                }
                else if(arg.startsWith("Print memory")){
                    rm.printMemory();
                }
                else if(arg.startsWith("Print VM memory")){
                    rm.printVMMemory();
                }
                else if (arg.startsWith("Quit")){
                    break;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
/**
 * DONE padaryt puslapiavimo mechanizma, nenuoseklia antminciu sueiti
 * DONE atspauzdinti VM RM atmintis
 * TODO padaryti debug mode // nepilnai padaryta truksta( Išorinių įrenginių būsenos. spausdinimo)
 * TODO realizuoti interuptus                                                     -- SONATA
 * TODO realizuoti vartotojo ir supervizoriaus rezimus                            -- SONATA
 * TODO realizuoti kazka su kietuoju disku
 * TODO registru perdavimai su bendra atmintimi
 * TODO semaforus
 * TODO padaryti be System.out.println( kad vaiksciotu spausdinimas per kanalu iregini(reikes 4 lab))
 * TODO perdarity, kad butu naudojami isoriniu irenginiu busenos(registrai)
 **/
