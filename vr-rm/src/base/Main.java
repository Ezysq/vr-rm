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
                    if(rm.VMExists())
                        rm.executeProgram();
                }
                else if(arg.startsWith("Debug")){
                    if(rm.VMExists())
                        rm.debugProgram();
                }
                else if(arg.startsWith("Print memory")){
                    if(rm.VMExists())
                        rm.printMemory();
                }
                else if(arg.startsWith("Print VM memory")){
                    if(rm.VMExists())
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
 * DONE padaryti debug mode
 * DONE perdarity, kad butu naudojami isoriniu irenginiu busenos(registrai)
 * TODO realizuoti interuptus                                                     -- SONATA
 * TODO realizuoti vartotojo ir supervizoriaus rezimus                            -- SONATA
 * TODO realizuoti kazka su kietuoju disku
 * DONE registru perdavimai su bendra atmintimi
 * DONE semaforus
 * TODO padaryti be System.out.println( kad vaiksciotu spausdinimas per kanalu iregini(reikes 4 lab))
 * TODO padaryti kad programa uzkrautu per kanalu irengini
 * DONE padaryti, kad kai nera nieko uzkrauta neuzluztu "Print memmory"
 * TODO Padaryt exceptionus
 * Papildomi
 * DONE Laisvi puslapiai parenkami atsitiktine tvarka
 * DONE Leistų užkrauti kitas vartotojų programas ir valdymas būtų atiduodamas vėliausiai užkrautajai (jei laisvos atminties nepakanka apie tai pranešama)
 **/
