package com.example.convert;

import java.io.*;
import java.util.*;

public class Convert{
    String inputPath;
    String outputPath;
    final static String ffmpegPath = "D:\\ffmpeg\\bin\\";
    String fileName;
    boolean done=false;


    public boolean isDone()
    {
        return done;
    }

    public Convert(String fileName){
        //构造方法
        this.fileName=fileName;
    }


    public void process() {
        //inputPath = "D:\\GitHub\\SA-Homework\\SA-Final\\Final-input\\" + fileName;
        String rootPath = System.getProperty("user.dir");
        File input = new File(rootPath);
        String filePath = input.getParent();
        inputPath = filePath + "\\Final-input\\" + fileName;

        //outputPath = "D:\\GitHub\\SA-Homework\\SA-Final\\Final-output\\";
        outputPath = filePath + "\\Final-output\\";


        // String fileName = inputPath.substring(inputPath.lastIndexOf("\\") + 1,
        // inputPath.lastIndexOf("."));
        String filetype = inputPath.substring(inputPath.lastIndexOf(".") + 1, inputPath.length());
        //System.out.println(fileName);
        //System.out.println(filetype);

        List<String> command = new ArrayList<String>();
        command.add(ffmpegPath + "ffmpeg");
        command.add("-i");
        command.add(inputPath);
        command.add("-y");
        command.add("-r");
        command.add("15");
        command.add("-s");
        command.add("480x360");
        command.add(outputPath + fileName.substring(0, fileName.lastIndexOf(".")) + "-360p" + "." + filetype);

        List<String> command1 = new ArrayList<String>();
        command1.add(ffmpegPath + "ffmpeg");
        command1.add("-i");
        command1.add(inputPath);
        command1.add("-y");
        command1.add("-r");
        command1.add("15");
        command1.add("-s");
        command1.add("864x480");
        command1.add(outputPath + fileName.substring(0, fileName.lastIndexOf(".")) + "-480p" + "." + filetype);

        try {
            System.out.println(fileName+"_360P 开始");
            Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
            new PrintStream(videoProcess.getErrorStream()).start();
            new PrintStream(videoProcess.getInputStream()).start();

            try {
                videoProcess.waitFor();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(fileName+"_360P 完成");

            System.out.println(fileName+"_480P 开始");
            Process videoProcess1 = new ProcessBuilder(command1).redirectErrorStream(true).start();
            new PrintStream(videoProcess1.getErrorStream()).start();
            //System.out.println("notfound?");
            new PrintStream(videoProcess1.getInputStream()).start();
            //System.out.println("notfound?");
            try {
                videoProcess1.waitFor();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(fileName+"_480P 完成");
            this.done=true;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    class PrintStream extends Thread {
        java.io.InputStream __is = null;

        public PrintStream(java.io.InputStream is) {
            __is = is;
        }

        public void run() {
            try {
                while (this != null) {
                    int _ch = __is.read();
                    if (_ch != -1)
                        System.out.print((char) _ch);
                    else
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}