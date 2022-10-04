import java.io.*;
import java.util.*;
public class Gradebook{
  public static ArrayList<Student> globalroster;
  public static ArrayList<Class> classes;
  public static ArrayList<Grade> gradelist;
  public static void main(String[] args){globalroster = new ArrayList<Student>();classes = new ArrayList<Class>();gradelist = new ArrayList<Grade>();startGUI(false);}
public static boolean isYes(String input){
  String[] yes = {"Y","sure","ok"};
  if ((input.toUpperCase()).startsWith("N")){return false;}
  else {
    for (String y : yes){if (input.equalsIgnoreCase(y)||input.toUpperCase().startsWith((y.charAt(0)+"").toUpperCase())){return true;}}
  }
  return false;
}
public static void startGUI(boolean repeating){
  if (!repeating){startup();}
  Scanner guiScanner = new Scanner(System.in);
  System.out.print("Would you like to make a new class? ");
  if (isYes(guiScanner.nextLine())){classCreateGUI("");}
  System.out.print("Would you like to make a new student? ");
  if (isYes(guiScanner.nextLine())){studentCreateGUI("");}
  System.out.print("Would you like to make a new assignment? ");
  if (isYes(guiScanner.nextLine())){assignmentGUI();}
  System.out.print("Would you like to edit a class? ");
  if (isYes(guiScanner.nextLine())){classEditGUI();}
  System.out.print("Would you like to edit a student? ");
  if (isYes(guiScanner.nextLine())){studentEditGUI();}
  System.out.print("Anything else? ");
  if (isYes(guiScanner.nextLine())){startGUI(true);} else {
  System.out.print("Would you like to save all changes made? ");
  if (isYes(guiScanner.nextLine())){save();System.out.print("Saving");for (int i = 0; i < 4; i++){System.out.print(".");}System.out.println(" Saved.");}
  System.exit(1);
}
}
public static void classCreateGUI(String name){//create class
  FileWriter fWriter = null;
  PrintWriter output = null;
  Scanner classScanner = new Scanner(System.in);
  String input = "";
  boolean done = false;
  while (!done){
  String localname = name;
  boolean stop = false;
  ArrayList<Student> localroster = new ArrayList<Student>();
  ArrayList<String> localassignmentsandweights = new ArrayList<String>();
  if (localname.equals("")){
  System.out.print("Class name: ");
  while (!stop){
    input = classScanner.nextLine();
    if (-1 == findClass(input)){localname = input;stop = true;}
    else{System.out.print("Class already exists. Please use a different name: ");}
  }
  }
  System.out.println("List students' names: (\"stop\" to stop)");
  stop = false;
  while (!stop){
    input = classScanner.nextLine();
    if (input.equalsIgnoreCase("stop")){stop = true;}
    else if (findStudent(input) != -1){localroster.add(globalroster.get(findStudent(input)));globalroster.get(findStudent(input)).addClass(classes.get(findClass(localname)));}
    else if (findStudentInClass(localroster,input) == -1){Student newStudent = new Student(input);localroster.add(newStudent);globalroster.add(newStudent);}
    else {System.out.print("Student already exists in class. Please find a different student: ");}
  }
  System.out.println("List assignment types (type, weight):");
  int counter = 0;
  stop = false;
  while (!stop){
    System.out.println("The rest of the class's assignments should add up to " + (100 - counter) + "% of the grade.");
    input = classScanner.nextLine();
    int weight = 0;
    try {
        weight = Integer.parseInt((input.substring(input.indexOf(",") + 1,input.length())).replaceAll(" ", ""));
    } catch(NumberFormatException e) {
        System.out.print("Error with your input. Please try again: ");
    }
    if (input.indexOf(",") == -1){System.out.print("Your input should be formatted as (type, weight). Please try again: ");}
    else if (!(weight > 0)){System.out.print("The weight should be a positive integer: ");}
    else if (weight + counter > 100){System.out.print("Weight exceeds limit. Please try again: ");}
    else {localassignmentsandweights.add(input);counter += weight;}
    if (counter == 100){stop = true;}
  }
  classes.add(new Class(localname,localroster,localassignmentsandweights));
  System.out.print("Class " + localname + " is made. Would you like to make another class? ");
  if(!isYes(classScanner.nextLine())){done = true;}
  }
}
public static void studentCreateGUI(String externalinput){
  Scanner studentScanner = new Scanner(System.in);
  boolean done = false;
    while (!done){
    String name = externalinput;
    String input = "";
    boolean stop = false;
    ArrayList<Class> classList = new ArrayList<Class>();
    if (externalinput.equals("")){
    System.out.print("Student name: ");
    while (!stop){
      input = studentScanner.nextLine();
      if (findStudent(input) == -1){name = input;stop = true;}
      else {System.out.println("Student already exists. Please use a different name: ");}
    }
  }
    System.out.println("List the id('s) of the class(es) the student is in (\"stop\" to stop):");
    stop = false;
    while (!stop){
      input = studentScanner.nextLine();
      if (input.equalsIgnoreCase("stop")){stop = true;}
      else if (findClass(input) == -1){System.out.println("Could not find class. Please try again: ");}
      else {classList.add(classes.get(findClass(input)));}
    }
    globalroster.add(new Student(name, classList));
    System.out.print("Student " + name + " is made. Would you like to make another student? ");
    if(!isYes(studentScanner.nextLine())){done = true;}
  }
}
public static void assignmentGUI(){
    Scanner assignmentScanner = new Scanner(System.in);
    boolean done = false;
      while (!done){
      String name = "";
      String type = "";
      String input = "";
      String inclass = "";
      int weight = 0;
      Class localclass = new Class();
      System.out.println("This assignment is for which class:");
      boolean stop = false;
      while (!stop){
        inclass = assignmentScanner.nextLine();
        if (findClass(inclass) == -1){System.out.println("Could not find class. Please try again: ");}
        else {localclass = classes.get(findClass(inclass));stop = true;}
      }
      System.out.print("Assignment name: ");
      stop = false;
      while (!stop){
        input = assignmentScanner.nextLine();
        if (-1 == findAssignment(localclass,input)){name = input;stop = true;}
        else{System.out.println("Assignment already exists. Please use a different name: ");}
      }
      System.out.print("Assignment type: ");
      stop = false;
      while (!stop){
        input = assignmentScanner.nextLine();
        if (-1 == findAssignmentType(localclass,input)){System.out.print("Assignment type doesn't exist. Please try again: ");}
        else{type = input;stop = true;}
      }
      (classes.get(findClass(inclass))).addAssignment(new Assignment(name,type,(classes.get(findClass(inclass)))));
      System.out.print("Assignment " + name + " is made. Would you like to make another assignment? ");
      if(!isYes(assignmentScanner.nextLine())){done = true;}
  }
}
public static void classEditGUI(){
  Scanner classScanner = new Scanner(System.in);
  Class localclass = new Class();
  System.out.print("Which class would you like to edit? ");
  boolean stop = false;
  boolean creatingClass = false;
  String input = "";
  while (!stop){
    input = classScanner.nextLine();
    if (-1 == findClass(input)){
      System.out.print("Cannot find class. Would you like to create a new class of this name?: ");
      creatingClass = isYes(classScanner.nextLine());
      if (creatingClass){stop = true;}else{System.out.print("Which class would you like to edit? ");}
    }
    else{localclass = classes.get(findClass(input));stop = true;}
  }
  if (creatingClass){classCreateGUI(input);} else {
    System.out.print("Would you like to change the name of the class? ");
    if (isYes(classScanner.nextLine())){
      System.out.print("Current name: " + localclass.getName() +". New name: ");
      input = classScanner.nextLine();
      localclass.setName(input);
    }
    System.out.print("Would you like to remove any students? ");
    if (isYes(classScanner.nextLine())){
      for (Student s : localclass.getRoster()){
        System.out.print("Remove " + s.getSurname() + ", " + s.getFirstName() + "? ");
        if (isYes(classScanner.nextLine())){
          localclass.removeStudent(findStudentInClass(localclass.getRoster(),s.getName()));
        }
      }
    }



  }

}
public static void studentEditGUI(){
  Scanner studentScanner = new Scanner(System.in);
  Student localstudent = new Student();
  Class localclass = new Class();
  Assignment localassignment = new Assignment();
  System.out.print("Which student would you like to edit? ");
  boolean stop = false;
  boolean creatingStudent = false;
  String input = "";
  while (!stop){
    input = studentScanner.nextLine();
    if (-1 == findStudent(input)){
      System.out.print("Cannot find student. Would you like to create a new student of this name?: ");
      creatingStudent = isYes(studentScanner.nextLine());
      if (creatingStudent){stop = true;}else{System.out.print("Which student would you like to edit? ");}
    }
    else{localstudent = globalroster.get(findStudent(input));stop = true;}
  }
  if (creatingStudent){studentCreateGUI(input);} else {
    System.out.print("Would you like to change the name of the student? ");
    if (isYes(studentScanner.nextLine())){
      System.out.print("Current name: " + localstudent.getName() +". New name: ");
      input = studentScanner.nextLine();
      localstudent.setName(input);
    }
    System.out.print("Would you like to edit the student's grade? ");
    if (isYes(studentScanner.nextLine())){
      System.out.print("For which class is the student's grade changing? ");
      stop = false;
      while (!stop){
        input = studentScanner.nextLine();
        if (-1 == findClass(input)){
          System.out.print("Cannot find class. Please try again ");
        }
        else{localclass = classes.get(findClass(input));stop = true;}
      }
      System.out.print("In which assignment is the student's grade changing? ");
      stop = false;
      while (!stop){
        input = studentScanner.nextLine();
        if (-1 == findAssignment(localclass,input)){
          System.out.print("Cannot find assignment. Please try again ");
        }
        else{localassignment = localclass.getAssignments().get(findAssignment(localclass,input));stop = true;}
      }
      System.out.print(localstudent.getName() + "'s current grade in the " + localassignment.getName() + " assignment of " + localclass.getName() + " class is " + localstudent.getGrade(localclass, localassignment, localstudent) + ". New grade for this assignment: ");
      boolean valid = false;
      while (!valid){
          System.out.print("Please enter a valid positive integer for the grade: ");
          input = studentScanner.nextLine();
          if (isInt(input)){localstudent.setGrade(localclass,localassignment,localstudent,Integer.parseInt(input));valid = true;}
        }
    }
  }
}
public static boolean isInt(String str) { //Using a try catch to check if input is and int (and is positive)
  try {
      int d = Integer.parseInt(str);
      if (d > 0){
      return true;
      }
      return false;
  } catch(NumberFormatException e) {
      return false;
  }
}
public static int findClass(String name){
  if (classes.size() == 0){return -1;}
  for (int i = 0; i < classes.size(); i++){if (name.equalsIgnoreCase((classes.get(i)).getName())){return i;}}
  return -1;
}
public static int findAssignment(Class c,String name){
  if (c.getAssignments().size() == 0){return -1;}
  for (int i = 0; i < c.getAssignments().size(); i++){if (name.equals(c.getAssignments().get(i).getName())){return i;}}
  return -1;
}
public static int findStudent(String name){
  if (globalroster.size() == 0){return -1;}
  for (int i = 0; i < globalroster.size(); i++){if (name.equals(globalroster.get(i).getName())){return i;}}
  return -1;
}
public static int findStudentInClass(ArrayList<Student> roster,String name){
  for (int i = 0; i < roster.size(); i++){if (name.equals(roster.get(i).getName())){return i;}}
  return -1;
}
public static int findAssignmentType(Class c,String type){
  if (c.getAssignmentTypes().size() == 0){return -1;}
  for (int i = 0; i < c.getAssignmentTypes().size(); i++){if (type.equalsIgnoreCase(c.getAssignmentTypes().get(i))){return i;}}
  return -1;
}
public static void startup(){
  FileReader classReader = null;
  Scanner classInput = null;
  FileReader studentReader = null;
  Scanner studentInput = null;
  FileReader assignmentReader = null;
  Scanner assignmentInput = null;
  FileReader gradeReader = null;
  Scanner gradeInput = null;
  boolean fileFound = false;
  //ClassList
  try {
     classReader = new FileReader("ClassList.gdbk");
     classInput = new Scanner(classReader);
     studentReader = new FileReader("GlobalRoster.gdbk");
     studentInput = new Scanner(studentReader);
     assignmentReader = new FileReader("AssignmentsList.gdbk");
     assignmentInput = new Scanner(assignmentReader);
     gradeReader = new FileReader("Gradebook.gdbk");
     gradeInput = new Scanner(gradeReader);
     fileFound = true;
  } catch (IOException e) {
  }
  if (fileFound){
    while (classInput.hasNextLine()){
    String line = classInput.nextLine();
    String[] words = line.split(";");
    String[] studentNames = words[1].split(",");
    String[] weightkey = words[2].split("-");
    ArrayList<Student> localroster = new ArrayList<Student>();
    for (String s : studentNames){localroster.add(new Student(s));}
    ArrayList<String> localassignmentsandweights = new ArrayList<String>();
    for (String w : weightkey){localassignmentsandweights.add(w);}
    classes.add(new Class(words[0], localroster, localassignmentsandweights));
  }

  //Student list / getRoster
  while (studentInput.hasNextLine()){
  String line = studentInput.nextLine();
  String[] words = line.split(";");
  String[] classnamesList = words[1].split(",");
  ArrayList<Class> localclasslist = new ArrayList<Class>();
  for (String a : classnamesList){localclasslist.add(classes.get(findClass(a)));}
  globalroster.add(new Student(words[0], localclasslist, Double.parseDouble(words[2])));
}
while (assignmentInput.hasNextLine()){
  String line = assignmentInput.nextLine();
  String[] words = line.split(";");
  classes.get(findClass(words[2])).addAssignment(new Assignment(words[0],words[1],classes.get(findClass(words[2]))));
}
while (gradeInput.hasNextLine()){
  String line = gradeInput.nextLine();
  String[] words = line.split(";");
  gradelist.add(new Grade(
  classes.get(findClass(words[0])),
  classes.get(findClass(words[0])).getAssignments().get(findAssignment(classes.get(findClass(words[0])),words[1])),
  globalroster.get(findStudent(words[2])),
  Integer.parseInt(words[3])
  ));
}
try {
    classReader.close();
    studentReader.close();
    assignmentReader.close();
    gradeReader.close();
} catch (IOException e){
    System.out.println("Error closing file: " + e);
    System.exit(1);
}
  }

}
public static void save(){
  FileWriter classWriter = null;
  FileWriter studentWriter = null;
  FileWriter assignmentWriter = null;
  FileWriter gradeWriter = null;
  PrintWriter classPrinter = null;
  PrintWriter studentPrinter = null;
  PrintWriter assignmentPrinter = null;
  PrintWriter gradePrinter = null;
  try {
      classWriter = new FileWriter("ClassList.gdbk", false);
      classPrinter = new PrintWriter(classWriter);
  } catch (IOException e){
      System.out.println("Error opening Class List file: " + e);
      System.out.println("Could not save.");
  }
  for (Class c : classes){classPrinter.println(c);System.out.println(c);}
  System.out.println();
  System.out.println();
  try {
      studentWriter = new FileWriter("GlobalRoster.gdbk", false);
      studentPrinter = new PrintWriter(studentWriter);
  } catch (IOException e){
      System.out.println("Error opening Global Roster file: " + e);
      System.out.println("Could not save.");
  }
  for (Student s : globalroster){studentPrinter.println(s);System.out.println(s);}
  try {
      assignmentWriter = new FileWriter("AssignmentsList.gdbk", false);
      assignmentPrinter = new PrintWriter(assignmentWriter);
  } catch (IOException e){
      System.out.println("Error opening Assignments List file: " + e);
      System.out.println("Could not save.");
  }
  System.out.println();
  System.out.println();
  for (Class c : classes){
    for (int i = 0; i < c.getAssignments().size(); i++){
      assignmentPrinter.println(c.getAssignments().get(i));
      System.out.println(c.getAssignments().get(i));
    }
  }
  try {
      gradeWriter = new FileWriter("Gradebook.gdbk", false);
      gradePrinter = new PrintWriter(gradeWriter);
  } catch (IOException e){
      System.out.println("Error opening gradebook: " + e);
      System.out.println("Could not save.");
  }
  System.out.println();
  System.out.println();
  for (Grade g : gradelist){
    assignmentPrinter.println(g);
    System.out.println(g);
  }

  try {
    classWriter.close();
    studentWriter.close();
    assignmentWriter.close();
    gradeWriter.close();
  } catch (IOException e){
      System.out.println("Error closing file: " + e);
      System.exit(1);
  }
}
}
