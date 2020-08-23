package com.SpringDownload.Project.downloaddemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//Application designed using Rest API and Spring boot to download a file, delete a file and copy a file
@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	String Filename = "C:\\Users\\Anirban Mitra\\Music\\DataFile\\DataDownload.csv";
	@RequestMapping(value="/download",method=RequestMethod.GET)// Rest Service to download this file
	public ResponseEntity<InputStreamResource> downloadFile() throws IOException
	{
		FileWriter fileWriter = null;
		try
		{
		Data d1 = new Data();
		d1.setId("1");
		d1.setName("Anirban Mitra");
		d1.setNumber("67496");
		
		Data d2 = new Data();
		d1.setId("2");
		d1.setName("Antarip Pal");
		d1.setNumber("67498");
		
		List<Data> DataList = new ArrayList<>();
		DataList.add(d1);
		DataList.add(d2);
		
		StringBuilder fileContent = new StringBuilder("ID, NAME, NUMBER\n");
		for(Data data:DataList)
		{
			fileContent.append(data.getId()).append(",").append(data.getName()).append(",").append(data.getNumber()).append("\n");
		}
		
		
	
		fileWriter.write(fileContent.toString());
		fileWriter.flush();
		File file = new File(Filename);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment:filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires","0");
		ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application.txt")).body(resource);
		return responseEntity;
	
		}
		catch(Exception e)
		{
			return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally
		{
			if(fileWriter!=null)
			{
				fileWriter.close();
			}
			
		}
		
		
		
	}
	@RequestMapping(value="/download/delete",produces="text/html",method=RequestMethod.DELETE)
	public String DeleteFile(@PathVariable("deletedFileName") String filepath)// Rest API to delete this file
	{
		String removeFileCheck="false";
		try
		{
			System.out.println("Delete a file through Rest API");
			File file = new File(Filename);
			if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
                removeFileCheck="true";
            }else{
                System.out.println("Delete operation has failed.");
            }
		}
	     catch(Exception e)
		{
	    	 e.printStackTrace();
		}
		return removeFileCheck;
		
		
		
	}
	@RequestMapping(value="/copy",method=RequestMethod.GET, consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
	public ResponseEntity<Object> CopyFile(@RequestParam("file")MultipartFile file)throws IOException // Rest API to create a copy of file
	{

	   File convertFile = new File("C:\\Users\\Anirban Mitra\\Music\\CopiedFile"+Filename);
	   convertFile.createNewFile();
	   FileOutputStream fout = new FileOutputStream(convertFile);
	   fout.write(file.getBytes());
	   fout.close();
	    
		return new ResponseEntity<Object>("File copied successfully",HttpStatus.OK);
		
	}

}
