package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.UploadForm;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Controller
public class ProductController {

  @Autowired
  ProductRepository productRepository;

  @RequestMapping(path = "/allproduct", method = RequestMethod.GET)
  @ResponseBody
  List<Product> getAllProduct(UploadForm form) {


    List<Product> list = productRepository.findAll();
    return list;
  }
  @RequestMapping(path = "/allproduct", method = RequestMethod.POST)
  @ResponseBody
  Product saveImgPath(@RequestBody String path) {
    Product tmp = new Product();
    tmp.setP_name("ねこ");
    tmp.setImage(path);
    tmp.setPrice(100);
    return productRepository.save(tmp);
  }




}
