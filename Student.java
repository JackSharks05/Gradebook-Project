/*
Jack de Haan
Mr. Cruté
AT1- E Block
October 7, 2022
Student.java

The Student Class holds information for individual students: their name, list of their Grades (as Grade Objects), and a list of their Classes (as Class Objects).
Most of the methods are to set or get these attributes.
*/
import java.util.*;
public class Student{
  String name;
  ArrayList<Grade> grade; //Stored as (class, assignment, grade)
  ArrayList<Class> classList;
  //The constructors are used at various times in the various Gradebook.java methods for various reasons.
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
  //The following methods get and set the various attributes of the Student object.
  public String getName(){return this.name;}
  public void setName(String input){this.name = input;}
  /*I didn't include first and last names because there was no need for them in my interpretation of the assignment. However, the methods are here.
  public String getFirstName(){return (this.name).substring(0,(this.name).indexOf(" "));}
  public String getSurname(){return (this.name).substring(((this.name).indexOf(" ") + 1),(this.name).length());}*/
  public void addClass(Class c){this.classList.add(c);}
  public void removeClass(String i){for (Class c : classList){if (c.getName().equalsIgnoreCase(i)){this.classList.remove(i);}}}
  public void setClassList(ArrayList<Class> classes){this.classList = classes;}
  public ArrayList<Class> getClassList(){return this.classList;}

  public static int findAssignment(Class c,String name){
    if (c.getAssignments().size() == 0){return -1;}
    for (int i = 0; i < c.getAssignments().size(); i++){if (name.equals(c.getAssignments().get(i).getName())){return i;}}
    return -1;
  }
  public void addGrade(Grade g){this.grade.add(g);}
  public void setGrade(Class c, Assignment a, Student s, double g){
    boolean exists = false;
    for (int i = 0; i < grade.size(); i++){
      if (grade.get(i).getClassName().equalsIgnoreCase(c.getName()) && grade.get(i).getAssignmentName().equalsIgnoreCase(a.getName()) && grade.get(i).getStudentName().equalsIgnoreCase(s.getName())){
        grade.set(i,new Grade(c,a,s,g));
        exists = true;
        break;
      }
    }
    if (!exists){grade.add(new Grade(c, a, s, g));}
  }
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
  public ArrayList<Grade> getGradeList(){return this.grade;}
  public double getGrade(Class c, Assignment a, Student s, ArrayList<Grade> gl){
    for (Grade g : gl){
      String[] words = g.toString().split(";");
      if (words[0].equals(c.getName()) && words[1].equals(a.getName()) && words[2].equals(s.getName())){
        if (words[3].substring(words[3].indexOf(".")+1,words[3].length()).length() > 2){return Double.parseDouble(words[3].substring(0,words[3].indexOf(".") + 3));}
        return Double.parseDouble(words[3]);
      }
    }
    return -1.0;
  }
  //The toString method provides the FileWriter in the save funciton of the Gradebook Class the information to print into the GlobalRoster.gdbk file.
  public String toString(){
    String str = this.name + ";";
    for (int i = 0; i < classList.size(); i++){
      str += classList.get(i).getName() + ",";
    }
    return str.substring(0,str.length() - 1);
  }

}
