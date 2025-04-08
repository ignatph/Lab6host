package utillity;

import java.util.Scanner;

public class Reader {
    private final ProtectedScanner scanner = new ProtectedScanner();

    public String nextLine() {
        return scanner.nextLine();
    }
}
class ProtectedScanner{
    Scanner sc = new Scanner(System.in);
    public String nextLine(){
        try{
            return sc.nextLine();
        } catch (RuntimeException e){
            System.out.println("Ввод завершен");
            System.exit(-1);
            return "";
        }
    }
}
