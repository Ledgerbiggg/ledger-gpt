package com.ledger.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author ledger
 * @version 1.0
 **/
public interface ApiService {
 String submitThreeDotFive(List<Map<String, String>> msgList, Map<String, Object> params) throws IOException;

 String submitThree(List<Map<String, String>> msgList, Map<String, Object> params) throws IOException;


 List<String> toGetImage(Map<String, String> promptMap, Map<String, Object> params) throws IOException;


}
