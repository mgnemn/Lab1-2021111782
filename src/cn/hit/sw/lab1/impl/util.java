package cn.hit.sw.lab1.impl;

import cn.hit.sw.entity.MyGraph;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class util {
    public static MyGraph getGraphFromFile(File file) throws FileNotFoundException {
        System.setProperty("org.graphstream.ui", "swing");
        // 用于存储文件内容的字符串
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // 逐行读取文件内容
            while ((line = reader.readLine()) != null) {
                // 将每行内容追加到StringBuilder中
                content.append(line);
                content.append("\n");
            }
        } catch (IOException ignored) {

        }

        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(content.toString());

        while (matcher.find()) {
            if (!result.isEmpty()) {
                result.append(" ");
            }
            result.append(matcher.group().toLowerCase());
        }

        MyGraph graph = new MyGraph("graph1");
        String[] words = result.toString().split(" ");
        Set<String> existNode = new HashSet<>();
        for (String word : words) {
            if (!existNode.contains(word)) {
                graph.addNode(word).setAttribute("ui.label", word);
                existNode.add(word);
            }
        }

        Set<String> existEdge = new HashSet<>();
        for (int i = 0; i < words.length - 1; i++) {
            if (!existEdge.contains(words[i] + "_" + words[i + 1])) {
                graph.addEdge(words[i] + "_" + words[i + 1], words[i], words[i + 1], true).setAttribute("ui.label", 1);
                existEdge.add(words[i] + "_" + words[i + 1]);
            } else {
                int weight = (int) graph.getEdge(words[i] + "_" + words[i + 1]).getAttribute("ui.label");
                graph.getEdge(words[i] + "_" + words[i + 1]).setAttribute("ui.label", weight + 1);
            }
        }
        return graph;
    }
}