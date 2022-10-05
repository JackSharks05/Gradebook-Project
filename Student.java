import java.util.*;
public class Student{
  String name;
  ArrayList<Grade> grade; //class assignment grade
  ArrayList<Class> classList;
  public Student(){}
  public Student(String name){
    this.name = name;
    this.classList = new ArrayList<Class>();
    this.grade = new ArrayList<Grade>();
  }
  public Student(String name, Class c){
    this.name = name;
    this.classList.add(c);
  }
  public Student(String name, ArrayList<Class> classes){
    this.name = name;
    this.classList = classes;
    this.grade = new ArrayList<Grade>();
  }
  public Student(String name, ArrayList<Class> classes, ArrayList<Grade> grade){
    this.name = name;
    this.classList = classes;
    this.grade = grade;
  }
  public void setGrade(Class c, Assignment a, Student s, double g){
    grade.add(new Grade(c, a, s, g));
  }
  public ArrayList<Grade> getGradeList(){return this.grade;}
  // public double getGrade(Class c, Assignment a, Student s){
  //   for (Grade g : grade){
  //     if ((g.getClassName().equals(c.getName())) && (g.getAssignmentName().equals(findAssignment(c,g.getAssignmentName())))){
  //       return g.getGrade();
  //     }
  //   }
  //   return -1;
  // }
  public double getGrade(Class c, Assignment a, Student s, ArrayList<Grade> gl){
    for (Grade g : gl){
      //
      // System.out.println(g);
      // if ((g.getClassName().equals(c.getName())) && (g.getAssignmentName().equals(findAssignment(c,g.getAssignmentName())))){
      //   return g.getGrade();
      // }
      String[] words = g.toString().split(";");
      if (words[0].equals(c.getName()) && words[1].equals(a.getName()) && words[2].equals(s.getName())){
        return Double.parseDouble(words[3]);
      }
    }
    return -1.0;
  }
  public void addGrade(Grade g){this.grade.add(g);}
  public void addClass(Class c){this.classList.add(c);}
  public void setClassList(ArrayList<Class> classes){this.classList = classes;}
  public String getName(){return this.name;}
  public void setName(String input){this.name = input;}
  public String getFirstName(){return (this.name).substring(0,(this.name).indexOf(" "));}
  public String getSurname(){return (this.name).substring(((this.name).indexOf(" ") + 1),(this.name).length());}
  public String getLetterGrade(double grade){
    if (grade >= 97.0){return "A+";}
    else if (grade >= 93.0){return "A";}
    else if (grade >= 90.0){return "A-";}
    else if (grade >= 87.0){return "B+";}
    else if (grade >= 83.0){return "B";}
    else if (grade >= 80.0){return "B-";}
    else if (grade >= 77.0){return "C+";}
    else if (grade >= 73.0){return "C";}
    else if (grade >= 70.0){return "C-";}
    else if (grade >= 67.0){return "D+";}
    else if (grade >= 63.0){return "D";}
    else if (grade >= 60.0){return "D-";}
    else if (grade >= 0){return "F";}
    else {return "Does not exist";}
  }
  public String toString(){
    String str = this.name + ";";
    for (int i = 0; i < classList.size(); i++){
      str += classList.get(i).getName() + ",";
    }
    return str.substring(0,str.length() - 1);
  }
  public static int findAssignment(Class c,String name){
    if (c.getAssignments().size() == 0){return -1;}
    for (int i = 0; i < c.getAssignments().size(); i++){if (name.equals(c.getAssignments().get(i).getName())){return i;}}
    return -1;
  }
}
