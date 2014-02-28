package com.squarespace.v6.template.less;

import java.util.Map;


/**
 * General interface for syntax and execution errors.
 */
public interface ErrorType {

  String prefix(Map<String, Object> params);
  
  String message(Map<String, Object> params);

}