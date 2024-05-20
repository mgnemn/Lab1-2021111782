package cn.hit.sw.gui;

import cn.hit.sw.lab1.Generator;
import cn.hit.sw.lab1.impl.GeneratorImpl;
import cn.hit.sw.lab1.impl.util;
import org.jdesktop.swingx.prompt.PromptSupport;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SimpleGUI extends JFrame {
    private Generator generator = null;

    public SimpleGUI() {
        setTitle("有向图相关功能实现");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(3, 2, 5, 5));
        initComponents();
        setSize(600, 400); // 设置窗口大小
        setVisible(true);
    }

    private void initComponents() {
        String[] buttonLabels = {"1.生成有向图", "2.展示有向图", "3.查询桥接词", "4.根据bridge word生成新文本", "5.计算最短路径", "6.随机游走"};
        JButton[] buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setBackground(Color.white);
            add(buttons[i]);
        }
        JFrame frame = new JFrame("错误");
        buttons[0].addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String selectedPath = fileChooser.getSelectedFile().getPath();
                if (selectedPath != null) {
                    File selectedFile = new File(selectedPath);
                    if (selectedFile.exists()) {
                        try {
                            generator = new GeneratorImpl(util.getGraphFromFile(new File(selectedPath)));
                            JOptionPane.showMessageDialog(frame, "有向图生成成功","成功",JOptionPane.INFORMATION_MESSAGE);
                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(frame, "文件不存在!","错误",JOptionPane.ERROR_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(frame, "选择的文件不存在!","错误",JOptionPane.ERROR_MESSAGE);}
                }else {
                    JOptionPane.showMessageDialog(frame, "未选择文件或文件失效","错误",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttons[1].addActionListener(e -> {
            if (generator == null) {
                JOptionPane.showMessageDialog(frame, "未生成有向图!","错误",JOptionPane.ERROR_MESSAGE);
            } else {
                generator.showDirectedGraph();
            }
        });

        buttons[2].addActionListener(e -> {
            if (generator == null) {
                JOptionPane.showMessageDialog(frame, "未生成有向图!","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 使用流式布局，居中对齐，间距为10
            JTextField textField1 = new JTextField(15);
            PromptSupport.setPrompt("请输入第一个单词", textField1);
            PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, textField1);
            PromptSupport.setForeground(Color.GRAY, textField1);
            JTextField textField2 = new JTextField(15);
            PromptSupport.setPrompt("请输入第二个单词", textField2);
            PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, textField2);
            PromptSupport.setForeground(Color.GRAY, textField2);
            panel.add(textField1);
            panel.add(textField2);
            int result = JOptionPane.showConfirmDialog(null, panel, "查询桥接词", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String input1 = textField1.getText();
                String input2 = textField2.getText();
                JOptionPane.showMessageDialog(null, generator.queryBridgeWords(input1, input2));
            }
        });

        buttons[3].addActionListener(e -> {
            if (generator == null) {
                JOptionPane.showMessageDialog(frame, "未生成有向图!","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            JPanel panel = new JPanel();
            JTextField textField1 = new JTextField(15);
            PromptSupport.setPrompt("请输入文本:", textField1);
            PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, textField1);
            PromptSupport.setForeground(Color.GRAY, textField1);
            panel.add(textField1);
            int result = JOptionPane.showConfirmDialog(frame, panel, "根据bridge word生成新文本",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(result == JOptionPane.OK_OPTION) {
                String input = textField1.getText();
                if (input != null) {
                    JOptionPane.showMessageDialog(null, generator.generateNewText(input));
                }
            }
        });

        buttons[4].addActionListener(e -> {
            if (generator == null) {
                JOptionPane.showMessageDialog(frame, "未生成有向图!","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 使用网格布局，2行2列，间距为10
            JTextField textField1 = new JTextField(15);
            PromptSupport.setPrompt("请输入起始单词", textField1);
            PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, textField1);
            PromptSupport.setForeground(Color.GRAY, textField1);
            JTextField textField2 = new JTextField(15);
            PromptSupport.setPrompt("请输入终止单词", textField2);
            PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, textField2);
            PromptSupport.setForeground(Color.GRAY, textField2);
            panel.add(textField1);
            panel.add(textField2);
            int result = JOptionPane.showConfirmDialog(null, panel, "计算最短路径", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String input1 = textField1.getText();
                String input2 = textField2.getText();
                JOptionPane.showMessageDialog(null, generator.calcShortestPath(input1, input2));
            }
            generator.showDirectedGraph();
        });

        buttons[5].addActionListener(e -> {
            if (generator == null) {
                JOptionPane.showMessageDialog(frame, "未生成有向图!","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            generator.randomWalk();
        });
    }
}


