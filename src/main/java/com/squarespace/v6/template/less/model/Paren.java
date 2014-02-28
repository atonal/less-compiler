package com.squarespace.v6.template.less.model;

import static com.squarespace.v6.template.less.core.LessUtils.safeEquals;

import com.squarespace.v6.template.less.LessException;
import com.squarespace.v6.template.less.core.Buffer;
import com.squarespace.v6.template.less.exec.ExecEnv;


public class Paren extends BaseNode {

  private Node node;
  
  public Paren(Node node) {
    this.node = node;
  }
  
  public Node getNode() {
    return node;
  }
  
  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Paren) ? safeEquals(node, ((Paren)obj).node) : false; 
  }
  
  @Override
  public NodeType type() {
    return NodeType.PAREN;
  }
 
  @Override
  public boolean needsEval() {
    return node.needsEval();
  }
  
  @Override
  public Node eval(ExecEnv env) throws LessException {
    return node.needsEval() ? new Paren(node.eval(env)) : this;
  }
  
  @Override
  public void repr(Buffer buf) {
    buf.append('(');
    node.repr(buf);
    buf.append(')');
  }
  
  @Override
  public void modelRepr(Buffer buf) {
    typeRepr(buf);
    buf.append('\n');
    if (node != null) {
      buf.incrIndent();
      buf.indent();
      node.modelRepr(buf);
      buf.decrIndent();
    }
  }
  
}