package com.example;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url="https://igc-services-pr.bmogc.net:9445/ibm/iis/igc-rest/v1")
public interface IDCExtensionMappingClientProd  extends IDCExtensionMappingClient{

	@RequestMapping(value = "/search?types=extension_mapping_document&begin={index}",method = RequestMethod.GET,consumes = "application/json")
	String getExtensionMappingFirstPage(@RequestHeader("Authorization") String token , @PathVariable("index") long index);
	
	@RequestMapping(value = "/types",method = RequestMethod.GET,consumes = "application/json")
	String getAllTypes();
}
