package com.squarespace.v6.template.less.core;

import com.squarespace.v6.template.less.ErrorInfo;
import com.squarespace.v6.template.less.ErrorType;
import com.squarespace.v6.template.less.SyntaxErrorType;


/**
 * Builds error messages for parse phase of the compile, implemented as 
 * static methods to reduce code clutter somewhat.
 */
public class SyntaxErrorMaker {

  public static ErrorInfo alphaUnitsInvalid(Object arg) {
    return error(SyntaxErrorType.ALPHA_UNITS_INVALID).arg0(arg);
  }
  
  public static ErrorInfo expected(Object thing) {
    return error(SyntaxErrorType.EXPECTED).arg0(thing);
  }
  
  public static ErrorInfo general(Object obj) {
    return error(SyntaxErrorType.GENERAL).arg0(obj);
  }
  
  public static ErrorInfo incompleteParse() {
    return error(SyntaxErrorType.INCOMPLETE_PARSE);
  }
  
  public static ErrorInfo javascriptDisabled() {
    return error(SyntaxErrorType.JAVASCRIPT_DISABLED);
  }
  
  public static ErrorInfo mixedDelimiters() {
    return error(SyntaxErrorType.MIXED_DELIMITERS);
  }
  
  public static ErrorInfo quotedBareLF() {
    return error(SyntaxErrorType.QUOTED_BARE_LF);
  }
  
  private static ErrorInfo error(ErrorType type) {
    ErrorInfo info = new ErrorInfo(type);
    info.code(type);
    return info;
  }

}