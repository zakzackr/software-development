import java.util.function.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;

class Assertion{
    public static void run(boolean b) throws Exception{
        if (!b) throw new Exception("Assertion Error...");
    }
}

class Main{

    public static void main(String[] args) throws Exception{
        // テストを実行する関数
        BiPredicate<ArrayList<Integer>, Predicate<ArrayList<Integer>>> assertionTest = (a, callback) -> {
            boolean result = callback.test(a);
            System.out.println("Checking against " + a + ", is it valid?..." + (result? true: false));
            try{
                Assertion.run(result);
            } catch(Exception e){
                System.out.println(e);
            }

            return true;
        };

        // テストしたい関数: n未満の素数を配列で返す
        Function<Integer, ArrayList<Integer>> sieveOfPrimes = n -> {
            boolean[] cache = new boolean[n];
            Arrays.fill(cache, true);

            for (int currentPrime = 2; currentPrime <= Math.floor(Math.sqrt(n)); currentPrime++){
                if (!cache[currentPrime]) continue;

                int i = 2;
                while (currentPrime * i < n){
                    if (cache[currentPrime * i]) cache[currentPrime * i] = false;
                    i++;
                }
            }

            ArrayList<Integer> primeArr = new ArrayList<>();
            for (int i = 2; i < cache.length; i++){
                if (cache[i]) primeArr.add(i);
            }

            return primeArr;
        };

        // リスト内の数字が素数であるか、重複していないか、すべてが n 以下であるか、k 個の素数が含まれているかどうかをチェックする
        // この自動テストでは、n未満に素数がk個あるという事前知識が必要
        BiFunction<Integer, Integer, Predicate<ArrayList<Integer>>> primeCheck = (n, k) -> {
            Predicate<Integer> isPrime = num -> {
                for (int i = 2; i <= Math.floor(Math.sqrt(num)); i++){
                    if (num % i == 0) return false;
                }

                return num > 1;
            };

            Predicate<ArrayList<Integer>> script = aList -> {
                HashSet<Integer> hashset = new HashSet<>();
                hashset.addAll(aList);
                if (hashset.size() != aList.size()) return false;
                if (aList.size() != k) return false;
                for (int i = 0; i < aList.size(); i++){
                    if (aList.get(i) > n || !isPrime.test(aList.get(i))) return false;
                }

                return true;
            };

            return script;
        };

        // 15未満の素数のリスト
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(2, 3, 5, 7, 11, 13));
        assertionTest.test(list, primeCheck.apply(15, 6));
        assertionTest.test(sieveOfPrimes.apply(15), primeCheck.apply(15, 6));  
        assertionTest.test(sieveOfPrimes.apply(100), primeCheck.apply(100, 25));  
        assertionTest.test(sieveOfPrimes.apply(10000), primeCheck.apply(10000, 1229));  

        assertionTest.test(new ArrayList<Integer>(Arrays.asList(2, 3, 5, 7, 9, 11, 14)), primeCheck.apply(15, 6));  // false
    }
}
