import java.util.Arrays;
import java.util.function.*;
import java.util.HashMap;

class Assertion{
    public static void run(boolean b) throws Exception{
        if (!b) throw new Exception("Assertion Error");
    }
}

class Main{
    // callback関数を変更することで、1つのテスト関数で異なる比較を行うことができる
    public static boolean equalAssertion(int[] a, int[] b, BiPredicate<int[], int[]> callback) throws Exception{
        boolean equality = callback.test(a, b);
        System.out.println("Comparing " + Arrays.toString(a) + " and " + Arrays.toString(b) + " ... " + (equality? "They are equal!": "They are not equal..."));
        Assertion.run(equality);
        return true;
    }

    public static void main(String[] args) throws Exception{
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = {2, 5, 3, 4, 1};
        int[] arr3 = {1, 2, 3, 4, 5};

        BiPredicate<int[], int[]> orderedArrayEquality = (a, b) -> {
            if (a.length != b.length) return false;
            for (int i = 0; i < a.length; i++){
                if (a[i] != b[i]) return false;
            }

            return true;
        };

        BiPredicate<int[], int[]> unorderedArrayEquality = (a, b) -> {
            if (a.length != b.length) return false;
            HashMap<Integer, Integer> hashA = new HashMap<>();
            HashMap<Integer, Integer> hashB = new HashMap<>();
            
            for (int i = 0; i < a.length; i++){
                hashA.put(a[i], hashA.getOrDefault(a[i], 0) + 1);
                hashB.put(b[i], hashB.getOrDefault(b[i], 0) + 1);
            }

            for (Integer key: hashA.keySet()){
                if (hashB.get(key) == null) return false;
                if (hashA.get(key) != hashB.get(key)) return false;
            }
            
            return true;
        };

        equalAssertion(arr1, arr3, orderedArrayEquality);
        equalAssertion(arr1, arr2, unorderedArrayEquality);
        // equalAssertion(arr1, arr2, orderedArrayEquality);  //fail
    }
}
