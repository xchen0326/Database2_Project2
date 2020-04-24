import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static String getRecord(String content, int recId){
        String record = "";
        if(recId>0&&recId<=100){
            for (int i = (recId-1)*40; i < (recId-1)*40+40; i++){
                record += content.charAt(i);
            }
        }
        return record;
    }

    public static void main(String[] args) throws IOException {
        Scanner reader = new Scanner(System.in);
        Index index = new Index();
        while (reader.hasNext()) {
            String command = reader.nextLine();
            if (command.equals("CREATE INDEX ON RandomV")){
                index.initialize();
                System.out.println("The hash-based and array-based indexes are built¡±");
            }
            else if (command.substring(36, 46).equals("RandomV = ")){
                long startTime=System.currentTimeMillis();
                System.out.println("HashIndex Used.");
                String v = command.substring(46);
                String value = index.hashMap.get(v);
                int count = 0;
                for (int i = 0; i < value.length(); i++){
                    count += 1;
                    String str = value.substring(i, i+6);
                    int fileId = Integer.valueOf(str.substring(0, 2));
                    int offset = Integer.valueOf(str.substring(2, 6));
                    String content = index.readEachFile(fileId);
                    String result = content.substring(offset, offset+40);
                    System.out.println(result);
                    i += 5;
//                    if (i==3998){
//                        break;
//                    }
                }
                System.out.println("A total of "+count+" data files were read.");
                long endTime=System.currentTimeMillis();
                System.out.println("Total time cost: "+String.valueOf(endTime-startTime)+" ms");
            }
            else if (command.substring(44, 45).equals(">")){
                long startTime=System.currentTimeMillis();
                System.out.println("ArrayIndex Used.");
                int i1 = 46;
                int i2 = command.length()-1;
                while (!command.substring(i1+1, i1+2).equals(" ")){
                    i1 += 1;
                }
                while (!command.substring(i2-1, i2).equals(" ")){
                    i2 -= 1;
                }
                int v1 = Integer.valueOf(command.substring(46, i1+1));
                int v2 = Integer.valueOf(command.substring(i2));
                int count = 0;
                for (int i = v1; i <v2-1; i++){
                    String value = index.arr[i];
                    for (int j = 0; j < value.length(); j++){
                        count += 1;
                        String str = value.substring(j, j+6);
                        int fileId = Integer.valueOf(str.substring(0, 2));
                        int offset = Integer.valueOf(str.substring(2, 6));
                        String content = index.readEachFile(fileId);
                        String result = content.substring(offset, offset+40);
                        System.out.println(result);
                        j += 5;
                    }
                }
                System.out.println("A total of "+count+" data files were read.");
                long endTime=System.currentTimeMillis();
                System.out.println("Total time cost: "+String.valueOf(endTime-startTime)+" ms");
            }
            else if (command.substring(44, 46).equals("!=")){
                long startTime=System.currentTimeMillis();
                System.out.println("Full Table Scan Used.");
                String v = command.substring(47);
                int count = 0;
                for (int i = 1; i < 100; i++) {//a total of 99 files
                    String content = index.readEachFile(i);
                    count += 1;
                    for (int j = 1; j < 101; j++) {// each file has a total of 100 records
                        String key = index.readEachKey(content, j);
                        if (!key.equals(v)){
                            System.out.println(getRecord(content, j));
                        }
                    }
                }
                System.out.println("A total of "+count+" data files were read.");
                long endTime=System.currentTimeMillis();
                System.out.println("Total time cost: "+String.valueOf(endTime-startTime)+" ms");
            }
        }
    }
}
