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
import com.squarespace.less.exec.ExecEnv;


/**
 * A detached ruleset is one with no selectors, that can be referenced
 * by a variable name.
 */
public class DetachedRuleset extends BlockNode {

  /**
   * Constructs a detached ruleset with the given block.
   */
  public DetachedRuleset(Block block) {
    super(block);
  }

  /**
   * Return a copy of this detached ruleset.
   */
  public DetachedRuleset copy(ExecEnv env) {
    return new DetachedRuleset(block.copy());
  }

  /**
   * See {@link Node#type()}
   */
  @Override
  public NodeType type() {
    return NodeType.DETACHED_RULESET;
  }

  @Override
  public void repr(Buffer buf) {
    buf.blockOpen();
    block.repr(buf);
    buf.blockClose();
  }

  @Override
  public void modelRepr(Buffer buf) {
    typeRepr(buf);
    posRepr(buf);
    buf.append('\n');
    buf.incrIndent().indent();
    super.modelRepr(buf);
    buf.decrIndent().append('\n');
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof DetachedRuleset) ? super.equals(obj) : false;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

}
