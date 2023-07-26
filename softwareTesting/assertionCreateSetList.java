import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.function.*;
import java.util.stream.Collectors;

class Assertion{
    public static void run(boolean b) throws Exception{
        if (!b) throw new Exception("Assertion error");
    }
}

class Main{
    public static boolean equalAssertion(String[] a, String[]  b, BiPredicate<String[], String[]> callback) throws Exception{
        boolean equality = callback.test(a, b);
        System.out.println("Comparing " + Arrays.toString(a) + " and " + Arrays.toString(b) + "..."  + (equality? "They are equal!": "They are NOT equal..."));
        Assertion.run(equality);
        return true;
    }

    // 重複を取り除いた配列を返す
    public static String[] createSetList(String[] emails){
        HashMap<String, Integer> hashmap = new HashMap<>();
        for (int i = 0; i < emails.length; i++){
            if (hashmap.get(i) == null) hashmap.put(emails[i], 1);
        }

        int size = hashmap.keySet().size();
        String[] output = hashmap.keySet().toArray(new String[size]);

        return output;
    }

    public static void main(String[] args) throws Exception{
        // System.out.println(emails);
        String[] emails = {"aaa@gmail.com", "abab@gmail.com", "aaa@gmail.com", "ccc@gmail.com"};
        BiPredicate<String[], String[]> hasSameElements = (a, b) -> {
            HashMap<String, Integer> aHash = new HashMap<>();
            HashMap<String, Integer> bHash = new HashMap<>(); 

            for (String i: a) aHash.put(i, aHash.getOrDefault(i, 0) + 1);
            for (String i: b) bHash.put(i, bHash.getOrDefault(i, 0) + 1);

            for (String key: aHash.keySet()){
                if (bHash.get(key) == null) return false;
            }

            return true;
        };

        String[] uniqueEmails = createSetList(emails);
        equalAssertion(emails, uniqueEmails, hasSameElements);  // true

        // streamAPIを用いて重複する要素を省いた配列を作成
        String[] emails2 = {"abc@gmail.com", "abab@gmail.com", "abc@gmail.com", "abab@gmail.com"};
        // String[] copyArr = emails2.clone();  // deepcopyを作成
        List<String> list = Arrays.stream(emails2).distinct().collect(Collectors.toList());  
        String[] uniqueArr = list.toArray(new String[list.size()]);  
        equalAssertion(emails2, uniqueArr, hasSameElements);  // true
    }
}
