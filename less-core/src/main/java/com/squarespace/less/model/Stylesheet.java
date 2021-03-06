/**
 * Copyright (c) 2014 SQUARESPACE, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.squarespace.less.model;

import com.squarespace.less.core.Buffer;
import com.squarespace.less.exec.ExtendIndex;


/**
 * Represents a LESS stylesheet.
 */
public class Stylesheet extends BlockNode {

  /**
   * Indexes the extend expressions as the stylesheet is traversed.
   */
  protected ExtendIndex extendIndex;

  /**
   * Constructs an empty stylesheet.
   */
  public Stylesheet() {
    super();
  }

  /**
   * Constructs a stylesheet with the given {@link Block}
   */
  public Stylesheet(Block block) {
    super(block);
  }

  /**
   * Returns the extend index for the stylesheet.
   */
  public ExtendIndex extendIndex() {
    return extendIndex;
  }

  /**
   * Sets the extend expression index for this scope.
   */
  public void extendContext(ExtendIndex index) {
    this.extendIndex = index;
  }

  /**
   * Creates a shallow copy of the stylesheet.
   */
  public Stylesheet copy() {
    Stylesheet result = new Stylesheet(block().copy());
    result.charOffset = charOffset;
    result.lineOffset = lineOffset;
    return result;
  }

  /**
   * See {@link Node#type()}
   */
  @Override
  public NodeType type() {
    return NodeType.STYLESHEET;
  }

  /**
   * See {@link Node#repr(Buffer)}
   */
  @Override
  public void repr(Buffer buf) {
    block.repr(buf);
  }

  /**
   * See {@link Node#modelRepr(Buffer)}
   */
  @Override
  public void modelRepr(Buffer buf) {
    typeRepr(buf);
    posRepr(buf);
    buf.append('\n');
    buf.incrIndent().indent();
    super.modelRepr(buf);
    buf.decrIndent();
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Stylesheet) && super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

}
