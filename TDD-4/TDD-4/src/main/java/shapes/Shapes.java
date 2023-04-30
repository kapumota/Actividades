package shapes;

import java.util.ArrayList;
import java.util.List;

public class Shapes {
     private final List<Shape> allShapes = new ArrayList<>();

     public void add(Shape s) {
         allShapes.add(s);
     }

     public void draw(Graphics g) {
         for (Shape s : allShapes) {
             switch (s.getType()) {
                 case "textbox":
                     var t = (TextBox) s;
                     g.drawText(t.getText());
                     break;
                 case "rectangle":
                     var r = (Rectangle) s;
                     for (int row = 0;
                          row < r.getHeight();
                          row++) {
                         g.drawLine(0, r.getWidth());
                     }
             }
         }
     }
 }
