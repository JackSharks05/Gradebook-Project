/*
Jack de Haan
Mr. Cruté
AT1- E Block
October 7, 2022
Grade.java

The Grade class stores information for a specific student's grade; its main function is to access the numerical value of an assignment grade, given the class, assignment (and hence its weight), and the student to which to assign the grade.
*/

import java.util.*;
public class Grade{
  Class localclass;
  Assignment localassignment;
  Student localstudent;
  double grade;
  //The constructor is only used once, when the startup method reads in information from the Gradebook.gdbk file.
  public Grade(Class inputclass, Assignment inputassignment, Student inputstudent, double inputgrade){
    this.localclass = inputclass;
    this.localassignment = inputassignment;
    this.localstudent = inputstudent;
    this.grade = inputgrade;
  }
  //These methods are used to access the four attributes (or the attributes of the attributes, in the case of the name/type of assignment) of the Grade object.
  public String getClassName(){return this.localclass.getName();}
  public Double getGrade(){return this.grade;}
  public String getAssignmentName(){return this.localassignment.getName();}
  public String getAssignmentType(){return this.localassignment.getType();}
  public String getStudentName(){return this.localstudent.getName();}
  //The toString method gives the FileWriter what to print in the Gradebook.gdbk file.
  public String toString(){
    return this.localclass.getName() + ";" + this.localassignment.getName() + ";" + this.localstudent.getName() + ";" + this.grade;
  }
}
