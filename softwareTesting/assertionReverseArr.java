import java.util.Arrays;
import java.util.function.*;

class Assertion{
    public static void run(boolean b) throws Exception{
        if (!b) throw new Exception("Assertion error");
    }
}

class Main{
    public static boolean equalAssertion(String[] a, String[] b, BiPredicate<String[], String[]> callback) throws Exception{
        boolean equality = callback.test(a, b);
        System.out.println("Comparing " + Arrays.toString(a) + " and " + Arrays.toString(b) + "..." + (equality? "they are equal!": "they are not equal..."));
        Assertion.run(equality);
        return true;
    }

    // in-placeで配列を反転させる
    public static String[] reverseArr(String[] strArr){
        for (int i = 0; i < strArr.length / 2; i++){
            String temp = strArr[i];
            strArr[i] = strArr[strArr.length - 1 - i];
            strArr[strArr.length - 1 - i] = temp;
        }

        return strArr;
    }

    public static void main(String[] args) throws Exception{
        String[] fruits = {"apple", "banana", "cherry", "dragon fruit"};
        String[] copyFruits = Arrays.copyOf(fruits,fruits.length);  // reverseArrはin-placeで配列を反転させるからdeepcopyを作成する

        BiPredicate<String[], String[]> areOppositeOrder = (a, b) -> {
            if (a.length != b.length) return false;
            for (int i = 0; i < a.length / 2; i++){
                if (a[i] != b[b.length - 1 - i]) return false;
            }

            return true;
        };

        reverseArr(copyFruits);
        equalAssertion(fruits, copyFruits, areOppositeOrder);
    }
}
