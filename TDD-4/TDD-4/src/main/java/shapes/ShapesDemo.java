package shapes;

public class ShapesDemo {
    public static void main(String[] args) {
        new ShapesDemo().run();
    }
  private void run() {
        Graphics console = new ConsoleGraphics();
        var shapes = new Shapes(console);

        shapes.add(new TextBox("..."));
        shapes.add(new Rectangle(32,1));
        shapes.add(new TextBox("..."));
        shapes.add(new TextBox("..."));
        shapes.add(new TextBox("..."));
        shapes.add(new TextBox("...:"));
        shapes.add(new Rectangle(5,3));
    
        shapes.draw();
    }
}


