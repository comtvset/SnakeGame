import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    //поля этого класса.. игровые параметры
    private final int SIZE = 320; //поле
    private final int DOT_SIZE = 16; //сколько пикселей занимает ячейка змейки и яблока (кол-во пикселей)
    private final int ALL_DOTS = 400; //сколько игровый едениц может поместиться на игровом поле
    private Image dot; //картинка .png
    private Image apple; //картинка .png
    private int appleX; //Х позиция яблока
    private int appleY; //Y позиция яблока
    private int[] x = new int[ALL_DOTS]; //x массив для хранения всех положений змейки, если сделать [ALL_DOTS-396], тогда змейка съест 1 яблоко и игра закончиться
    private int[] y = new int[ALL_DOTS]; //y массив для хранения всех положений змейки, если сделать [ALL_DOTS-396], тогда змейка съест 1 яблоко и игра закончиться
    private int dots; //размер змейки
    private Timer timer; //нужен для скорости движения змейки
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField(){    //конструктор
        setBackground(Color.DARK_GRAY); //задаем цвет игрового поля
        loadImages(); //вызов метода loadImages
        initGame();  //вызов метода initGame
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){   //метод который инициализирует начало игры
        dots = 3; //размер змейки на начальном этапе (инициализация начальных точек)
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE; //не понял! разобрать
            y[i] = 48;
        }
        timer = new Timer(200,this); //200 - это скорость движения змейки. this - это значит что класс GameField будет отвечать за обработку вызова
                                                 //таймера(каждые 200 миллисекунд).. но необходимо заимплементировать интерфейс ActionListener
        timer.start(); //запуск таймера
        createApple(); //вызов метода для создания яблока
    }

    public void createApple(){   //метода для создания яблока
        appleX = new Random().nextInt(20)*DOT_SIZE; //случайная позиция на поле из задоного размера
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImages(){            //метод для загрузки картинок
        ImageIcon iia = new ImageIcon("apple.png"); //создаем объект
        apple = iia.getImage(); //инициализация объекта
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        } else {
            String str = "Game Over";
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.white);
            //g.setFont(f);
            g.drawString(str,125,SIZE/2);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }

    }

    public void checklApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }

            @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checklApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                up = true;
                right = false;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
