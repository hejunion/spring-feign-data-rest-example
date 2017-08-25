package com.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@EnableAutoConfiguration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableFeignClients
public class Application {
    public static void main(String[] args) throws IOException {
    	
    	BufferedWriter  bw = new BufferedWriter ( new FileWriter("IDCExtensionMappingDocumentProd.csv") );
    	
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        
        String jrehome_caerts  ="C:\\working\\cacerts";
        // Devt String token = "Basic SUdDSURQREFUREVWU1ZDOnQ3dkBmR1M2englZA==";
        String token = "Basic amhlMDY6Tm9raWF3aW44JA==";
        //PersonClient client = context.getBean(PersonClient.class);
        //System.out.println(client.getPersons().getContent());
        //System.out.println(client.getPerson(1));
        Properties systemProps = System.getProperties();
        systemProps.put("javax.net.ssl.keyStorePassword","passwordForKeystore");
        systemProps.put("javax.net.ssl.keyStore", jrehome_caerts);
        systemProps.put("javax.net.ssl.keyStorePassword","changeit");
        systemProps.put("javax.net.ssl.trustStore",  jrehome_caerts);
        systemProps.put("javax.net.ssl.trustStorePassword","changeit");
        System.setProperties(systemProps);        
        IDCExtensionMappingClientDevt client =context.getBean(IDCExtensionMappingClientDevt.class);
        String pageContent = client.getExtensionMappingFirstPage(token,0);
        Gson gson = new GsonBuilder().create();
        IDCAssets assets = gson.fromJson(pageContent, IDCAssets.class);

        long totalAssets = 0;
	        long assetsFound = assets.items!=null? assets.items.length:0; 
	        totalAssets+=assetsFound;
	        System.out.println( assetsFound );
	        for (IDCAsset asset:assets.items){
	        	bw.write(asset._id);bw.write(IDCAsset.COMMA_SPLITTER);
	        	bw.write(asset._name);bw.write(IDCAsset.COMMA_SPLITTER);
	        	bw.write(asset.getContextPath() );bw.write(IDCAsset.COMMA_SPLITTER);
	        	bw.write(asset._type);
	        	bw.write("\n");	        	
	        }
        while ( assets.hasNextPage()){
	        //System.out.println(assets.paging);
            pageContent = client.getExtensionMappingFirstPage(token,assets.paging.end + 1);
            assets = gson.fromJson(pageContent, IDCAssets.class);

	        assetsFound = assets.items!=null? assets.items.length:0;
	        totalAssets+=assetsFound;
		    System.out.println( assets.paging.next );
	        for (IDCAsset asset:assets.items){
	        	bw.write(asset._id);bw.write(IDCAsset.COMMA_SPLITTER);
	        	bw.write(asset._name);bw.write(IDCAsset.COMMA_SPLITTER);
	        	bw.write(asset.getContextPath() );bw.write(IDCAsset.COMMA_SPLITTER);
	        	bw.write(asset._type);
	        	bw.write("\n");	        	
	        }
            
        }
        bw.close();
        System.out.println(totalAssets);
        
        
    }
}
