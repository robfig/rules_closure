package test;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.exprtree.Operator;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.jssrc.restricted.SoyJsSrcPrintDirective;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;
import com.google.template.soy.shared.restricted.SoyJavaPrintDirective;
import com.google.template.soy.shared.restricted.SoyPrintDirective;

public class ExampleModule extends AbstractModule {
    /** {@inheritDoc} */
    @Override protected void configure() {
        bindFunctions(Multibinder.newSetBinder(binder(), SoyFunction.class));
        bindDirectives(Multibinder.newSetBinder(binder(), SoyPrintDirective.class));
    }

    private void bindFunctions(Multibinder<SoyFunction> fns) {
        fns.addBinding().to(ToLowerFunction.class);
    }

    private void bindDirectives(Multibinder<SoyPrintDirective> dirs) {
        dirs.addBinding().to(ToUpperDirective.class);
    }

    public static class ToLowerFunction implements SoyJavaFunction, SoyJsSrcFunction {
        @Override
        public String getName() {
            return "toLower";
        }

        @Override
        public Set<Integer> getValidArgsSizes() {
            return ImmutableSet.of(1);
        }

        @Override
        public SoyValue computeForJava(List<SoyValue> args) {
            return StringData.forValue(args.get(0).stringValue().toLowerCase());
        }

        @Override
        public JsExpr computeForJsSrc(List<JsExpr> args) {
            JsExpr arg = args.get(0);
            String exprText = "(" + arg.getText() + ").toLowerCase()";
            return new JsExpr(exprText, Operator.NOT_EQUAL.getPrecedence());
        }
    }

    public static class ToUpperDirective implements SoyJavaPrintDirective, SoyJsSrcPrintDirective {
        @Override
        public String getName() {
            return "|toUpper";
        }

        @Override
        public Set<Integer> getValidArgsSizes() {
            return ImmutableSet.of(0);
        }

        @Override
        public SoyValue applyForJava(SoyValue value, List<SoyValue> args) {
            return StringData.forValue(value.stringValue().toUpperCase());
        }

        @Override
        public JsExpr applyForJsSrc(JsExpr value, List<JsExpr> args) {
            String exprText = "(" + value.getText() + ").toUpperCase()";
            return new JsExpr(exprText,  Operator.NOT_EQUAL.getPrecedence());
        }
    }
}
