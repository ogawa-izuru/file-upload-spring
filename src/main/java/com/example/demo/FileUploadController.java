package com.example.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
@Controller
public class FileUploadController {

  private String getExtension(String filename) {
    int dot = filename.lastIndexOf(".");
    if (dot > 0) {
      return filename.substring(dot).toLowerCase();
    }
    return "";
  }

  private String enhanseExtension(String filename) {
    int dot = filename.lastIndexOf(".");
    if (dot > 0) {
      String aaa = filename.substring(dot).toLowerCase();
      String fullFileName = filename.replace(aaa, "");
      return fullFileName;
    }
    return "";
  }



  private String getUploadFileName(String fileName) {

      return enhanseExtension(fileName) + "_" +
              DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
                  .format(LocalDateTime.now())
              + getExtension(fileName);
  }

  private void createDirectory() {
      Path path = Paths.get("C:/upload/files");
      if (!Files.exists(path)) {
        try {
          Files.createDirectory(path);
        } catch (Exception e) {
          //エラー処理は省略
        }
      }
  }

  private void savefile(MultipartFile file) {
    String filename = getUploadFileName(file.getOriginalFilename());
    enhanseExtension(filename);
    Path uploadfile = Paths.get("C:/upload/files/" + filename);
    try (OutputStream os = Files.newOutputStream(uploadfile, StandardOpenOption.CREATE)) {
      byte[] bytes = file.getBytes();
      os.write(bytes);
    } catch (IOException e) {
      //エラー処理は省略
    }
  }

  private void savefiles(List<MultipartFile> multipartFiles) {
      createDirectory();
      for (MultipartFile file : multipartFiles) {
          savefile(file);
      }
  }

//  @RequestMapping(path = "/", method = RequestMethod.GET)
//  String uploadview(Model model) {
//    model.addAttribute("form", new UploadForm());
//    return "index";
//  }

  @RequestMapping(path = "/upload", method = RequestMethod.POST)
  @ResponseBody
  String upload(UploadForm form) {


    if (form.getFile()==null || form.getFile().isEmpty()) {
      //エラー処理は省略
      return "a";
    }
    savefiles(form.getFile());

    return "";
  }





}
