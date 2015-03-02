/**
 * Copyright, 2015, Squarespace, Inc.
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

import com.squarespace.less.LessException;
import com.squarespace.less.core.Buffer;
import com.squarespace.less.exec.ExecEnv;


/**
 * Part of an {@link ExtendList}, representing a {@link Selector} and
 * the optional "all" keyword.
 */
public class Extend extends BaseNode {

  private final Selector selector;

  private final boolean matchAll;

  public Extend(Selector selector, boolean matchAll) {
    this.selector = selector;
    this.matchAll = matchAll;
  }

  public Selector selector() {
    return selector;
  }

  public boolean matchAll() {
    return matchAll;
  }

  @Override
  public NodeType type() {
    return NodeType.EXTEND;
  }

  @Override
  public Node eval(ExecEnv env) throws LessException {
    return super.eval(env);
  }

  /**
   * @see Node#repr(Buffer)
   */
  @Override
  public void repr(Buffer buf) {
    selector.repr(buf);
    if (matchAll) {
      buf.append(" all");
    }
  }

  /**
   * @see Node#modelRepr(Buffer)
   */
  @Override
  public void modelRepr(Buffer buf) {
    typeRepr(buf);
    posRepr(buf);
    if (matchAll) {
      buf.append(" ALL");
    }
    buf.append('\n');
    buf.incrIndent().indent();
    selector.modelRepr(buf);
    buf.decrIndent();
  }

}
