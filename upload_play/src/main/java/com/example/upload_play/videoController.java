package com.example.upload_play;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;


import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class videoController {

    @Autowired
    RabbitAdmin rabbitAdmin;

    @Bean
    public RabbitAdmin rabbitAdmin(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @RequestMapping("/")
    public String goupload() {
        return "upload";
    }

    @GetMapping(value = "/gouploadagain")
    public String gouploadagain() {
        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request)
            throws InterruptedException, UnsupportedEncodingException 
    {   
        if(file.isEmpty())
        {
            return "upload";
        }
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        String rootPath = System.getProperty("user.dir");
        File input = new File(rootPath);
        String RootParentPath = input.getParent();
        String filePath = RootParentPath + "\\Final-input\\";
        String FullName = filePath + fileName;
        String outputPath = RootParentPath + "\\Final-output\\";
        
        request.getSession().setAttribute("fileOutPath", outputPath);
        //String filePath = request.getSession().getServletContext().getRealPath("");
        //String filePath = "D:\\GitHub\\SA-Homework\\SA-HW5\\HW5-input\\";
        System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);
        try 
        {
            File targetFile = new File(filePath);  
            if(!targetFile.exists())
            {    
                targetFile.mkdirs();   
            }       
            FileOutputStream out = new FileOutputStream(filePath + fileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } 
        catch (Exception e) {
            // TODO: handle exception
            }
        //System.out.println(fileName.substring(0,fileName.lastIndexOf(".")));
        
        //Convert convertVideo = new Convert();
        //convertVideo.process(fileName);
        //TODO:换成消息队列。
        //System.out.println(fileName);
        rabbitAdmin.getRabbitTemplate().convertAndSend("myQueue", new String (fileName));

        return "uploadSuccess";
    }

    @RequestMapping("/list")
    public String list(HttpServletRequest request,Model model)
    {
        File dir = new File((String) request.getSession().getAttribute("fileOutPath"));
        File[] subFiles = dir.listFiles();
        ArrayList<File> videos = new ArrayList<>();
        ArrayList<String> videosName = new ArrayList<>();
        for (File subFile : subFiles) 
        {
            videos.add(subFile);
            videosName.add(subFile.getName());
            //System.out.println(subFile.getAbsolutePath());
        }
        model.addAttribute("videos", videos);
        model.addAttribute("videosName", videosName);
        return "playlist";
    }

}