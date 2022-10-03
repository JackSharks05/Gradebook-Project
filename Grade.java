import java.util.*;

public class Grade{
  Class localclass;
  Assignment localassignment;
  Student localstudent;
  int grade;
  public Grade(Class inputclass, Assignment inputassignment, Student inputstudent, int inputgrade){
    this.localclass = inputclass;
    this.localassignment = inputassignment;
    this.localstudent = inputstudent;
    this.grade = inputgrade;
  }
  public Class getClassObject(){return this.localclass;}
  public String getClassName(){return this.localclass.getName();}
  public int getGrade(){return this.grade;}
  public String getAssignmentName(){return this.localassignment.getName();}
  public String toString(){
    return this.localclass.getName() + ";" + this.localassignment.getName() + ";" + this.localstudent.getName() + ";" + this.grade;
  }
}
