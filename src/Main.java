import GUI.GraphicalUserInterface;
import database.HibernateSingleton;

public class Main {

    public static void main(String[] args) {
        GraphicalUserInterface gui = new GraphicalUserInterface();
        gui.run();
        HibernateSingleton.getInstance().close();
    }

}