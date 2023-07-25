import java.util.ArrayList;

class Student{
    public int studentId;
    public int grade;
    public String name;
    public int age;
    public double height;

    public Student(int studentId, int grade, String name, int age, double height){
        this.studentId = studentId;
        this.grade = grade;
        this.name = name;
        this.age = age;
        this.height = height;
    }

    public String toString(){
        return "id: " + this.studentId + ", grade: " + this.grade + ", name: " + this.name + ", age: " + this.age + ", height: " + this.height;
    }
}

class Main{
    public static void swap(ArrayList<Student> studentArr, int i, int j){
        Student temp = studentArr.get(i);
        studentArr.set(i, studentArr.get(j));
        studentArr.set(j, temp);
    }

    // s1がs2より若く、身長が大きいかどうかをbooleanで返す
    // s1とs2が年齢も身長も同じ場合、idの若さで比較
    public static boolean studentCompare(Student s1, Student s2){
        if (s1.age == s2.age){
            return s1.height == s2.height? s1.studentId < s2.studentId: s1.height > s2.height;
        }

        return s1.age < s2.age;
    }

    // 生徒の年齢と身長を条件に最小ヒープを作成する
    public static void minHeap(ArrayList<Student> studentArr, int idx){
        int l = 2 * idx + 1;
        int r = 2 * idx + 2;
        int smallest = idx;

        // studentCompare()を使用して生徒を比較
        if (l < studentArr.size() && !studentCompare(studentArr.get(smallest), studentArr.get(l))) smallest = l;
        if (r < studentArr.size() && !studentCompare(studentArr.get(smallest), studentArr.get(r))) smallest = r;

        if (smallest != idx){
            swap(studentArr, idx, smallest);
            minHeap(studentArr, smallest);
        }
    }

    // 年齢が一番若い学生のうち、身長が一番大きい生徒
    public static void heapify(ArrayList<Student> dArr){
        int mid = (dArr.size() - 1) / 2;
        for (int i = mid; i >= 0; i--) minHeap(dArr, i);
    }

    // 最年少かつ最も高い生徒をk人返す
    public static ArrayList<Student> chooseStudent(ArrayList<Student> studentArr, int k){
        // 入力studentArrのディープコピーを作成する
        ArrayList<Student> copyArr = new ArrayList<>();
        copyArr.addAll(studentArr);
        heapify(copyArr);

        ArrayList<Student> results = new ArrayList<Student>();
        for (int i = 0; i < k; i++){
            swap(copyArr, 0, copyArr.size()-1);
            results.add(copyArr.remove(copyArr.size() - 1));

            // ソート時に根ノードと最後のノードをswap()で交換するから、再度ヒープ条件を満たすようにminHeap()を実行する
            if (copyArr.size() > 0) minHeap(copyArr, 0);
            else break;
        }

        return results;
    }

    public static boolean areStudentListsEquals(ArrayList<Student> sList1, ArrayList<Student> sList2){
        return sList1.equals(sList2);

        // if (sList1.size() != sList2.size()) return false;
        // for (int i = 0; i < sList1.size(); i++){
        //     if (sList1.get(i).studentId != sList2.get(i).studentId || sList1.get(i).grade != sList2.get(i).grade || sList1.get(i).name != sList2.get(i).name || sList1.get(i).age != sList2.get(i).age || sList1.get(i).height != sList2.get(i).height) return false;
        // }
        
        // return true;
    }

    public static void printStudents(ArrayList<Student> studentArr){
        System.out.println("---" + studentArr.size() + "---");
        for (Student student: studentArr) System.out.println(student);
        System.out.println("--- END ---");

    }

    public static void main(String[] args){

        ArrayList<Student> studentList1 = new ArrayList<>(){
            {
                add(new Student(1000,9,"Matt Verdict", 14, 5.5));
                add(new Student(1001,9,"Amy Lam", 14, 5.5));
                add(new Student(1002,10,"Bryant Gonzales", 15, 5.9));
                add(new Student(1003,9,"Kimberly York", 15, 5.3));
                add(new Student(1004,11,"Christine Bryant", 15, 5.8));
                add(new Student(1005,10,"Mike Allen", 16, 6.2));
            }
        };

        // // white-box testingにより、chooseStudent()実行時に入力配列に変更（副作用）が加えられていないかテストする
        ArrayList<Student> copyStudentList1 = new ArrayList<>();
        copyStudentList1.addAll(studentList1);
        System.out.println(chooseStudent(copyStudentList1, 1).get(0).studentId == 1000);  // true
        System.out.println(areStudentListsEquals(studentList1, copyStudentList1));  // ture
        

        ArrayList<Student> studentList2 = new ArrayList<>(){
            {
                add(new Student(1000,9,"Matt Verdict", 14, 5.5));
                add(new Student(1001,9,"Amy Lam", 13, 5.5));
                add(new Student(1002,10,"Bryant Gonzales", 15, 5.9));
                add(new Student(1003,9,"Kimberly York", 15, 5.3));
                add(new Student(1004,11,"Christine Bryant", 15, 5.8));
                add(new Student(1005,10,"Mike Allen", 16, 6.2));

            }
        };

        ArrayList<Student> copyStudentList2 = new ArrayList<>();
        copyStudentList2.addAll(studentList2);
        System.out.println(chooseStudent(copyStudentList2, 1).get(0).studentId == 1001);  // true
        System.out.println(areStudentListsEquals(studentList2, copyStudentList2));  // ture


        ArrayList<Student> studentList3 = new ArrayList<>(){
            {
                add(new Student(1000,9,"Matt Verdict", 11, 5.5));
                add(new Student(1001,9,"Amy Lam", 13, 5.5));
                add(new Student(1002,10,"Bryant Gonzales", 13, 5.5));
                add(new Student(1003,9,"Kimberly York", 18, 5.3));
                add(new Student(1004,11,"Christine Bryant", 15, 5.3)); 
                add(new Student(1005,10,"Mike Allen", 12, 6.2));

            }
        };
        // 最年少かつ最も身長の大きい生徒: [1000, 1005, 1001, 1002, 1004, 1003]

        printStudents(studentList3);
        printStudents(chooseStudent(studentList3,4));
        printStudents(chooseStudent(studentList3,6));
        printStudents(studentList3);  // chooseStudent()内で入力配列のディープコピーをしているから副作用が発生していない
    }
}
