import java.io.*;
import java.util.HashMap;

public class HashIndex {
    HashMap<String, String> hashMap;

    public HashIndex(){
        hashMap = new HashMap<>();
    }

    public void initialize() throws IOException {
        for (int i = 1; i < 100; i++){//a total of 99 files
            String content = readEachFile(i);
            for (int j = 1; j < 101; j++){// each file has a total of 100 records
                String key = readEachKey(content, j);
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
                if (!hashMap.containsKey(key)){
                    hashMap.put(key, value);
                }
                else {
                    String oldVal = hashMap.get(key);
                    String newVal = oldVal + value;
                    hashMap.put(key, newVal);
                }
            }
        }
    }

    //public String readEachOffset(){}

    public String readEachFile(int fileId) throws IOException {
        String parentDir = System.getProperty("user.dir");
        String currentDir = parentDir+"/Project2Dataset/F"+fileId+".txt";

        File newfile = new File(currentDir);
        BufferedReader reader = new BufferedReader(new FileReader(newfile));
        String result = reader.readLine();
        return result;
    }

    /**
     * Key represents random v
     * @param content
     * @param recId
     * @return
     */
    public String readEachKey(String content, int recId){
        return content.substring(40*(recId-1)+33, 40*(recId-1)+37);
    }

    public HashMap getHashMap(){
        return this.hashMap;
    }

    public static void main(String[] args) throws IOException {
        HashIndex hashIndex = new HashIndex();
//        String result = hashIndex.readEachFile(99);
//        String record = hashIndex.readEachKey(result, 100);
//        System.out.println(record);
        hashIndex.initialize();
        System.out.println(hashIndex.getHashMap().get("4037"));
    }
}
