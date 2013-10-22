package com.squarespace.v6.template.less.model;

import static com.squarespace.v6.template.less.core.ExecuteErrorMaker.incompatibleUnits;
import static com.squarespace.v6.template.less.core.ExecuteErrorMaker.invalidOperation;
import static com.squarespace.v6.template.less.model.NodeType.COLOR;

import com.squarespace.v6.template.less.ErrorInfo;
import com.squarespace.v6.template.less.LessException;
import com.squarespace.v6.template.less.Options;
import com.squarespace.v6.template.less.exec.ExecEnv;


public abstract class BaseColor extends BaseNode {

  public abstract Colorspace getColorspace();

  public abstract RGBColor toRGB();
  
  public abstract HSLColor toHSL();
  
  public static RGBColor fromHex(String raw) {
    int[] c = Colors.hexToRGB(raw);
    return new RGBColor(c[0], c[1], c[2]);
  }
  
  public static RGBColor fromDimension(Dimension dim) {
    int val = (int)dim.value();
    return new RGBColor(val, val, val);
  }

  public static RGBColor fromName(String name) {
    int[] c = Colors.nameToRGB(name);
    if (c != null) {
      return new RGBColor(c[0], c[1], c[2], true);
    }
    return null;
  }
  
  public static double clamp(double val, double min, double max) {
    return Math.min(Math.max(val, min), max);
  }
  
  @Override
  public NodeType type() {
    return COLOR;
  }
  
  @Override
  public Node operate(ExecEnv env, Operator op, Node arg) throws LessException {
    Options opts = env.context().options();
    if (arg.is(NodeType.COLOR)) {
      return operate(op, this, (BaseColor)arg);
    
    } else if (arg.is(NodeType.DIMENSION)) {
      Dimension dim = (Dimension)arg;
      if (dim.unit() != null) {
        ErrorInfo info = incompatibleUnits(dim.unit(), NodeType.COLOR);
        if (opts.strict()) {
          throw new LessException(info);
        }
        env.addWarning(info.getMessage() + ".. stripping unit.");
      }
      return operate(op, this, fromDimension((Dimension)arg));

    } else {
      ErrorInfo info = invalidOperation(op, type());
      if (opts.strict()) {
        throw new LessException(info);
      }
      if (!opts.hideWarnings()) {
        env.addWarning(info.getMessage() + ".. ignoring the right-hand operand.");
      }
      return this;
    }
  }
  
  private BaseColor operate(Operator op, BaseColor arg0, BaseColor arg1) throws LessException {
    RGBColor c0 = arg0.toRGB();
    RGBColor c1 = arg1.toRGB();
    int r = c0.red();
    int g = c0.green();
    int b = c0.blue();
    double a = c0.alpha() + c1.alpha(); // per less.js
    switch (op) {
      case ADD:
        return new RGBColor(r + c1.red(), g + c1.green(), b + c1.blue(), a);
        
      case DIVIDE:
        return new RGBColor(r / c1.red(), g / c1.green(), b / c1.blue(), a);

      case MULTIPLY:
        return new RGBColor(r * c1.red(), g * c1.green(), b * c1.blue(), a);

      case SUBTRACT:
        return new RGBColor(r - c1.red(), g - c1.green(), b - c1.blue(), a);

      default:
        throw new LessException(invalidOperation(op, NodeType.COLOR));
    }
  }

}
