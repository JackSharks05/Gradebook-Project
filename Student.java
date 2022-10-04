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
  public Student(String name, ArrayList<Class> classes, Double grade){System.out.println("please fix");}
  public void setGrade(Class c, Assignment a, Student s, int g){
    grade.add(new Grade(c, a, s, g));
  }
  public int getGrade(Class c, Assignment a, Student s){
    for (Grade g : grade){
      if ((g.getClassName().equals(c.getName())) && (g.getAssignmentName().equals(findAssignment(c,g.getAssignmentName())))){
        return g.getGrade();
      }
    }
    return -1;
  }
  public void addClass(Class c){this.classList.add(c);}
  public void setClassList(ArrayList<Class> classes){this.classList = classes;}
  public String getName(){return this.name;}
  public void setName(String input){this.name = input;}
  public String getFirstName(){return (this.name).substring(0,(this.name).indexOf(" "));}
  public String getSurname(){return (this.name).substring(((this.name).indexOf(" ") + 1),(this.name).length());}
  public String getLetterGrade(int grade){
    if (grade >= 97){return "A+";}
    else if (grade >= 93){return "A";}
    else if (grade >= 90){return "A-";}
    else if (grade >= 87){return "B+";}
    else if (grade >= 83){return "B";}
    else if (grade >= 80){return "B-";}
    else if (grade >= 77){return "C+";}
    else if (grade >= 73){return "C";}
    else if (grade >= 70){return "C-";}
    else if (grade >= 67){return "D+";}
    else if (grade >= 63){return "D";}
    else if (grade >= 60){return "D-";}
    else {return "F";}
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
