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

package com.squarespace.less.parse;

import com.squarespace.less.LessException;
import com.squarespace.less.core.Chars;
import com.squarespace.less.model.Node;


/**
 * Parses variable names.
 */
public class VariableParselet implements Parselet {

  @Override
  public Node parse(LessStream stm) throws LessException {
    Mark mark = stm.mark();
    boolean indirect = false;
    int pos = 0;
    if (stm.peek() != Chars.AT_SIGN) {
      return null;
    }
    pos++;

    if (stm.peek(pos) == Chars.AT_SIGN) {
      indirect = true;
      pos++;
    }

    stm.seek(pos);
    if (!stm.matchIdentifier()) {
      stm.restore(mark);
      return null;
    }

    String name = '@' + stm.token();
    return stm.context().nodeBuilder().buildVariable(indirect ? '@' + name : name, false);
  }

}
