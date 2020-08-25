package com.masflam.radxg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.masflam.radxg.xmltags.Endpoint;
import com.masflam.radxg.xmltags.Spec;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		if(args.length < 2) {
			System.err.println("Usage:\tradxg <xml-file> <css-file>");
		} else {
			String xmlFilePath = args[0];
			String cssFilePath = args[1];
			
			String fileBasePath = xmlFilePath;
			fileBasePath = fileBasePath.replaceAll("\\.[Xx][Mm][Ll]$", "");
			
			String fileBasename = fileBasePath.replaceAll("\\\\", "/");
			int lastIndexOfSlash = fileBasename.lastIndexOf('/');
			fileBasename = fileBasename.substring(lastIndexOfSlash != -1 ? lastIndexOfSlash + 1 : 0);
			
			
			
			ObjectMapper xmlMapper = new XmlMapper();
			
			try {
				Spec spec = xmlMapper.readValue(new File(xmlFilePath), Spec.class);
				for(Endpoint endpoint : spec.endpoints) {
					if(endpoint.reqParams == null) {
						endpoint.reqParams = new ArrayList<>();
					}
				}
				
				String css = Files.readString(Path.of(cssFilePath));
				
				System.out.println(xmlMapper.writeValueAsString(spec));
				
				try {
					String html = HtmlGen.generateHtml(spec, fileBasename, css);
					var htmlFile = new File(fileBasename + ".html");
					htmlFile.createNewFile();
					try(var fileOutputStream = new FileOutputStream(htmlFile)) {
						fileOutputStream.write(html.getBytes());
					}
				} catch(IOException e) {
					System.err.println("Failed to write file:\t" + fileBasename + ".html\nException stack trace:\n");
					e.printStackTrace();
				}
			} catch(IOException e) {
				System.err.println("Failed to read file:\t" + xmlFilePath + "\nException stack trace:\n");
				e.printStackTrace();
			}
		}
	}
}
