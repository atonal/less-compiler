package com.squarespace.v6.template.less.exec;

import java.util.ArrayList;
import java.util.List;

import com.squarespace.v6.template.less.LessException;
import com.squarespace.v6.template.less.core.FlexList;
import com.squarespace.v6.template.less.model.Block;
import com.squarespace.v6.template.less.model.Mixin;
import com.squarespace.v6.template.less.model.MixinCallArgs;
import com.squarespace.v6.template.less.model.MixinParams;
import com.squarespace.v6.template.less.model.Node;
import com.squarespace.v6.template.less.model.NodeType;
import com.squarespace.v6.template.less.model.Ruleset;


/**
 * Searches the node tree, looking for any MIXIN or RULESET nodes which match
 * the given MIXIN_CALL's selector and arguments, if any.
 */
public class MixinResolver {

  private List<MixinMatch> results;
  
  private MixinMatcher matcher;
  
  private MixinCallArgs args;

  private List<String> callPath;

  private int callPathSize;
  
  private int maxIndex;
  
  public MixinResolver() {
  }

  public void reset(MixinMatcher matcher) {
    this.matcher = matcher;
    this.args = matcher.mixinArgs();
    this.callPath = matcher.mixinCall().path();
    this.callPathSize = callPath.size();
    this.maxIndex = callPath.size() - 1;
    this.results = new ArrayList<>(3);
  }
  
  public List<MixinMatch> matches() {
    return results;
  }
  
  public boolean match(Block block) throws LessException {
    return match(0, block);
  }
  
  private boolean match(int index, Block block) throws LessException {
    if (index >= callPathSize) {
      return false;
    }
    
    FlexList<Node> rules = block.rules();
    if (rules.isEmpty()) {
      return false;
    }

    boolean matched = false;
    int size = rules.size();
    for (int i = 0; i < size; i++) {
      Node node = rules.get(i);
      if (node.is(NodeType.RULESET)) {
        matched |= matchRuleset(index, (Ruleset)node);

      } else if (node.is(NodeType.MIXIN)) {
        matched |= matchMixin(index, (Mixin)node);
      }
    }
    
    return matched;
  }

  private boolean matchRuleset(int index, Ruleset ruleset) throws LessException {
    Ruleset original = (Ruleset)ruleset.original();
    if (original.evaluating()) {
      return false;
    }
    
    List<List<String>> paths = ruleset.mixinPaths();
    if (paths.isEmpty()) {
      return false;
    }
    
    // Try each of the ruleset's mixin paths
    for (List<String> path : paths) {
      int remaining = matchPath(index, path);
      if (remaining < 0) {
        continue;
      }

      // Full match.. add this to the results.
      if (remaining == 0) {
        if (args == null || args.isEmpty()) {
          results.add(new MixinMatch(ruleset, null));
          return true;
        }

      } else {
        // Partial match.. continue matching the children of this ruleset.
        if (match(index + path.size(), ruleset.block())) {
          return true;
        }
      }
    }

    return false;
  }
  
  private boolean matchMixin(int index, Mixin mixin) throws LessException {
    boolean matched = callPath.get(index).equals(mixin.name());
    if (!matched) {
      return false;
    }
    if (matched && index < maxIndex) {
      // We haven't matched entire path, so drill deeper.
      return match(index + 1, mixin.block());
    }

    MixinParams params = mixin.params();
    ExecEnv env = matcher.callEnv().copy();
    ExecEnv defEnv = mixin.closure();
    if (defEnv != null) {
      env.append(defEnv.frames().copy());
    }
    params = (MixinParams) params.eval(env);
    boolean matches = matcher.patternMatch(params);

    if (matches) {
      results.add(new MixinMatch(mixin, params));
    }
    return matches;
  }

  private int matchPath(int index, List<String> other) {
    int currSize = callPathSize - index;
    int otherSize = other.size();
    if (otherSize == 0 || currSize < otherSize) {
      return -1;
    }

    int j = 0;
    while (j < otherSize) {
      if (!callPath.get(index).equals(other.get(j))) {
        return -1;
      }
      index++;
      j++;
    }
    return callPathSize - index;
  }

}