import java.time.LocalDate;
import java.util.*;
import java.util.function.*;

class Assertion{
    public static void run(boolean b) throws Exception{
        if (!b) throw new Exception("アサーションエラー");
    }
}

class Donation{

    public String name;
    public int price;
    public int donationNumber;
    public LocalDate donationDay;

    public Donation(String name, int price, int donationNumber, int day, int month, int year) {
        this.name = name;
        this.price = price;
        this.donationNumber = donationNumber;
        this.donationDay = LocalDate.of(year, month, day);
    }

    public String toString() {
        return "name: " + this.name + " price:" + this.price + " donationNumber: " + this.donationNumber + " day: " + this.donationDay;
    }

    public int getPrice(){
        return this.price;
    } 
}

class Main{
    public static boolean equalAssertion(Donation a, Donation b, BiPredicate<Donation, Donation> callback) throws Exception{
        boolean equality = callback.test(a, b);
        Assertion.run(equality);
        return true;
    }

    public static void main(String[] args) throws Exception{
        ArrayList<Donation> testcase = new ArrayList<>(){
            {
                add(new Donation("Steve Jobs", 50000, 1, 21, 3, 2021));
                add(new Donation("Bill Gatess", 40000, 2, 2, 9, 2021));
                add(new Donation("Mark Elliot Zuckerberg", 40000, 3, 29, 4, 2021));
                add(new Donation("Jeffrey Preston Bezos", 60000, 4, 1, 2, 2021));
                add(new Donation("Steve Jobs", 10000, 5, 19, 5, 2021));
            }
        };

        Function<ArrayList<Donation>, Donation> highestDonation = arr -> {
            int highestIdx = 0;
            for (int i = 1; i < arr.size(); i++){
                if (arr.get(i).price > arr.get(highestIdx).price) highestIdx = i;
            }

            return arr.get(highestIdx);
        };

        BiPredicate<Donation, Donation> areSamePrice = (a, b) -> a.price == b.price;

        // testcaseをprice昇順にソートする
        ArrayList<Donation> copyDonationList = new ArrayList<>();
        copyDonationList.addAll(testcase);
        copyDonationList.sort(Comparator.comparing(Donation::getPrice));
        Donation highest = highestDonation.apply(testcase);

        System.out.println(equalAssertion(highest, copyDonationList.get(copyDonationList.size() - 1), areSamePrice));  // true
    }
}
