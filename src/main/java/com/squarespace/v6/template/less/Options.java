package com.squarespace.v6.template.less;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;


/**
 * Represents all basic options for the compiler.
 */
public class Options {

  public static final int DEFAULT_INDENT = 2;

  public static final String DEFAULT_ROOT = ".";
  
  public static final int DEFAULT_RECURSION_LIMIT = 64;
  
  private final Set<Option> flags = EnumSet.of(Option.STRICT);
  
  private int indent = DEFAULT_INDENT;

  private List<Path> importPaths = new ArrayList<>();
  
  private int recursionLimit = DEFAULT_RECURSION_LIMIT;
  
  public Options() {
  }
  
  public Options(int indent) {
    compress(false);
    indent(indent);
  }
  
  public Options(boolean compress) {
    compress(compress);
  }
  
  public boolean compress() {
    return flags.contains(Option.COMPRESS);
  }
  
  public boolean debug() {
    return flags.contains(Option.DEBUG);
  }

  public boolean hideWarnings() {
    return flags.contains(Option.HIDE_WARNINGS);
  }
  
  public int indent() {
    return indent;
  }

  public boolean importOnce() {
    return flags.contains(Option.IMPORT_ONCE);
  }
  
  public List<Path> importPaths() {
    return importPaths;
  }

  public boolean lineNumbers() {
    return flags.contains(Option.LINE_NUMBERS);
  }
  
  public int recursionLimit() {
    return recursionLimit;
  }
  
  public boolean strict() {
    return flags.contains(Option.STRICT);
  }
  
  public boolean tabs() {
    return flags.contains(Option.TABS);
  }
  
  public boolean tracing() {
    return flags.contains(Option.TRACING);
  }
  
  public void compress(boolean flag) {
    set(flag, Option.COMPRESS);
  }
  
  public void debug(boolean flag) {
    set(flag, Option.DEBUG);
  }

  public void indent(int size) {
    this.indent = size;
  }

  public void importOnce(boolean flag) {
    set(flag, Option.IMPORT_ONCE);
  }
  
  public void addImportPath(String path) {
    this.importPaths.add(Paths.get(path));
  }

  public void hideWarnings(boolean flag) {
    set(flag, Option.HIDE_WARNINGS);
  }
  
  public void importPaths(List<String> paths) {
    for (String path : paths) {
      this.importPaths.add(Paths.get(path));
    }
  }
  
  public void lineNumbers(boolean flag) {
    set(flag, Option.LINE_NUMBERS);
  }
  
  public void recursionLimit(int limit) {
    this.recursionLimit = limit;
  }
  
  public void strict(boolean flag) {
    set(flag, Option.STRICT);
  }
  
  public void tabs(boolean flag) {
    set(flag, Option.TABS);
  }
  
  public void tracing(boolean flag) {
    set(flag, Option.TRACING);
  }
  
  private void set(boolean flag, Option opt) {
    if (flag) {
      flags.add(opt);
    } else {
      flags.remove(opt);
    }
  }
  
  private static enum Option {
    COMPRESS,
    DEBUG,
    HIDE_WARNINGS,
    IMPORT_ONCE,
    LINE_NUMBERS,
    STRICT,
    TABS,
    TRACING
  }

}