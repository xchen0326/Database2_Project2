import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ArrayIndex {

    String[] arr;

    public ArrayIndex(){
        arr = new String[5000];
    }

    public void initialize() throws IOException{
        for (int i = 0; i < 5000; i++){
            arr[i] = "";
        }
        for (int i = 1; i < 100; i++){//a total of 99 files
            String content = readEachFile(i);
            for (int j = 1; j < 101; j++){// each file has a total of 100 records
                String index = readEachIndex(content, j);
                String value = "";
                String s = "";
                if (40*(j-1)<10){
                    s+="000";
                }
                else if (40*(j-1)<100){
                    s+="00";
                }
                else if (40*(j-1)<1000){
                    s+="0";
                }
                if (i<10){
                    value += "0"+i+s+40*(j-1);
                }
                else value += i+s+40*(j-1);
                if (arr[Integer.valueOf(index)-1].equals("")){
                    arr[Integer.valueOf(index)-1] = value;
                }
                else {
                    String oldVal = arr[Integer.valueOf(index)-1];
                    String newVal = oldVal + value;
                    arr[Integer.valueOf(index)-1] = newVal;
                }
            }
        }
    }

    public String readEachFile(int fileId) throws IOException {
        String parentDir = System.getProperty("user.dir");
        String currentDir = parentDir+"/Project2Dataset/F"+fileId+".txt";

        File newfile = new File(currentDir);
        BufferedReader reader = new BufferedReader(new FileReader(newfile));
        String result = reader.readLine();
        return result;
    }

    /**
     * Index represents random v
     * @param content
     * @param recId
     * @return
     */
    public String readEachIndex(String content, int recId){
        return content.substring(40*(recId-1)+33, 40*(recId-1)+37);
    }

    public static void main(String[] args) throws IOException {
        ArrayIndex arrayIndex = new ArrayIndex();
        arrayIndex.initialize();
        System.out.println();
    }
}
