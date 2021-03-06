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

import static com.squarespace.less.parse.Parselets.EXPRESSION;

import com.squarespace.less.LessException;
import com.squarespace.less.core.Chars;
import com.squarespace.less.model.Node;


public class SubParselet implements Parselet {

  @Override
  public Node parse(LessStream stm) throws LessException {
    Mark mark = stm.mark();
    if (!stm.seekIf(Chars.LEFT_PARENTHESIS)) {
      return null;
    }

    // Communicate with nested expressions that they're inside parens.
    stm.setInParens(true);
    Node node = stm.parse(EXPRESSION);
    stm.setInParens(false);

    if (!stm.seekIf(Chars.RIGHT_PARENTHESIS)) {
      stm.restore(mark);
      return null;
    }
    return node;
  }

}
