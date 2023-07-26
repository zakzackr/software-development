import java.util.function.*;

class Assertion{
    public static void run(boolean b) throws Exception{
        if (!b) throw new Exception("Assertion Error");
    }
}

class Main{
    public static void main(String[] args) throws Exception{
        BiPredicate<String, Predicate<String>> assertionTest = (a, callback) -> {
            boolean valid = callback.test(a);
            System.out.println("Checking if " + a + " is valid....." + (valid? "Valid!!": "NOT valid..."));
            try{
                Assertion.run(valid);
            } catch (Exception e){
                System.out.println(e);
            }

            return true;
        };

        // 与えられたemailが有効かtestする
        // 有効なemailは空白のスペースがなく、@を1つ含み、@の後に.が含まれる文字列とする
        Predicate<String> isValidEmail = email -> {
            if (email.indexOf(" ") >= 0 || email.indexOf("@") == -1 || email.substring(email.indexOf("@") + 1).indexOf("@") != -1) return false;
            if (email.substring(email.indexOf("@") + 1).indexOf(".") == -1) return false;
            return true;
        };

        assertionTest.test("abcdef@test.com", isValidEmail);
        assertionTest.test("abcdef @test.com", isValidEmail);  // error

        // 与えられた文字が全て大文字かどうかtestする
        Predicate<String> areAllUppercase = str -> str == str.toUpperCase();
        assertionTest.test("aPPle", areAllUppercase);
        assertionTest.test("AUSTRALIA", areAllUppercase);
    }
}
