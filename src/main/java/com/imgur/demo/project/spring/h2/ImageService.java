package com.imgur.demo.project.spring.h2;

import com.imgur.demo.project.spring.h2.model.Employee;
import com.imgur.demo.project.spring.h2.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

  private static final String IMGUR_CLIENT_ID = "7952f282cc3e64e";

  private static final String IMGUR_GALLERY_URI = "https://api.imgur.com/3/gallery/hot/viral/all/1?showViral=true&mature=false&album_previews=false";

  //private static final String IMGUR_GALLERY_URI = "https://imgur.com/9uBQRdO";

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  @Cacheable("image")
  public Image getImage(String id) {
    LOGGER.info("getImage called for id {}", id);

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
    ResponseEntity<Gallery> response = restTemplate.exchange(IMGUR_GALLERY_URI, HttpMethod.GET, entity, Gallery.class);

    List<Data> datas = response.getBody().getData().stream().filter(data -> !(data.getImages() == null))
            .collect(Collectors.toList());
    Optional<Data> responseImage = datas.stream()
            .filter(inner -> inner.getImages().stream().anyMatch(image -> id.equals(image.id))).findFirst();
    if (responseImage.isPresent()) {
      System.out.println("present");
      return responseImage.get().getImages().get(0);
    } else {
      return null;
    }
  }

  @Cacheable("image")
  public List<Image> getAllImages() {


    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
    ResponseEntity<Gallery> response = restTemplate.exchange(IMGUR_GALLERY_URI, HttpMethod.GET, entity, Gallery.class);

    List<Data> datas = response.getBody().getData().stream().filter(data -> !(data.getImages() == null))
            .collect(Collectors.toList());

    List allimages = new ArrayList();

    for (Data data : datas) {
      allimages.add(data.getImages());
    }

    return allimages;
  }


//  @Cacheable("image")
//  public void uploadImage(File file) {
//
//
//    RestTemplate restTemplate = new RestTemplate();
//    HttpHeaders headers = new HttpHeaders();
//    headers.add("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
//    headers.setContentType(MediaType.IMAGE_JPEG);
//   // HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
//   // ResponseEntity response = restTemplate.exchange("https://api.imgur.com/3/image", HttpMethod.POST, entity, String.class);
//    FileInputStream fl = null;
//    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//    try {
//      fl = new FileInputStream(file);
//    }catch (Exception e){
//
//    }
//
//    // Now creating byte array of same length as file
//    byte[] arr = new byte[(int)file.length()];
//
//
//    ByteArrayResource fileAsResource = new ByteArrayResource(arr){
//      @Override
//      public String getFilename(){
//        return file.getName();
//      }
//    };
//    body.add("file",fileAsResource);
//
//    HttpEntity<MultiValueMap<String, Object>> requestEntity
//            = new HttpEntity<>(body, headers);
//
//    ResponseEntity<String> response = restTemplate
//            .postForEntity("https://api.imgur.com/3/image", requestEntity, String.class);
//
//  }

  public void postFile(String id, MultipartFile file) {


    String filename = file.getOriginalFilename();

    // Now creating byte array of same length as file
    byte[] someByteArray = null;

    try {
      someByteArray = file.getBytes();
    } catch (Exception e) {
      e.printStackTrace();

    }
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
    headers.add("Authorization", "Bearer " + "076ae717ab4beef094cb8246162c7613452c76da");
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    // This nested HttpEntiy is important to create the correct
    // Content-Disposition entry with metadata "name" and "filename"
    MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
    ContentDisposition contentDisposition = ContentDisposition
            .builder("form-data")
            .name("file")
            .filename(filename)
            .build();
    fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
    HttpEntity<byte[]> fileEntity = new HttpEntity<>(someByteArray, fileMap);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", fileEntity);


    HttpEntity<MultiValueMap<String, Object>> requestEntity =
            new HttpEntity<>(body, headers);

    Optional<Employee> employee = employeeRepository.findById(Long.valueOf(id));
    if(employee.isPresent()) {
      Employee emp = employee.get();
      emp.setImageurl(filename);
      employeeRepository.save(emp);
    }



    try {
      ResponseEntity response = restTemplate.exchange(
              "https://api.imgur.com/3/image",
              HttpMethod.POST,
              requestEntity,
              String.class);

      System.out.println(response);

      //update employeetable with image path


    } catch (HttpClientErrorException e) {
      e.printStackTrace();
    }
  }
}

