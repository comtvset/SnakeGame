import javax.swing.*;

public class MainWindow extends JFrame {  //JFrame - это основной свинговый класс, который хочет быть окном
    public MainWindow(){
        setTitle("Змейка"); //название в заголовке
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //когда мы будем нажимать крестик в окне, программа будет прекращать свою работу
        setSize(350,375); //размеры окна
        setLocation(800,400); //положение фрейма(окна) на экране
        add(new GameField()); //создает игру из класса GameField
        setVisible(true); //отображает окно
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}
