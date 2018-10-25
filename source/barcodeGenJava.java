import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.*; 
import javax.swing.JOptionPane; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class barcodeGenJava extends PApplet {




Map<Integer, String> digitMap = new HashMap();
Map<Integer, String> widthMap = new HashMap();
int height = 80;
int marginLeft = 30;
int marginTop = 30;
float narrow = 1;
float wide = 3;
float positionOffset = 0;

public void setup() {
  //strokeWeight(12);
  
  background(255,255,255);
  fill(0,0,0);
  stroke(0,0,0);
  noLoop();
  
  digitMap.put(0, "101001101101");
  digitMap.put(1, "110100101011");
  digitMap.put(2, "101100101011");
  digitMap.put(3, "110110010101");
  digitMap.put(4, "101001101011");
  digitMap.put(5, "110100110101");
  digitMap.put(6, "101100110101");
  digitMap.put(7, "101001011011");
  digitMap.put(8, "110100101101");
  digitMap.put(9, "101100101101");
  
  widthMap.put(0, "NNNWWNWNN");
  widthMap.put(1, "WNNWNNNNW");
  widthMap.put(2, "NNWWNNNNW");
  widthMap.put(3, "WNWWNNNNN");
  widthMap.put(4, "NNNWWNNNW");
  widthMap.put(5, "WNNWWNNNN");
  widthMap.put(6, "NNWWWNNNN");
  widthMap.put(7, "NNNWNNWNW");
  widthMap.put(8, "WNNWNNWNN");
  widthMap.put(9, "NNWWNNWNN");
}

// Return the Code39 binary representation of a given digit.
public String digitToCode39Binary(int digit) {
  if (digit > 10 || digit < 0) {
    return "error";
  }
  
  return digitMap.get(digit);
}

// Return the Code39 width encoding pattern of a given digit.
public String digitToCode39Pattern(int digit) {
  if (digit > 10 || digit < 0) {
    return "error";
  }
  
  return widthMap.get(digit);
}

// Return the Code39 binary representation of the start/stop character.
public void drawStartStopCode39Binary(int digitCount) {
  String nibble = "100101101101";
  String pattern = "NWNNWNWNN";
  int patternCount = 0;
  float strokeWeight = 0;
  
  for (int j=0; j < 9; j++) {
      /*if (j > 0 && !nibble.substring(j,j+1).equals(nibble.substring(j-1,j))) {
        if (pattern.substring(patternCount, patternCount + 1).equals("N")) {
          //strokeWeight(narrow);
          strokeWeight = narrow;
        } else {
          //strokeWeight(wide);
          strokeWeight = wide;
        }
        patternCount++;
      }
      if (nibble.substring(j,j+1).equals("1")) {
          fill(0,0,0);
          stroke(0,0,0);
      } else {
          fill(255,255,255);
          stroke(255,255,255);
      }*/
      
      if (pattern.substring(j, j + 1).equals("N")) {
        //strokeWeight(narrow);
        strokeWeight = narrow;
      } else {
        //strokeWeight(wide);
        strokeWeight = wide;
      }
      
      if (j % 2 == 0) {
        fill(0,0,0);
        stroke(0,0,0);
      } else {
        fill(255,255,255);
        stroke(255,255,255);
      }
      
      rect(marginLeft + positionOffset, marginTop, strokeWeight, height);
      positionOffset += strokeWeight;
  }
  
   // Small space after character
  fill(255,255,255);
  stroke(255,255,255);
  rect(marginLeft + positionOffset, marginTop, 1, height);
  positionOffset += 1;
}

public void drawBarAndDigit(int digit, int digitCount) {
  String nibble = digitToCode39Binary(digit);
  String pattern = digitToCode39Pattern(digit);
  int patternCount = 0;
  float strokeWeight = 0;
  
  //Display the digit in Decimal
  fill(0,0,0);
  stroke(0,0,0);
  textSize(18);
  text(digit, marginLeft + positionOffset, marginTop + 20 + height);
  
  for (int j=0; j < 9; j++) {
      /*if (j > 0 && !nibble.substring(j,j+1).equals(nibble.substring(j-1,j))) {
        if (pattern.substring(patternCount, patternCount + 1).equals("N")) {
          //strokeWeight(narrow);
          strokeWeight = narrow;
        } else {
          //strokeWeight(wide);
          strokeWeight = wide;
        }
        patternCount++;
      }
      if (nibble.substring(j,j+1).equals("1")) {
          fill(0,0,0);
          stroke(0,0,0);
      } else {
          fill(255,255,255);
          stroke(255,255,255);
      }*/
      
      if (pattern.substring(j, j + 1).equals("N")) {
        //strokeWeight(narrow);
        strokeWeight = narrow;
      } else {
        //strokeWeight(wide);
        strokeWeight = wide;
      }
      
      if (j % 2 == 0) {
        fill(0,0,0);
        stroke(0,0,0);
      } else {
        fill(255,255,255);
        stroke(255,255,255);
      }
      
      //rect(marginLeft + digitCount*26 + 2*j, marginTop, 2, height);
      rect(marginLeft + positionOffset, marginTop, strokeWeight, height);
      positionOffset += strokeWeight;
  }
  
  // Small space after character
  fill(255,255,255);
  stroke(255,255,255);
  rect(marginLeft + positionOffset, marginTop, 1, height);
  positionOffset += 1;
  
}

public void drawBarCode(int startingNum, int numBarcodes) {
    //Generates a Code 39 barcode.
    
    for (int count = 0; count < numBarcodes; count++) {
        
        String number = String.valueOf(startingNum + count);
        List<String> numArray = new ArrayList<String>();
        numArray.clear();
        numArray.addAll(Arrays.asList(number.split("(?<=.)")));
        
        
        // Start char (*)
        drawStartStopCode39Binary(0);
        
        
        for (int i=0; i < numArray.size(); i++) {
            int digit = Integer.parseInt(numArray.get(i));
            drawBarAndDigit(digit, i + 1);
        }
        
        // Stop char (*)
        drawStartStopCode39Binary(numArray.size() + 1);
            
        saveFrame("barcode-" + number + ".png");
        fill(255,255,255);
        stroke(255,255,255);
        rect(0, 0, 300, 300);
        positionOffset = 0;
        
    }
}

public void draw() {
  setup();
  Scanner user_input = new Scanner(System.in);
  
  int i = 0, startingNum = 1, numBarcodes = 1;
  String r = JOptionPane.showInputDialog(null, "Starting Value: ", "Enter", JOptionPane.QUESTION_MESSAGE);
  try {
    startingNum = Integer.parseInt(r);
  } catch(NumberFormatException e) {
    println("you did not enter a number!");
  }
  
  r = JOptionPane.showInputDialog(null, "Number of Barcodes: ", "Enter", JOptionPane.QUESTION_MESSAGE);
  try {
    numBarcodes = Integer.parseInt(r);
  } catch(NumberFormatException e) {
    println("you did not enter a number!");
  }
  
  user_input.close();
  
  drawBarCode(startingNum, numBarcodes);
  
  
}
  public void settings() {  size(300,150); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "barcodeGenJava" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
