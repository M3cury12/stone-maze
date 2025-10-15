package com.xdulee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {

    // 图片路径
    private  static final String imagePath = "stone-maze/src/image/";

    //定义一个4*4的图片数组
    private final int[][] imageData = {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };

    //定义一个华容道完成的数组
    private static final int[][] completeData = {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };

    //定义两个整数变量记录当前空白色块位置
    private int whiteX ;//行
    private int whiteY ;//列

    //统计步数
    private int count;

    public MainFrame () {

        initFrame();//初始化窗口

        initRandomArray();//打乱图片数组顺序

        initImage();//初始化图片界面

        initMenu();//初始化菜单

        initKeyEvent();//初始化按键监听事件

        this.setVisible(true);
    }

    private void initKeyEvent() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int KeyCode = e.getKeyCode();
                if(KeyCode == KeyEvent.VK_UP){
                    SwitchAndMove(Direction.UP);
                }
                else if(KeyCode == KeyEvent.VK_DOWN){
                    SwitchAndMove(Direction.DOWN);
                }
                else if(KeyCode == KeyEvent.VK_LEFT){
                    SwitchAndMove(Direction.LEFT);
                }
                else if(KeyCode == KeyEvent.VK_RIGHT){
                    SwitchAndMove(Direction.RIGHT);
                }
            }
        });
    }

    private void SwitchAndMove(Direction direction) {
        switch (direction){
            case UP:
                //上交换的条件是行数小于整个数组长度-1
                if(whiteX < imageData.length - 1){
                    //当前空白块位置为 whiteX ,whiteY
                    //要移动的色块位置为 whiteX + 1 ,whiteY
                    //交换两个色块位置
                    int temp = imageData[whiteX][whiteY];
                    imageData[whiteX][whiteY] = imageData[whiteX + 1][whiteY];
                    imageData[whiteX + 1][whiteY] = temp;
                    whiteX++;//空白色块位置更新
                    count++;
                }

                System.out.println("上");
                break;

            case DOWN:
                //下交换的条件是行数大于0
                if(whiteX > 0){
                    //当前空白块位置为 whiteX ,whiteY
                    //要移动的色块位置为 whiteX  - 1,whiteY
                    //交换两个色块位置
                    int temp = imageData[whiteX][whiteY];
                    imageData[whiteX][whiteY] = imageData[whiteX - 1][whiteY];
                    imageData[whiteX - 1][whiteY] = temp;
                    whiteX--;//空白色块位置更新
                    count++;
                }
                System.out.println("下");
                break;

            case LEFT:
                //左交换的条件是小于整个数组长度-1
                if(whiteY < imageData.length - 1){
                    //当前空白块位置为 whiteX ,whiteY
                    //要移动的色块位置为 whiteX - 1 ,whiteY
                    //交换两个色块位置
                    int temp = imageData[whiteX][whiteY];
                    imageData[whiteX][whiteY] = imageData[whiteX][whiteY + 1];
                    imageData[whiteX][whiteY + 1] = temp;
                    whiteY++;//空白色块位置更新
                    count++;
                }
                System.out.println("左");
                break;

            case RIGHT:
                //右交换的条件是列数列数大于0
                if(whiteY > 0){
                    //当前空白块位置为 whiteX ,whiteY
                    //要移动的色块位置为 whiteX + 1 ,whiteY
                    //交换两个色块位置
                    int temp = imageData[whiteX][whiteY];
                    imageData[whiteX][whiteY] = imageData[whiteX][whiteY - 1];
                    imageData[whiteX][whiteY - 1] = temp;
                    whiteY--;
                    count++;
                }
                System.out.println("右");
                break;
        }
        initImage();//重新刷新界面
    }

    private void initRandomArray() {
        for (int i = 0; i < imageData.length; i++) {
            for (int j = 0; j < imageData[i].length; j++) {
                int temp = imageData[i][j];
                int randomX = (int)(Math.random() * imageData.length);
                int randomY = (int)(Math.random() * imageData[i].length);
                imageData[i][j] = imageData[randomX][randomY];
                imageData[randomX][randomY] = temp;
            }
        }

        //定位空白色块位置
        OUT:
        for (int i = 0; i < imageData.length; i++) {
            for (int j = 0; j < imageData[i].length; j++) {
                if(imageData[i][j] == 0){
                    whiteX = i;
                    whiteY = j;
                    break  OUT;//跳出循环至OUT
                }
            }
        }

    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("系统");
        JMenuItem exitItem = new JMenuItem("退出");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JMenuItem restartItem = new JMenuItem("重启");
        menu.add(restartItem);

        restartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count = 0;
                initRandomArray();
                initImage();

            }
        });
        menuBar.add(menu);
        this.setJMenuBar(menuBar);


    }

    //初始化框架
    private void initFrame() {
        this.setTitle("华容道 V1.0");
        this.setSize(465,560);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout( null);//设置布局方式为绝对位置定位
    }

    //把图片添加到窗体中
    private void initImage() {
        //先清空窗口上所有图层
        this.getContentPane().removeAll();

        //显示操作步数
        JLabel stepLabel = new JLabel("步数：" + count);
        stepLabel.setBounds(20,0,80,20);
        stepLabel.setFont(new Font("微软雅黑",Font.BOLD,12));
        this.add(stepLabel);

        //判断是否完成
        if(isWin()){
            JLabel winLabel = new JLabel(new ImageIcon(imagePath + "win.png"));
            winLabel.setBounds(124,230,266,88);
            this.add(winLabel);
        }
        //按数组顺序展示图片
        for (int i = 0; i < imageData.length; i++) {
            for (int j = 0; j < imageData[i].length; j++) {
                String imageName = imageData[i][j] + ".png";
                //创建图片标签
                JLabel label = new JLabel(new ImageIcon(imagePath + imageName));
                //设置图片标签的位置
                label.setBounds(20 + j*100,70 + i*100,100,100);
                //将图片标签添加到窗体中
                this.add(label);
            }
        }


        //设置背景图片
        JLabel background = new JLabel(new ImageIcon(imagePath + "background.png"));
        background.setBounds(0,10,450,484);
        this.add(background);

        //刷新图层
        this.repaint();

    }

    private boolean isWin() {
        //判断imageData数组是否与completeData数组一致
        for (int i = 0; i < imageData.length; i++) {
            for (int j = 0; j < imageData[i].length; j++) {
                if(imageData[i][j] != completeData[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
}
